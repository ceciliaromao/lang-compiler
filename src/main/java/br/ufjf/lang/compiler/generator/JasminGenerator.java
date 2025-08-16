package br.ufjf.lang.compiler.generator;

import br.ufjf.lang.compiler.ast.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class JasminGenerator {

    private StringBuilder code;
    private int labelCounter = 0;
    
    public String generate(Program program, String className) {
        code = new StringBuilder();

        // Cabeçalho padrão do Jasmin
        code.append(".class public ").append(className).append("\n");
        code.append(".super java/lang/Object\n\n");

        // Construtor padrão
        code.append(".method public <init>()V\n");
        code.append("    aload_0\n");
        code.append("    invokenonvirtual java/lang/Object/<init>()V\n");
        code.append("    return\n");
        code.append(".end method\n\n");

        // Encontra a função 'main' e a gera como o ponto de entrada
        FunDef mainFunction = null;
        for (Def def : program.definitions) {
            if (def instanceof FunDef && ((FunDef) def).name.equals("main")) {
                mainFunction = (FunDef) def;
                break;
            }
        }

        if (mainFunction != null) {
            visitMain(mainFunction);
        } else {
            throw new RuntimeException("Função 'main' não encontrada para geração de código Jasmin.");
        }

        return code.toString();
    }

    private void visitMain(FunDef fun) {
        code.append(".method public static main([Ljava/lang/String;)V\n");
        code.append("    .limit stack 10\n"); // Um valor inicial, pode precisar de ajuste
        code.append("    .limit locals 10\n"); // Mesma coisa para locais

        // O corpo da 'main' será gerado aqui
        visitCmd(fun.body, new HashMap<String, Integer>());

        code.append("    return\n");
        code.append(".end method\n");
    }

    private void visitCmd(Cmd command, Map<String, Integer> localVars) {
        // A implementação da visita aos comandos virá aqui
        if (command instanceof CmdPrint) {
            visitPrint((CmdPrint) command, localVars);
        } else if (command instanceof CmdBlock) {
            for (Cmd cmd : ((CmdBlock) command).commands) {
                visitCmd(cmd, localVars);
            }
        } else if (command instanceof CmdAssign) {
            visitAssign((CmdAssign) command, localVars);
        } else if (command instanceof CmdIf) {
            visitIf((CmdIf) command, localVars);
        }
        // Adicionar outros comandos aqui
    }

    private void visitPrint(CmdPrint print, Map<String, Integer> localVars) {
        code.append("    getstatic java/lang/System/out Ljava/io/PrintStream;\n");
        visitExpr(print.expr, localVars);
        
        Type type = ((ExprBase) print.expr).type;
        String descriptor = "Ljava/lang/String;";
        if (type.isA("Int")) descriptor = "I";
        else if (type.isA("Float")) descriptor = "F";
        else if (type.isA("Bool")) descriptor = "Z";
        else if (type.isA("Char")) descriptor = "C";

        code.append("    invokevirtual java/io/PrintStream/print(").append(descriptor).append(")V\n");
    }

    private void visitIf(CmdIf ifCmd, Map<String, Integer> localVars) {
        String elseLabel = newLabel();
        String endLabel = newLabel();

        // Avalia a condição
        visitExpr(ifCmd.condition, localVars);

        // Se a condição for falsa (0), pula para o bloco else (ou para o fim)
        code.append("    ifeq ").append(elseLabel).append("\n");

        // Bloco then
        visitCmd(ifCmd.thenBranch, localVars);
        code.append("    goto ").append(endLabel).append("\n");

        // Bloco else
        code.append(elseLabel).append(":\n");
        if (ifCmd.elseBranch != null) {
            visitCmd(ifCmd.elseBranch, localVars);
        }

        // Fim do if
        code.append(endLabel).append(":\n");
    }

    private void visitAssign(CmdAssign assign, Map<String, Integer> localVars) {
        if (!(assign.target instanceof LValueVar)) {
            // Por enquanto, só suportamos atribuição a variáveis simples
            return;
        }
        String varName = ((LValueVar) assign.target).name;
        visitExpr(assign.value, localVars);

        int varIndex;
        if (localVars.containsKey(varName)) {
            varIndex = localVars.get(varName);
        } else {
            varIndex = localVars.size(); // Próximo índice disponível
            localVars.put(varName, varIndex);
        }

        Type type = ((ExprBase) assign.value).type;
        char typePrefix = getJasminTypePrefix(type);

        code.append("    ").append(typePrefix).append("store ").append(varIndex).append("\n");
    }

    private void visitExpr(Expr expr, Map<String, Integer> localVars) {
        if (expr instanceof ExprInt) {
            code.append("    ldc ").append(((ExprInt) expr).value).append("\n");
        } else if (expr instanceof ExprFloat) {
            code.append("    ldc ").append(((ExprFloat) expr).value).append("\n");
        } else if (expr instanceof ExprBool) {
            code.append("    iconst_").append(((ExprBool) expr).value ? "1" : "0").append("\n");
        } else if (expr instanceof ExprChar) {
            code.append("    ldc ").append((int)((ExprChar) expr).value).append("\n");
        } else if (expr instanceof ExprBinary) {
            visitBinary((ExprBinary) expr, localVars);
        } else if (expr instanceof ExprLValue) {
            visitLValue(((ExprLValue) expr).lvalue, localVars);
        }
    }

    private void visitLValue(LValue lvalue, Map<String, Integer> localVars) {
        if (lvalue instanceof LValueVar) {
            String varName = ((LValueVar) lvalue).name;
            int varIndex = localVars.get(varName);
            Type type = ((LValueVar) lvalue).type;
            char typePrefix = getJasminTypePrefix(type);
            code.append("    ").append(typePrefix).append("load ").append(varIndex).append("\n");
        }
    }

    private void visitBinary(ExprBinary expr, Map<String, Integer> localVars) {
        visitExpr(expr.left, localVars);
        visitExpr(expr.right, localVars);
        Type type = ((ExprBase) expr.left).type;
        char typePrefix = getJasminTypePrefix(type);

        switch (expr.op) {
            case "+":
                code.append("    ").append(typePrefix).append("add\n");
                break;
            case "-":
                code.append("    ").append(typePrefix).append("sub\n");
                break;
            case "*":
                code.append("    ").append(typePrefix).append("mul\n");
                break;
            case "/":
                code.append("    ").append(typePrefix).append("div\n");
                break;
            case "==":
            case "!=":
            case "<":
                handleComparison(expr.op, typePrefix);
                break;
            case "&&":
                handleLogicalAnd();
                break;
        }
    }

    private void handleComparison(String op, char typePrefix) {
        String trueLabel = newLabel();
        String endLabel = newLabel();
        String instruction = "";

        switch (op) {
            case "==": instruction = "if_icmp" + (typePrefix == 'f' ? "" : "eq"); break;
            case "!=": instruction = "if_icmp" + (typePrefix == 'f' ? "" : "ne"); break;
            case "<":  instruction = "if_icmp" + (typePrefix == 'f' ? "" : "lt"); break;
        }
        if (typePrefix == 'f') { // Comparações de float são mais complexas
            code.append("    fcmpl\n");
             switch (op) {
                case "==": instruction = "ifeq"; break;
                case "!=": instruction = "ifne"; break;
                case "<":  instruction = "iflt"; break;
            }
        }
        
        code.append("    ").append(instruction).append(" ").append(trueLabel).append("\n");
        code.append("    iconst_0\n");
        code.append("    goto ").append(endLabel).append("\n");
        code.append(trueLabel).append(":\n");
        code.append("    iconst_1\n");
        code.append(endLabel).append(":\n");
    }

    private void handleLogicalAnd() {
        String falseLabel = newLabel();
        String endLabel = newLabel();

        // Avalia o lado esquerdo
        // Se for 0 (false), o resultado é 0. Pula para o fim.
        code.append("    ifeq ").append(falseLabel).append("\n");

        // Avalia o lado direito
        // Se o direito for 0, o resultado é 0.
        code.append("    ifeq ").append(falseLabel).append("\n");

        // Se ambos não forem 0, o resultado é 1.
        code.append("    iconst_1\n");
        code.append("    goto ").append(endLabel).append("\n");

        // Rótulo para o caso de resultado falso
        code.append(falseLabel).append(":\n");
        code.append("    iconst_0\n");

        // Fim da expressão
        code.append(endLabel).append(":\n");
    }

    private String newLabel() {
        return "L" + labelCounter++;
    }

    private char getJasminTypePrefix(Type type) {
        if (type.isA("Int") || type.isA("Bool") || type.isA("Char")) {
            return 'i';
        } else if (type.isA("Float")) {
            return 'f';
        } else {
            return 'a'; // para referências (arrays, records)
        }
    }

    // Outros métodos visit...
}
