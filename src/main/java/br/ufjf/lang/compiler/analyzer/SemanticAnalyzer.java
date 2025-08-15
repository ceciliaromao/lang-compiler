package br.ufjf.lang.compiler.analyzer;

import br.ufjf.lang.compiler.ast.*;
import java.util.HashMap;
import java.util.Map;

public class SemanticAnalyzer {

    private Map<String, FunDef> functionTable;
    private SymbolTable table = new SymbolTable();
    
    public void analyze(Program program) {
        System.out.println("Análise semântica iniciada...");

        functionTable = new HashMap<>();
        
        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                if (functionTable.containsKey(fun.name)) {
                    throw new SemanticError("Erro: Função '" + fun.name + "' já foi definida.");
                }
                functionTable.put(fun.name, fun);
            }
        }

        for (Def def : program.definitions) {
            if (def instanceof FunDef fun) {
                analyzeFunction(fun, table); 
            }
        }
        
        System.out.println("Análise semântica concluída com sucesso!");
    }

    private Type checkExpr(Expr expr, SymbolTable table) {
        
        // Casos base: literais
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
            lvalExpr.type = lvalType;
            return lvalType;
        }

        if (expr instanceof ExprBinary b) {

            Type leftType = checkExpr(b.left, table);
            Type rightType = checkExpr(b.right, table);

            Type resultType = null;

            switch (b.op) {
                case "+", "-", "*", "/":
                    if (leftType.equals(new TypeBase("Int")) && rightType.equals(new TypeBase("Int"))) {
                        resultType = new TypeBase("Int");
                    } else if (leftType.equals(new TypeBase("Float")) && rightType.equals(new TypeBase("Float"))) {
                        resultType = new TypeBase("Float");
                    } else {
                        throw new SemanticError("Operador '" + b.op + "' não pode ser aplicado a tipos " + leftType + " e " + rightType);
                    }
                    break;
                
                case "==", "!=", "<":
                    // Comparações são permitidas entre tipos iguais (Int, Float, Char)
                    if (leftType.equals(rightType) && (leftType.isA("Int") || leftType.isA("Float") || leftType.isA("Char"))) {
                        resultType = new TypeBase("Bool");
                    } else {
                        throw new SemanticError("Operador de comparação '" + b.op + "' não pode ser aplicado a tipos " + leftType + " e " + rightType);
                    }
                    break;

                case "&&":
                    if (leftType.isA("Bool") && rightType.isA("Bool")) {
                        resultType = new TypeBase("Bool");
                    } else {
                        throw new SemanticError("Operador lógico '&&' requer operandos do tipo Bool.");
                    }
                    break;
            }

            b.type = resultType;
            return b.type;
        }

        if (expr instanceof ExprUnary u) {
            // Verifica recursivamente o tipo da expressão interna
            Type innerType = checkExpr(u.expr, table);
            Type resultType = null;

            switch (u.op) {
                case "!":
                    if (!innerType.isA("Bool")) {
                        throw new SemanticError("Operador '!' só pode ser aplicado a operandos do tipo Bool.");
                    }
                    resultType = new TypeBase("Bool");
                    break;
                case "-":
                    if (!innerType.isA("Int") && !innerType.isA("Float")) {
                        throw new SemanticError("Operador '-' só pode ser aplicado a operandos do tipo Int ou Float.");
                    }
                    resultType = innerType; // O tipo do resultado é o mesmo do operando
                    break;
                default:
                    throw new SemanticError("Operador unário desconhecido: " + u.op);
            }

            u.type = resultType; // Decora o nó
            return u.type;
        }

        if (expr instanceof ExprCall call) {

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

            Type indexType = checkExpr(call.index, table);
            if (!indexType.isA("Int")) {
                throw new SemanticError("O índice de acesso ao retorno de uma função deve ser um inteiro.");
            }
            // (Uma verificação mais avançada poderia checar se o valor do índice é constante e está dentro dos limites)

            // TODO: Acessar o valor numérico do índice para verificar os limites.
            // Por enquanto, vamos assumir que o índice 0 é válido se houver retornos.
            if (fun.returnTypes.isEmpty()) {
                throw new SemanticError("Função '" + fun.name + "' não retorna nenhum valor e não pode ser usada em uma expressão.");
            }
            Type returnType = fun.returnTypes.get(0); // Simplificação, o ideal é usar o valor do índice

            call.type = returnType; // Decora o nó
            return call.type;
        }
        
        throw new SemanticError("Tipo de expressão não reconhecido: " + expr.getClass().getSimpleName());
    }

    private Type checkLValue(LValue lval, SymbolTable table) {
        if (lval instanceof LValueVar var) {
            Type varType = table.find(var.name);
            if (varType == null) {
                throw new SemanticError("Erro: Variável '" + var.name + "' não declarada.");
            }
            var.type = varType; // Decora o nó
            return varType;
        }
        
        if (lval instanceof LValueArrayAccess arrAccess) {
            Type baseType = checkLValue(arrAccess.array, table);
            if (!(baseType instanceof TypeArray arrayType)) {
                throw new SemanticError("Tentativa de acesso por índice em um tipo que não é um array.");
            }

            Type indexType = checkExpr(arrAccess.index, table);
            if (!indexType.isA("Int")) {
                throw new SemanticError("O índice de um array deve ser um inteiro.");
            }

            Type resultType = arrayType.elementType;
            arrAccess.type = resultType; // Decora o nó
            return arrAccess.type;
        }

        throw new SemanticError("Verificação de tipo não implementada para a expressão: " + lval.getClass().getSimpleName());
    }

    private void analyzeFunction(FunDef fun, SymbolTable table) {
        table.enterScope();

        // Adiciona os parâmetros da função à tabela de símbolos
        for (FunDef.Param p : fun.params) {
            table.add(p.name(), p.type());
        }

        // Analisa o corpo da função
        checkCmd(fun.body, table);

        table.leaveScope();
    }

    private void checkCmd(Cmd cmd, SymbolTable table) {
        if (cmd instanceof CmdAssign assign) {
            // Verifica o tipo do alvo e da expressão
            Type targetType = checkLValue(assign.target, table);
            Type valueType = checkExpr(assign.value, table);

            // Verifica se os tipos são compatíveis para atribuição
            if (!targetType.equals(valueType)) {
                // (Numa versão mais avançada, aqui se verificaria se valueType é um subtipo de targetType)
                throw new SemanticError("Erro de tipo: não é possível atribuir " + valueType + " a " + targetType);
            }
            return;
        }

        if (cmd instanceof CmdIf iff) {
            Type condType = checkExpr(iff.condition, table);
            if (!condType.isA("Bool")) {
                throw new SemanticError("Erro de tipo: a condição do 'if' deve ser do tipo Bool, mas foi recebido " + condType);
            }
            // Verifica os corpos do 'then' e do 'else'
            checkCmd(iff.thenBranch, table);
            if (iff.elseBranch != null) {
                checkCmd(iff.elseBranch, table);
            }
            return;
        }
    }
}