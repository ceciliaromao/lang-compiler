//Maria Cecília Romão Santos      202165557C
//Maria Luisa Riolino Guimarães 202165563C

package br.ufjf.lang.compiler.interpreter;

import br.ufjf.lang.compiler.ast.*;
import java.util.*;

// Definições auxiliares para os valores em tempo de execução.
abstract class LValue {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}

class LValueInt extends LValue {
    public int value;
    public LValueInt(int v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}

class LValueFloat extends LValue {
    public double value;
    public LValueFloat(double v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}

class LValueChar extends LValue {
    public char value;
    public LValueChar(char v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}

class LValueBool extends LValue {
    public boolean value;
    public LValueBool(boolean v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}

class LValueNull extends LValue {
    @Override public String toString() { return "null"; }
}

class LValueArray extends LValue {
    private final List<LValue> values;

    public LValueArray(int size) {
        this.values = new ArrayList<>(Collections.nCopies(size, new LValueNull()));
    }

    public LValue get(int index) {
        if (index < 0 || index >= values.size()) {
            throw new RuntimeException("Acesso a array fora dos limites: índice " + index);
        }
        return values.get(index);
    }

    public void set(int index, LValue value) {
         if (index < 0 || index >= values.size()) {
            throw new RuntimeException("Atribuição a array fora dos limites: índice " + index);
        }
        values.set(index, value);
    }

    @Override
    public String toString() {
        return values.toString();
    }

    public int size() {
        return values.size();
    }
}

// Representa um valor de registro (struct/objeto) em tempo de execução
class LValueRecord extends LValue {
    private final Map<String, LValue> fields = new HashMap<>();

    public LValue get(String fieldName) {
        if (!fields.containsKey(fieldName)) {
            throw new RuntimeException("Acesso a campo inexistente: '" + fieldName + "'");
        }
        return fields.get(fieldName);
    }

    public void set(String fieldName, LValue value) {
        fields.put(fieldName, value);
    }

    @Override
    public String toString() {
        return fields.toString();
    }
}

public class Interpreter {

    private final Map<String, FunDef> functionTable = new HashMap<>();
    private final Map<String, DataDef> dataTable = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void run(Program program) {
        // Carrega as definições de função e de dados em tabelas para acesso rápido.
        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                functionTable.put(fun.name, fun);
            } else if (def instanceof DataDef data) {
                dataTable.put(data.name, data);
            }
        }

        System.out.println("Funções disponíveis:");
        for (String funName : functionTable.keySet()) {
            System.out.println(" - " + funName);
        }

        FunDef mainFun = functionTable.get("main");
        if (mainFun == null) {
            throw new RuntimeException("Função main não encontrada!");
        }

        // Cria argumentos padrão para a função main, se necessário.
        List<LValue> mainArgs = new ArrayList<>();
        for (FunDef.Param param : mainFun.params) {
            if (param.type() instanceof TypeBase base) {
                switch (base.name) {
                    case "Int" -> mainArgs.add(new LValueInt(0));
                    case "Float" -> mainArgs.add(new LValueFloat(0.0));
                    case "Char" -> mainArgs.add(new LValueChar('\0'));
                    case "Bool" -> mainArgs.add(new LValueBool(false));
                    default -> {
                        // Assume que é um tipo de registro.
                        DataDef dataDef = dataTable.get(base.name);
                        if (dataDef != null) {
                            LValueRecord record = new LValueRecord();
                            for (FieldDecl field : dataDef.fields) {
                                record.set(field.name, new LValueNull());
                            }
                            mainArgs.add(record);
                        } else {
                            mainArgs.add(new LValueNull());
                        }
                    }
                }
            } else if (param.type() instanceof TypeArray) {
                mainArgs.add(new LValueArray(0)); // Array de tamanho 0 como padrão
            } else {
                mainArgs.add(new LValueNull());
            }
        }

        // Inicia a execução com uma pilha de escopos nova e vazia.
        List<LValue> returnValues = executeFunction(mainFun, mainArgs, new Stack<>());

        // Imprime os valores de retorno da função main, se houver.
        if (returnValues != null && !returnValues.isEmpty()) {
            System.out.println("Retorno de main:");
            for (LValue val : returnValues) {
                System.out.println(" - " + val);
            }
        }
    }

    private List<LValue> executeFunction(FunDef fun, List<LValue> args, Stack<Map<String, LValue>> scopes) {
        // Cria um novo escopo na pilha para os parâmetros e variáveis locais da função.
        scopes.push(new HashMap<>());

        List<FunDef.Param> params = fun.params;
        if (params.size() != args.size()){
            throw new RuntimeException("Erro na chamada da função '" + fun.name + "': esperado " + params.size() + " argumentos, mas foram fornecidos " + args.size());
        }

        for (int i = 0; i < params.size(); i++) {
            // Adiciona os argumentos da chamada ao escopo atual (topo da pilha).
            scopes.peek().put(params.get(i).name(), args.get(i));
        }

        // Executa o corpo da função com a pilha de escopos atualizada.
        List<LValue> returnValues = executeCmd(fun.body, scopes);

        // Remove o escopo da função da pilha ao final de sua execução.
        scopes.pop();

        return returnValues;
    }

    private List<LValue> executeCmd(Cmd cmd, Stack<Map<String, LValue>> scopes) {

        if (cmd instanceof CmdBlock block) {
            // Entra em um novo escopo de bloco.
            scopes.push(new HashMap<>());
            for (Cmd c : block.commands) {
                List<LValue> ret = executeCmd(c, scopes);
                // Se um comando de retorno for encontrado, propaga o valor imediatamente.
                if (ret != null) {
                    scopes.pop(); // Garante que o escopo seja removido antes de retornar.
                    return ret;
                }
            }
            // Sai do escopo do bloco.
            scopes.pop();
            return null;
        }

        if (cmd instanceof CmdPrint p) {
            LValue v = evalExpr(p.expr, scopes);
            if (v instanceof LValueInt i) System.out.println(i.value);
            else if (v instanceof LValueBool b) System.out.println(b.value);
            else if (v instanceof LValueFloat f) System.out.println(f.value);
            else if (v instanceof LValueChar c) System.out.println(c.value);
            else if (v instanceof LValueNull) System.out.println("null");
            else System.out.println(v);
            return null;
        }

        if (cmd instanceof CmdReturn r) {
            List<LValue> values = new ArrayList<>();
            for (Expr e : r.values) {
                values.add(evalExpr(e, scopes));
            }
            return values;
        }

        if (cmd instanceof CmdAssign assign) {
            LValue val = evalExpr(assign.value, scopes);
            setLValue(assign.target, val, scopes);
            return null;
        }

        if (cmd instanceof CmdIf iff) {
            LValue cond = evalExpr(iff.condition, scopes);
            if (asBool(cond)) {
                return executeCmd(iff.thenBranch, scopes);
            } else if (iff.elseBranch != null) {
                return executeCmd(iff.elseBranch, scopes);
            }
            return null;
        }

        if (cmd instanceof CmdIterate loop) {
            LValue rangeVal = evalExpr(loop.range, scopes);
            int count;

            if (rangeVal instanceof LValueInt num) {
                count = num.value;
            } else if (rangeVal instanceof LValueArray arr) {
                count = arr.size();
            } else {
                throw new RuntimeException("O comando 'iterate' requer um valor inteiro ou um array.");
            }
            
            if (count > 0) {
                scopes.push(new HashMap<>());

                for (int i = 0; i < count; i++) {
                    if (loop.varName != null) {
                        scopes.peek().put(loop.varName, new LValueInt(i));
                    }

                    List<LValue> ret = executeCmd(loop.body, scopes);
                    if (ret != null) {
                        scopes.pop(); // Garante a saída do escopo antes de retornar.
                        return ret;
                    }
                }

                scopes.pop();
            }
            return null;
        }
        
        if (cmd instanceof CmdRead read) {
            System.out.print("> ");
            String line = scanner.nextLine();
        
            if (read.target instanceof LValueVar var) {
                if (var.type == null) {
                    LValue inferredVal = inferFromInput(line);
                    var.type = getTypeOf(inferredVal);
                    setLValue(var, inferredVal, scopes);
                } else if (var.type instanceof TypeBase baseType) {
                    LValue readValue = switch (baseType.name) {
                        case "Int" -> new LValueInt(Integer.parseInt(line));
                        case "Char" -> {
                            if (line.length() != 1) throw new RuntimeException("Esperado um único caractere, obtido: " + line);
                            yield new LValueChar(line.charAt(0));
                        }
                        case "Float" -> new LValueFloat(Double.parseDouble(line));
                        case "Bool" -> {
                             if (!line.equals("true") && !line.equals("false")) throw new RuntimeException("Esperado 'true' ou 'false', obtido: " + line);
                             yield new LValueBool(Boolean.parseBoolean(line));
                        }
                        default -> throw new RuntimeException("Tipo não suportado: " + baseType.name);
                    };
                    setLValue(var, readValue, scopes);
                } else {
                    throw new RuntimeException("Tipo da variável não reconhecido: " + var.type);
                }
            } else {
                throw new RuntimeException("Target do comando 'read' não é uma variável válida.");
            }
            return null;
        }

        if (cmd instanceof CmdCall call) {
            FunDef callee = functionTable.get(call.functionName);
            if (callee == null) throw new RuntimeException("Função não encontrada: " + call.functionName);

            List<LValue> args = new ArrayList<>();
            for (Expr arg : call.arguments) {
                args.add(evalExpr(arg, scopes));
            }

            List<LValue> results = executeFunction(callee, args, scopes);

            if (call.outputTargets != null && results != null) {
                for (int i = 0; i < call.outputTargets.size(); i++) {
                    setLValue(call.outputTargets.get(i), results.get(i), scopes);
                }
            }
            return null;
        }

        throw new RuntimeException("Comando não suportado: " + cmd.getClass().getSimpleName());
    }

    private LValue evalExpr(Expr expr, Stack<Map<String, LValue>> scopes) {
        if (expr instanceof ExprInt i) return new LValueInt(i.value);
        if (expr instanceof ExprBool b) return new LValueBool(b.value);
        if (expr instanceof ExprFloat f) return new LValueFloat(f.value);
        if (expr instanceof ExprChar c) return new LValueChar(c.value);
        if (expr instanceof ExprNull) return new LValueNull();
        if (expr instanceof ExprParen p) return evalExpr(p.inner, scopes);

        if (expr instanceof ExprUnary u) {
            LValue val = evalExpr(u.expr, scopes);
            return evalUnary(u.op, val);
        }

        if (expr instanceof ExprNew n) {

            // array
            if (n.size != null) {
                LValue sizeVal = evalExpr(n.size, scopes);

                if (!(sizeVal instanceof LValueInt i)) {
                    throw new RuntimeException("O tamanho de um array deve ser um inteiro.");
                }
                int size = i.value;
                if (size < 0) {
                    throw new RuntimeException("O tamanho de um array não pode ser negativo.");
                }

                return new LValueArray(size);
            }

            // record
            else {
                if (n.type instanceof TypeBase base) {
                    DataDef dataDef = dataTable.get(base.name);
                    if (dataDef != null) {
                        LValueRecord record = new LValueRecord();
                        for (FieldDecl field : dataDef.fields) {
                            record.set(field.name, new LValueNull());
                        }
                        return record;
                    }
                }
                // Se não for um tipo base conhecido ou não estiver na dataTable, retorna um registro vazio
                return new LValueRecord();
            }
        }

        if (expr instanceof ExprBinary b) {
            LValue l = evalExpr(b.left, scopes);
            LValue r = evalExpr(b.right, scopes);
            return evalBinary(b.op, l, r);
        }

        if (expr instanceof ExprLValue lval) {
            return getLValue(lval.lvalue, scopes);
        }

        if (expr instanceof ExprCall call) {
            FunDef callee = functionTable.get(call.functionName);
            if (callee == null) throw new RuntimeException("Função não encontrada: " + call.functionName);

            System.out.println("Chamando função: " + call.functionName);
            
            List<LValue> args = new ArrayList<>();
            for (Expr arg : call.arguments) {
                args.add(evalExpr(arg, scopes));
            }

            LValue indexValue = evalExpr(call.index, scopes);
            if (!(indexValue instanceof LValueInt i)) {
                throw new RuntimeException("O índice de retorno de uma função deve ser um inteiro.");
            }
            int returnIndex = i.value;

            List<LValue> results = executeFunction(callee, args, scopes);

            if (results == null || returnIndex < 0 || returnIndex >= results.size()) {
                throw new RuntimeException("Índice de retorno " + returnIndex + " fora dos limites para a chamada da função '" + call.functionName + "'");
            }
            
            return results.get(returnIndex);
        }

        throw new RuntimeException("Expressão não suportada: " + expr.getClass().getSimpleName());
    }

    private LValue evalUnary(String op, LValue val) {
        switch (op) {
            case "!": {
                if (!(val instanceof LValueBool b)) {
                    throw new RuntimeException("O operador '!' requer um operando do tipo Bool.");
                }
                return new LValueBool(!b.value);
            }
            case "-":{
                if (val instanceof LValueInt i) {
                    return new LValueInt(-i.value);
                }

                if (val instanceof LValueFloat f) {
                    return new LValueFloat(-f.value);
                }

                throw new RuntimeException("O operador '-' requer um operando numérico (Int ou Float).");
            }
            default: {
                throw new UnsupportedOperationException("Operador unário não suportado: " + op);
            }
        }
    }

    private LValue evalBinary(String op, LValue left, LValue right) {
        switch (op) {
            case "+", "-", "*", "/", "%":
                // Promoção de tipo: se um for Float, converte o outro para Float.
                if (left instanceof LValueFloat || right instanceof LValueFloat) {
                    double l = (left instanceof LValueFloat f) ? f.value : ((LValueInt) left).value;
                    double r = (right instanceof LValueFloat f) ? f.value : ((LValueInt) right).value;
                    return new LValueFloat(switch (op) {
                        case "*" -> l * r;
                        case "/" -> l / r;
                        case "%" -> l % r;
                        case "+" -> l + r;
                        case "-" -> l - r;
                        default -> throw new UnsupportedOperationException("Operador não suportado: " + op);
                    });
                }
                
                if (left instanceof LValueInt li && right instanceof LValueInt ri) {
                    return new LValueInt(switch (op) {
                        case "*" -> li.value * ri.value;
                        case "/" -> li.value / ri.value;
                        case "%" -> li.value % ri.value;
                        case "+" -> li.value + ri.value;
                        case "-" -> li.value - ri.value;
                        default -> throw new UnsupportedOperationException("Operador não suportado: " + op);
                    });
                }
                throw new RuntimeException("Tipos incompatíveis para operação: " + left + " " + op + " " + right);

            case "==", "!=", "<": {
                int cmp;
                
                if (left instanceof LValueInt li && right instanceof LValueInt ri) {
                    cmp = Integer.compare(li.value, ri.value);
                } 
                else if (left instanceof LValueFloat lf && right instanceof LValueFloat rf) {
                    cmp = Double.compare(lf.value, rf.value);
                }
                else if (left instanceof LValueChar lc && right instanceof LValueChar rc) {
                    cmp = Character.compare(lc.value, rc.value);
                }
                else {
                    throw new RuntimeException("Tipos incompatíveis para a operação de comparação '" + op + "'");
                }

                return new LValueBool(switch (op) {
                    case "==" -> cmp == 0;
                    case "!=" -> cmp != 0;
                    case "<"  -> cmp < 0;
                    default -> false; 
                });
            }
            case "&&": {
                boolean l = asBool(left);
                boolean r = asBool(right);
                return new LValueBool(l && r);
            }

            default:
                throw new UnsupportedOperationException("Operador binário não implementado ou tipos incompatíveis: " + op);
        }
    }

    private LValue getLValue(br.ufjf.lang.compiler.ast.LValue lvalue, Stack<Map<String, LValue>> scopes) {
        // Acesso a variáveis simples
        if (lvalue instanceof LValueVar v) {
            // Procura a variável na pilha, do escopo mais interno (topo) ao mais externo (base).
            for (int i = scopes.size() - 1; i >= 0; i--) {
                Map<String, LValue> currentScope = scopes.get(i);
                if (currentScope.containsKey(v.name)) {
                    return currentScope.get(v.name);
                }
            }
            throw new RuntimeException("Variável não encontrada: " + v.name);
        }

        // Acesso a arrays
        if (lvalue instanceof LValueArrayAccess arrAccess) {
            LValue baseValue = getLValue(arrAccess.array, scopes);
            if (!(baseValue instanceof LValueArray array)) {
                throw new RuntimeException("Tentativa de acesso por índice em um tipo que não é array.");
            }

            LValue indexValue = evalExpr(arrAccess.index, scopes);
            if (!(indexValue instanceof LValueInt i)) {
                throw new RuntimeException("O índice de um array deve ser um inteiro.");
            }

            return array.get(i.value);
        }

        // Acesso a records
        if (lvalue instanceof LValueRecordAccess recAccess) {
            LValue baseValue = getLValue(recAccess.record, scopes);
            if (!(baseValue instanceof LValueRecord record)) {
                throw new RuntimeException("Tentativa de acesso a campo em um tipo que não é registro.");
            }

            return record.get(recAccess.field);
        }

        throw new UnsupportedOperationException("Tipo de LValue não suportado para leitura.");

    }

    private void setLValue(br.ufjf.lang.compiler.ast.LValue lvalue, LValue val, Stack<Map<String, LValue>> scopes) {
        if (lvalue instanceof LValueVar v) {
            // Procura a variável para atualizá-la, começando do escopo mais interno.
            for (int i = scopes.size() - 1; i >= 0; i--) {
                if (scopes.get(i).containsKey(v.name)) {
                    scopes.get(i).put(v.name, val);
                    return;
                }
            }
            // Se não encontrou em nenhum escopo, declara a variável no escopo atual (topo da pilha).
            scopes.peek().put(v.name, val);
            return;
        }

        if (lvalue instanceof LValueArrayAccess arrAccess) {
        
            LValue baseValue = getLValue(arrAccess.array, scopes);
            if (!(baseValue instanceof LValueArray array)) {
                throw new RuntimeException("Tentativa de atribuição por índice em um tipo que não é array.");
            }

            LValue indexValue = evalExpr(arrAccess.index, scopes);
            if (!(indexValue instanceof LValueInt i)) {
                throw new RuntimeException("O índice de um array deve ser um inteiro.");
            }

            array.set(i.value, val);
            return;
        }

        if (lvalue instanceof LValueRecordAccess recAccess) {
            LValue baseValue = getLValue(recAccess.record, scopes);
            if (!(baseValue instanceof LValueRecord record)) {
                throw new RuntimeException("Tentativa de atribuição a campo em um tipo que não é registro.");
            }

            record.set(recAccess.field, val);
            return;
        }

        throw new UnsupportedOperationException("Tipo de LValue não suportado para atribuição.");
    }

    private LValue inferFromInput(String line) {
        try {
            return new LValueInt(Integer.parseInt(line));
        } catch (NumberFormatException ignored) {}

        try {
            return new LValueFloat(Double.parseDouble(line));
        } catch (NumberFormatException ignored) {}

        if (line.equals("true") || line.equals("false")) {
            return new LValueBool(Boolean.parseBoolean(line));
        }

        if (line.length() == 1) {
            return new LValueChar(line.charAt(0));
        }

        throw new RuntimeException("Não foi possível inferir o tipo da entrada: " + line);
    }

    private Type getTypeOf(LValue val) {
        if (val instanceof LValueInt) return new TypeBase("Int");
        if (val instanceof LValueFloat) return new TypeBase("Float");
        if (val instanceof LValueBool) return new TypeBase("Bool");
        if (val instanceof LValueChar) return new TypeBase("Char");
        return new TypeBase("Unknown");
    }

    private boolean asBool(LValue v) {
        if (v instanceof LValueBool b) return b.value;
        if (v instanceof LValueInt i) return i.value != 0;
        if (v instanceof LValueFloat f) return f.value != 0.0;
        if (v instanceof LValueChar c) return c.value != '\0';
        if (v instanceof LValueNull) return false;
        return true;
    }
}