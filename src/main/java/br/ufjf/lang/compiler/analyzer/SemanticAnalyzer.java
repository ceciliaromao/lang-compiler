package br.ufjf.lang.compiler.analyzer;

import br.ufjf.lang.compiler.ast.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SemanticAnalyzer {

    private Map<String, FunDef> functionTable;
    private Map<String, DataDef> recordTable;
    private SymbolTable table;

    public void analyze(Program program) {
        System.out.println("Análise semântica iniciada...");

        functionTable = new HashMap<>();
        recordTable = new HashMap<>();
        table = new SymbolTable();
        
        // 1º PASSE: Coleta de declarações globais (funções e tipos)
        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                if (functionTable.containsKey(fun.name)) {
                    throw new SemanticError("Erro: Função '" + fun.name + "' já foi definida.");
                }
                functionTable.put(fun.name, fun);
            }
            else if (def instanceof DataDef data) {
                if (recordTable.containsKey(data.name)) {
                    throw new SemanticError("Erro: O tipo '" + data.name + "' já foi definido.");
                }
                Set<String> fieldNames = new HashSet<>();
                for (var field : data.fields) {
                    if (!fieldNames.add(field.name)) {
                        throw new SemanticError("Erro: Campo '" + field.name + "' duplicado na definição do tipo '" + data.name + "'.");
                    }
                }
                recordTable.put(data.name, data);
            }
        }

        // Validação da função 'main'
        FunDef mainFun = functionTable.get("main");
        if (mainFun == null) {
            throw new SemanticError("Erro: Função 'main' obrigatória não foi encontrada no programa.");
        }
        if (!mainFun.params.isEmpty() || !mainFun.returnTypes.isEmpty()) {
            throw new SemanticError("Erro: A função 'main' deve ser um procedimento sem argumentos e sem valor de retorno.");
        }

        // 2º PASSE: Análise do corpo das funções
        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                analyzeFunction(fun, table); 
            }
        }
        
        System.out.println("Análise semântica concluída com sucesso!");
    }

    private void analyzeFunction(FunDef fun, SymbolTable table) {
        table.enterScope();

        for (FunDef.Param p : fun.params) {
            table.add(p.name(), p.type());
        }

        checkCmd(fun.body, table, fun.returnTypes);

        if (!fun.returnTypes.isEmpty()) {
            if (!guaranteesReturn(fun.body)) {
                throw new SemanticError("Erro na função '" + fun.name + "': nem todos os caminhos de controle retornam um valor.");
            }
        }

        table.leaveScope();
    }

    private void checkCmd(Cmd cmd, SymbolTable table, List<Type> expectedReturnTypes) {
        if (cmd == null) return;

        if (cmd instanceof CmdBlock block) {
            table.enterScope();
            for (Cmd c : block.commands) {
                checkCmd(c, table, expectedReturnTypes);
            }
            table.leaveScope();
            return;
        }

        if (cmd instanceof CmdAssign assign) {
            Type valueType = checkExpr(assign.value, table);
            Type targetType = checkLValue(assign.target, table);

            if (targetType != null) { // Atribuição a variável existente
                if (!targetType.equals(valueType)) {
                    throw new SemanticError("Erro de tipo: não é possível atribuir um valor do tipo " + valueType + " a uma variável existente do tipo " + targetType);
                }
            } else { // Declaração por inferência
                if (!(assign.target instanceof LValueVar var)) {
                    throw new SemanticError("Erro: Apenas variáveis simples podem ser declaradas por inferência.");
                }
                table.add(var.name, valueType);
                var.type = valueType;
            }
            return;
        }

        if (cmd instanceof CmdIf iff) {
            Type condType = checkExpr(iff.condition, table);
            if (!condType.isA("Bool")) {
                throw new SemanticError("Erro de tipo: a condição do 'if' deve ser do tipo Bool, mas foi recebido " + condType);
            }
            checkCmd(iff.thenBranch, table, expectedReturnTypes);
            if (iff.elseBranch != null) {
                checkCmd(iff.elseBranch, table, expectedReturnTypes);
            }
            return;
        }

        if (cmd instanceof CmdPrint p) {
            Type exprType = checkExpr(p.expr, table);
            if (!exprType.isA("Int") && !exprType.isA("Float") && !exprType.isA("Char") && !exprType.isA("Bool")) {
                throw new SemanticError("Erro: O comando 'print' não pode ser usado com o tipo " + exprType + ".");
            }
            return;
        }

        if (cmd instanceof CmdIterate loop) {
            Type rangeType = checkExpr(loop.range, table);
            if (rangeType.isA("Int")) {
                table.enterScope();
                if (loop.varName != null) {
                    Type existingVarType = table.find(loop.varName);
                    if (existingVarType != null && !existingVarType.isA("Int")) {
                        throw new SemanticError("Erro: A variável do iterate '" + loop.varName + "' sombreia uma variável externa de tipo incompatível (" + existingVarType + ").");
                    }
                    table.add(loop.varName, new TypeBase("Int"));
                }
                checkCmd(loop.body, table, expectedReturnTypes);
                table.leaveScope();
            } else if (rangeType instanceof TypeArray arrayType) {
                if (loop.varName == null) {
                    throw new SemanticError("Erro: Para iterar sobre um array, é necessário fornecer uma variável (ex: 'iterate(elem: meuArray)').");
                }
                table.enterScope();
                table.add(loop.varName, arrayType.elementType);
                checkCmd(loop.body, table, expectedReturnTypes);
                table.leaveScope();
            } else {
                throw new SemanticError("Erro: A expressão do comando 'iterate' deve ser do tipo Int ou um array, mas foi recebido " + rangeType);
            }
            return;
        }

        if (cmd instanceof CmdCall call) {
            FunDef fun = functionTable.get(call.functionName);
            if (fun == null) {
                throw new SemanticError("Função '" + call.functionName + "' não foi declarada.");
            }
            if (fun.params.size() != call.arguments.size()) {
                throw new SemanticError("Função '" + fun.name + "' espera " + fun.params.size() + " argumentos, mas recebeu " + call.arguments.size() + ".");
            }
            for (int i = 0; i < call.arguments.size(); i++) {
                Type argType = checkExpr(call.arguments.get(i), table);
                Type paramType = fun.params.get(i).type();
                if (!argType.equals(paramType)) {
                    throw new SemanticError("Erro de tipo no argumento " + i + " da função '" + fun.name + "'. Esperado " + paramType + ", mas recebido " + argType + ".");
                }
            }
            if (call.outputTargets != null && !call.outputTargets.isEmpty()) {
                if (fun.returnTypes.size() != call.outputTargets.size()) {
                    throw new SemanticError("Função '" + fun.name + "' retorna " + fun.returnTypes.size() + " valores, mas a chamada tenta atribuir a " + call.outputTargets.size() + " variáveis.");
                }
                for (int i = 0; i < call.outputTargets.size(); i++) {
                    var targetLval = call.outputTargets.get(i);
                    Type expectedType = fun.returnTypes.get(i);
                    Type targetType = checkLValue(targetLval, table);
                    if (targetType != null) {
                        if (!targetType.equals(expectedType)) {
                            throw new SemanticError("Erro de tipo no retorno " + i + " da função '" + fun.name + "'. A variável existente é do tipo " + targetType + ", mas o valor esperado é " + expectedType + ".");
                        }
                    } else {
                        if (!(targetLval instanceof LValueVar var)) {
                            throw new SemanticError("Erro: Apenas variáveis simples podem ser declaradas na recepção de retornos de função.");
                        }
                        table.add(var.name, expectedType);
                        var.type = expectedType;
                    }
                }
            }
            return;
        }
        
        if (cmd instanceof CmdReturn r) {
            if (r.values.size() != expectedReturnTypes.size()) {
                throw new SemanticError("Erro: A função espera " + expectedReturnTypes.size() + " valores de retorno, mas foram fornecidos " + r.values.size() + ".");
            }
            for (int i = 0; i < r.values.size(); i++) {
                Type actualType = checkExpr(r.values.get(i), table);
                Type expectedType = expectedReturnTypes.get(i);
                if (!actualType.equals(expectedType)) {
                    throw new SemanticError("Erro de tipo no valor de retorno " + i + ". Esperado " + expectedType + ", mas foi recebido " + actualType + ".");
                }
            }
            return;
        }

        // Se chegar aqui, o tipo de comando não foi tratado
        throw new SemanticError("Análise semântica não implementada para o comando: " + cmd.getClass().getSimpleName());
    }

    private Type checkExpr(Expr expr, SymbolTable table) {
        if (expr == null) return null; // Não deveria acontecer com uma AST bem formada

        if (expr instanceof ExprInt i) {
            i.type = new TypeBase("Int");
            return i.type;
        }
        if (expr instanceof ExprFloat f) {
            f.type = new TypeBase("Float");
            return f.type;
        }
        if (expr instanceof ExprBool b) {
            b.type = new TypeBase("Bool");
            return b.type;
        }
        if (expr instanceof ExprChar c) {
            c.type = new TypeBase("Char");
            return c.type;
        }
        if (expr instanceof ExprNull n) {
            n.type = new TypeBase("Null");
            return n.type;
        }

        if (expr instanceof ExprLValue lvalExpr) {
            Type lvalType = checkLValue(lvalExpr.lvalue, table);
            // **A VERIFICAÇÃO CRÍTICA QUE IMPEDE O NULLPOINTEREXCEPTION**
            if (lvalType == null) {
                if (lvalExpr.lvalue instanceof LValueVar var) {
                    throw new SemanticError("Erro: A variável '" + var.name + "' não foi declarada antes de ser usada.");
                } else {
                    throw new SemanticError("Erro: Tentativa de usar uma variável não declarada em uma expressão.");
                }
            }
            lvalExpr.type = lvalType;
            return lvalType;
        }
        
        if (expr instanceof ExprParen p) {
            Type innerType = checkExpr(p.inner, table);
            p.type = innerType; 
            return p.type;
        }

        if (expr instanceof ExprUnary u) {
            Type innerType = checkExpr(u.expr, table);
            Type resultType;
            switch (u.op) {
                case "!":
                    if (!innerType.isA("Bool")) { throw new SemanticError("Operador '!' só pode ser aplicado a operandos do tipo Bool."); }
                    resultType = new TypeBase("Bool");
                    break;
                case "-":
                    if (!innerType.isA("Int") && !innerType.isA("Float")) { throw new SemanticError("Operador '-' só pode ser aplicado a operandos do tipo Int ou Float."); }
                    resultType = innerType;
                    break;
                default:
                    throw new SemanticError("Operador unário desconhecido: " + u.op);
            }
            u.type = resultType;
            return u.type;
        }

        if (expr instanceof ExprBinary b) {
            Type leftType = checkExpr(b.left, table);
            Type rightType = checkExpr(b.right, table);
            Type resultType;
            switch (b.op) {
                case "+", "-", "*", "/":
                    if (leftType.isA("Int") && rightType.isA("Int")) { resultType = new TypeBase("Int"); }
                    else if (leftType.isA("Float") && rightType.isA("Float")) { resultType = new TypeBase("Float"); }
                    else { throw new SemanticError("Operador aritmético '" + b.op + "' não pode ser aplicado aos tipos " + leftType + " e " + rightType); }
                    break;
                case "%":
                     if (leftType.isA("Int") && rightType.isA("Int")) { resultType = new TypeBase("Int"); }
                     else { throw new SemanticError("Operador '%' só pode ser aplicado a tipos Int."); }
                     break;
                case "==", "!=", "<":
                    if (leftType.equals(rightType) && (leftType.isA("Int") || leftType.isA("Float") || leftType.isA("Char"))) {
                        resultType = new TypeBase("Bool");
                    } else { throw new SemanticError("Operador de comparação '" + b.op + "' não pode ser aplicado aos tipos " + leftType + " e " + rightType); }
                    break;
                case "&&":
                    if (leftType.isA("Bool") && rightType.isA("Bool")) { resultType = new TypeBase("Bool"); }
                    else { throw new SemanticError("Operador lógico '&&' requer operandos do tipo Bool."); }
                    break;
                default:
                     throw new SemanticError("Operador binário desconhecido: " + b.op);
            }
            b.type = resultType;
            return b.type;
        }
        
        if (expr instanceof ExprNew n) {
            if (n.size != null) {
                Type sizeType = checkExpr(n.size, table);
                if (!sizeType.isA("Int")) {
                    throw new SemanticError("Erro: O tamanho de um array deve ser um inteiro, mas foi recebido o tipo " + sizeType + ".");
                }
                Type resultType = new TypeArray(n.typeToCreate);
                n.type = resultType;
                return n.type;
            } else {
                if (!(n.typeToCreate instanceof TypeBase typeBase)) {
                    throw new SemanticError("Erro: 'new' só pode ser usado com nomes de tipos de registro.");
                }
                if (!recordTable.containsKey(typeBase.name)) {
                    throw new SemanticError("Erro: Tentativa de instanciar um tipo de registro desconhecido: '" + typeBase.name + "'.");
                }
                n.type = typeBase;
                return n.type;
            }
        }
        
        if (expr instanceof ExprCall call) {
            FunDef fun = functionTable.get(call.functionName);
            if (fun == null) { throw new SemanticError("Função '" + call.functionName + "' não foi declarada."); }
            if (fun.params.size() != call.arguments.size()) { throw new SemanticError("Função '" + fun.name + "' espera " + fun.params.size() + " argumentos, mas recebeu " + call.arguments.size() + "."); }
            for (int i = 0; i < call.arguments.size(); i++) {
                Type argType = checkExpr(call.arguments.get(i), table);
                Type paramType = fun.params.get(i).type();
                if (!argType.equals(paramType)) {
                    throw new SemanticError("Erro de tipo no argumento " + i + " da função '" + fun.name + "'. Esperado " + paramType + ", mas recebido " + argType + ".");
                }
            }
            Type indexType = checkExpr(call.index, table);
            if (!indexType.isA("Int")) { throw new SemanticError("O índice de acesso ao retorno de uma função deve ser um inteiro."); }
            
            if (fun.returnTypes.isEmpty()) { throw new SemanticError("Função '" + fun.name + "' não retorna nenhum valor e não pode ser usada em uma expressão."); }
            
            // Verificação estática do índice, se for uma constante
            int returnIndex = -1;
            if(call.index instanceof ExprInt intLiteral) {
                returnIndex = intLiteral.value;
            }
            if(returnIndex != -1 && returnIndex >= fun.returnTypes.size()) {
                throw new SemanticError("Índice de retorno " + returnIndex + " está fora dos limites para a função '" + call.functionName + "', que retorna " + fun.returnTypes.size() + " valores.");
            }
            
            // Se o índice não for constante, só podemos pegar o tipo de forma genérica.
            // Para lang, como todos os retornos são definidos, podemos pegar pelo índice se for constante.
            // Se não for constante, a verificação em tempo de execução falharia. Para a análise estática,
            // temos que assumir que o tipo é o declarado.
            Type returnType = fun.returnTypes.get(returnIndex >= 0 ? returnIndex : 0); // Simplificação: assume 0 se não for constante
            
            call.type = returnType;
            return call.type;
        }

        throw new SemanticError("Verificação de tipo não implementada para a expressão: " + expr.getClass().getSimpleName());
    }

    private Type checkLValue(br.ufjf.lang.compiler.ast.LValue lval, SymbolTable table) {
        if (lval instanceof LValueVar var) {
            Type varType = table.find(var.name);
            if (varType != null) {
                var.type = varType;
            }
            return varType;
        }
        if (lval instanceof LValueArrayAccess arrAccess) {
            Type baseType = checkLValue(arrAccess.array, table);
            if(baseType == null) {
                 throw new SemanticError("Erro: Tentativa de acesso de array em variável não declarada.");
            }
            if (!(baseType instanceof TypeArray arrayType)) {
                throw new SemanticError("Tentativa de acesso por índice em um tipo que não é um array.");
            }
            Type indexType = checkExpr(arrAccess.index, table);
            if (!indexType.isA("Int")) {
                throw new SemanticError("O índice de um array deve ser um inteiro.");
            }
            Type resultType = arrayType.elementType;
            arrAccess.type = resultType;
            return arrAccess.type;
        }
        if (lval instanceof LValueRecordAccess recAccess) {
            Type baseType = checkLValue(recAccess.record, table);
            if(baseType == null) {
                 throw new SemanticError("Erro: Tentativa de acesso de campo em variável não declarada.");
            }
            if (!(baseType instanceof TypeBase recordType)) {
                 throw new SemanticError("Erro: Tentativa de acessar campo '.' em um tipo que não é um registro.");
            }
            DataDef recordDef = recordTable.get(recordType.name);
            if (recordDef == null) {
                throw new SemanticError("Erro: Tipo de registro '" + recordType.name + "' não foi definido.");
            }
            for (var field : recordDef.fields) {
                if (field.name.equals(recAccess.field)) {
                    recAccess.type = field.type;
                    return field.type;
                }
            }
            throw new SemanticError("Erro: O tipo '" + recordType.name + "' não possui um campo chamado '" + recAccess.field + "'.");
        }
        throw new SemanticError("Verificação de tipo não implementada para o LValue: " + lval.getClass().getSimpleName());
    }
    
    private boolean guaranteesReturn(Cmd cmd) {
        if (cmd instanceof CmdReturn) {
            return true;
        }
        if (cmd instanceof CmdBlock block) {
            if (block.commands.isEmpty()) {
                return false;
            }
            return guaranteesReturn(block.commands.get(block.commands.size() - 1));
        }
        if (cmd instanceof CmdIf iff) {
            if (iff.elseBranch == null) {
                return false;
            }
            return guaranteesReturn(iff.thenBranch) && guaranteesReturn(iff.elseBranch);
        }
        return false;
    }
}