//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C

package br.ufjf.lang.compiler.interpreter;

import br.ufjf.lang.compiler.ast.*;
import java.util.*;

public class Interpreter {

    private final Map<String, FunDef> functionTable = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void run(Program program) {
        // carrega as funções no functionTable
        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                functionTable.put(fun.name, fun);
            }
        }

        // printa functionTable
        System.out.println("Funções disponíveis:");
        for (String funName : functionTable.keySet()) {
            System.out.println(" - " + funName);
        }

        // procura a função main
        FunDef mainFun = functionTable.get("main");
        if (mainFun == null) {
            throw new RuntimeException("Função main não encontrada!");
        }

        // executa a função main
        executeFunction(mainFun, List.of(), new HashMap<>());
    }

    private List<LValue> executeFunction(FunDef fun, List<LValue> args, Map<String, LValue> globals) {
        // cria um ambiente local para os parâmetros
        Map<String, LValue> locals = new HashMap<>(globals);

        List<FunDef.Param> params = fun.params;
        for (int i = 0; i < params.size(); i++) {
            locals.put(params.get(i).name(), args.get(i));
        }

        // executa o corpo
        return executeCmd(fun.body, locals, globals);
    }

    private List<LValue> executeCmd(Cmd cmd, Map<String, LValue> locals, Map<String, LValue> globals) {

        if (cmd instanceof CmdBlock block) {
            for (Cmd c : block.commands) {
                List<LValue> ret = executeCmd(c, locals, globals);
                if (ret != null) return ret;
            }
            return null;
        }

        if (cmd instanceof CmdPrint p) {
            LValue v = evalExpr(p.expr, locals, globals);
            if (v instanceof LValueInt i) System.out.println(i.value);
            if (v instanceof LValueBool b) System.out.println(b.value);
            if (v instanceof LValueFloat f) System.out.println(f.value);
            if (v instanceof LValueChar c) System.out.println(c.value);
            if (v instanceof LValueNull n) System.out.println("null");
            return null;
        }

        if (cmd instanceof CmdReturn r) {
            List<LValue> values = new ArrayList<>();
            for (Expr e : r.values) {
                values.add(evalExpr(e, locals, globals));
            }
            return values;
        }

        if (cmd instanceof CmdAssign assign) {
            LValue val = evalExpr(assign.value, locals, globals);
            setLValue(assign.target, val, locals, globals);
            return null;
        }

        if (cmd instanceof CmdIf iff) {
            LValue cond = evalExpr(iff.condition, locals, globals);
            if (asBool(cond)) {
                return executeCmd(iff.thenBranch, locals, globals);
            } else if (iff.elseBranch != null) {
                return executeCmd(iff.elseBranch, locals, globals);
            }
            return null;
        }

        if (cmd instanceof CmdIterate loop) {
            while (true) {
                System.out.println("whileloop");
                LValue cond = evalExpr(loop.range, locals, globals);
                if (!asBool(cond)) break;
                executeCmd(loop.body, locals, globals);
            }
            return null;
        }

        if (cmd instanceof CmdRead read) {
            System.out.print("> ");
            String line = scanner.nextLine();
        
            // verifica o tipo da variável antes de converter
            if (read.target instanceof LValueVar var) {
                if (var.type == null) {
                    // Inferir tipo a partir da entrada
                    LValue inferredVal = inferFromInput(line);
                    var.type = getTypeOf(inferredVal);
                    locals.put(var.name, inferredVal);
                } else if (var.type instanceof TypeBase baseType) {
                    switch (baseType.name) {
                        case "Int" -> locals.put(var.name, new LValueInt(Integer.parseInt(line)));
                        case "Char" -> {
                            if (line.length() != 1) throw new RuntimeException("Esperado um único caractere, obtido: " + line);
                            locals.put(var.name, new LValueChar(line.charAt(0)));
                        }
                        case "Float" -> locals.put(var.name, new LValueFloat(Double.parseDouble(line)));
                        case "Bool" -> {
                            if (!line.equals("true") && !line.equals("false")) throw new RuntimeException("Esperado 'true' ou 'false', obtido: " + line);
                            locals.put(var.name, new LValueBool(Boolean.parseBoolean(line)));
                        }
                        default -> throw new RuntimeException("Tipo não suportado: " + baseType.name);
                    }
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

            System.out.println("Chamando função: " + call.functionName);
            if (callee == null) {
                // Tenta encontrar a função no escopo global
                callee = globals.get(call.functionName) instanceof FunDef ? (FunDef) globals.get(call.functionName) : null;
            }

            //if (callee == null) throw new RuntimeException("Função não encontrada: " + call.functionName);

            List<LValue> args = new ArrayList<>();
            for (Expr arg : call.arguments) {
                args.add(evalExpr(arg, locals, globals));
            }

            List<LValue> results = executeFunction(callee, args, globals);

            if (call.outputTargets != null && results != null) {
                for (int i = 0; i < call.outputTargets.size(); i++) {
                    setLValue(call.outputTargets.get(i), results.get(i), locals, globals);
                }
            }
            return null;
        }

        throw new RuntimeException("Comando não suportado: " + cmd);
    }

    private LValue evalExpr(Expr expr, Map<String, LValue> locals, Map<String, LValue> globals) {
        if (expr instanceof ExprInt i) return new LValueInt(i.value);
        if (expr instanceof ExprBool b) return new LValueBool(b.value);
        if (expr instanceof ExprFloat f) return new LValueFloat(f.value);
        if (expr instanceof ExprChar c) return new LValueChar(c.value);
        if (expr instanceof ExprNull) return new LValueNull();

        if (expr instanceof ExprUnary u) {
            LValue val = evalExpr(u.expr, locals, globals);
            return evalUnary(u.op, val);
        }

        if (expr instanceof ExprBinary b) {
            LValue l = evalExpr(b.left, locals, globals);
            LValue r = evalExpr(b.right, locals, globals);
            return evalBinary(b.op, l, r);
        }

        if (expr instanceof ExprLValue lval) {
            return getLValue(lval.lvalue, locals, globals);
        }

        if (expr instanceof ExprCall call) {
            FunDef callee = functionTable.get(call.functionName);

            System.out.println("Chamando função: " + call.functionName);    
            if (callee == null) {
                // Tenta encontrar a função no escopo global
                callee = globals.get(call.functionName) instanceof FunDef ? (FunDef) globals.get(call.functionName) : null;
            }
            if (callee == null) throw new RuntimeException("Função não encontrada: " + call.functionName);
            
            List<LValue> args = new ArrayList<>();
            for (Expr arg : call.arguments) {
                args.add(evalExpr(arg, locals, globals));
            }

            List<LValue> results = executeFunction(callee, args, globals);
            if (results != null && !results.isEmpty()) {
                return results.get(0);
            }

            throw new RuntimeException("A função não possui retorno.");
        }

        throw new RuntimeException("Expressão não suportada: " + expr);
    }

    private LValue evalUnary(String op, LValue val) {
        // Implementar de verdade depois
        throw new UnsupportedOperationException("Unary op não implementado: " + op);
    }

    private LValue evalBinary(String op, LValue left, LValue right) {

        switch (op) {
            case "+", "-", "*", "/", "%":
            // Operações aritméticas
                if (left instanceof LValueInt li && right instanceof LValueInt ri) {
                    return new LValueInt(switch (op) {
                        case "*" -> li.value * ri.value;
                        case "/" -> li.value / ri.value;
                        case "%" -> li.value % ri.value;
                        case "+" -> li.value + ri.value;
                        case "-" -> li.value - ri.value;
                        default -> throw new UnsupportedOperationException("Operador não suportado: " + op);
                    });
                } else if (left instanceof LValueFloat lf && right instanceof LValueFloat rf) {
                    return new LValueFloat(switch (op) {
                        case "*" -> lf.value * rf.value;
                        case "/" -> lf.value / rf.value;
                        case "%" -> lf.value % rf.value;
                        case "+" -> lf.value + rf.value;
                        case "-" -> lf.value - rf.value;
                        default -> throw new UnsupportedOperationException("Operador não suportado: " + op);
                    });
                } 
                throw new RuntimeException("Tipos incompatíveis para operação: " + left + " " + op + " " + right);
            
                case "==", "!=", "<":
                // Operações de comparação
                if (left instanceof LValueInt li && right instanceof LValueInt ri) {
                    return new LValueBool(switch (op) {
                        case "==" -> li.value == ri.value;  
                        case "!=" -> li.value != ri.value;
                        case "<" -> li.value < ri.value;
                        default -> throw new UnsupportedOperationException("Operador não suportado: " + op);
                    });
                }
            case "&&":
                // Operação lógica AND
                if (left instanceof LValueBool lb && right instanceof LValueBool rb) {
                    return new LValueBool(lb.value && rb.value);
                }
            default:
                return new LValueBool(false); // Retorna falso para operadores não implementados
        }

    }

    private LValue getLValue(LValue lvalue, Map<String, LValue> locals, Map<String, LValue> globals) {
        if (lvalue instanceof LValueVar v) {
            if (locals.containsKey(v.name)) return locals.get(v.name);
            if (globals.containsKey(v.name)) return globals.get(v.name);
            throw new RuntimeException("Variável não encontrada: " + v.name);
        }
        
        throw new UnsupportedOperationException("Acesso LValue complexo não implementado");
    }

    private void setLValue(LValue lvalue, LValue val, Map<String, LValue> locals, Map<String, LValue> globals) {
        // Verifica se o LValue é uma variável
        if (lvalue instanceof LValueVar v) {
            // Verifica se o tipo do valor é compatível com o tipo da variável
            if (v.type instanceof TypeBase baseType) {
                switch (baseType.name) {
                    case "Int" -> {
                        if (!(val instanceof LValueInt)) {
                            throw new RuntimeException("Tipo incompatível: esperado 'int', obtido: " + val.getClass().getSimpleName());
                        }
                    }
                    case "Char" -> {
                        if (!(val instanceof LValueChar)) {
                            throw new RuntimeException("Tipo incompatível: esperado 'char', obtido: " + val.getClass().getSimpleName());
                        }
                    }
                    case "Float" -> {
                        if (!(val instanceof LValueFloat)) {
                            throw new RuntimeException("Tipo incompatível: esperado 'float', obtido: " + val.getClass().getSimpleName());
                        }
                    }
                    case "Bool" -> {
                        if (!(val instanceof LValueBool)) {
                            throw new RuntimeException("Tipo incompatível: esperado 'bool', obtido: " + val.getClass().getSimpleName());
                        }
                    }
                    default -> throw new RuntimeException("Tipo não suportado: " + baseType.name);
                }
            } else {
                throw new RuntimeException("Tipo da variável não reconhecido: " + v.type);
            }
    
            // Atribui o valor à variável no escopo local
            locals.put(v.name, val);
            return;
        }
    
        // Caso o LValue não seja uma variável, lança uma exceção
        throw new UnsupportedOperationException("Atribuição LValue complexo não implementado");
    }

    private LValue inferFromInput(String line) {
        // Tenta inferir int
        try {
            return new LValueInt(Integer.parseInt(line));
        } catch (NumberFormatException ignored) {}
    
        // Tenta inferir float
        try {
            return new LValueFloat(Double.parseDouble(line));
        } catch (NumberFormatException ignored) {}
    
        // Tenta inferir bool
        if (line.equals("true") || line.equals("false")) {
            return new LValueBool(Boolean.parseBoolean(line));
        }
    
        // Tenta inferir char
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
        throw new RuntimeException("Esperado Bool, obtido: " + v);
    }
}
