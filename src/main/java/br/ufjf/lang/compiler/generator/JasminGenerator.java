package br.ufjf.lang.compiler.generator;

import br.ufjf.lang.compiler.ast.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;

public class JasminGenerator {

    // private StringBuilder code;
    // private int labelCounter = 0;
    
    // public String generate(Program program, String className) {
    //     code = new StringBuilder();

    //     // Cabeçalho padrão do Jasmin
    //     code.append(".class public ").append(className).append("\n");
    //     code.append(".super java/lang/Object\n\n");

    //     // Construtor padrão
    //     code.append(".method public <init>()V\n");
    //     code.append("    aload_0\n");
    //     code.append("    invokenonvirtual java/lang/Object/<init>()V\n");
    //     code.append("    return\n");
    //     code.append(".end method\n\n");

    //     // Gera o ponto de entrada padrão da JVM
    //     generateMainEntryPoint(program, className);

    //     // Gera um método para cada função definida na linguagem
    //     for (Def def : program.definitions) {
    //         if (def instanceof FunDef) {
    //             visitFun((FunDef) def);
    //         }
    //     }

    //     return code.toString();
    // }

    // private void generateMainEntryPoint(Program program, String className) {
    //     FunDef langMain = null;
    //     for (Def def : program.definitions) {
    //         if (def instanceof FunDef && ((FunDef) def).name.equals("main")) {
    //             langMain = (FunDef) def;
    //             break;
    //         }
    //     }

    //     if (langMain == null) {
    //         throw new RuntimeException("Função 'main' não encontrada.");
    //     }

    //     code.append(".method public static main([Ljava/lang/String;)V\n");
    //     code.append("    .limit stack 10\n");
    //     code.append("    .limit locals 10\n");
        
    //     // Chama a função 'main' da nossa linguagem (renomeada para _main)
    //     String mainSignature = getMethodSignature(langMain);
    //     code.append("    invokestatic ").append(className).append("/_main").append(mainSignature).append("\n");

    //     // Se a main da linguagem retornar algo, o valor é descartado pois a main da JVM é void.
    //     if (!langMain.returnTypes.isEmpty()) {
    //         code.append("    pop\n");
    //     }

    //     code.append("    return\n");
    //     code.append(".end method\n\n");
    // }

    // private void visitFun(FunDef fun) {
    //     // Renomeia 'main' para '_main' para evitar conflito com o entry point da JVM
    //     String funName = fun.name.equals("main") ? "_main" : fun.name;
    //     String signature = getMethodSignature(fun);
        
    //     code.append(".method public static ").append(funName).append(signature).append("\n");
    //     code.append("    .limit stack 10\n");
    //     code.append("    .limit locals 10\n");

    //     visitCmd(fun.body, new HashMap<String, Integer>());

    //     // Se a função for void, adiciona um return explícito
    //     if (fun.returnTypes.isEmpty()) {
    //         code.append("    return\n");
    //     }
        
    //     code.append(".end method\n\n");
    // }

    // private String getMethodSignature(FunDef fun) {
    //     String params = fun.params.stream()
    //         .map(p -> getTypeDescriptor(p.type()))
    //         .collect(Collectors.joining());

    //     String returnType;
    //     if (fun.returnTypes.isEmpty()) {
    //         returnType = "V";
    //     } else {
    //         returnType = getTypeDescriptor(fun.returnTypes.get(0));
    //         // Múltiplos retornos serão tratados depois (ex: retornando um array)
    //     }
    //     return "(" + params + ")" + returnType;
    // }

    // private String getTypeDescriptor(Type type) {
    //     if (type.isA("Int")) return "I";
    //     if (type.isA("Float")) return "F";
    //     if (type.isA("Bool")) return "Z";
    //     if (type.isA("Char")) return "C";
    //     // Para tipos de referência (records, arrays), o descritor seria "Lpath/to/Class;"
    //     return "Ljava/lang/Object;"; 
    // }

    // private void visitCmd(Cmd command, Map<String, Integer> localVars) {
    //     if (command instanceof CmdPrint) {
    //         visitPrint((CmdPrint) command, localVars);
    //     } else if (command instanceof CmdBlock) {
    //         for (Cmd cmd : ((CmdBlock) command).commands) {
    //             visitCmd(cmd, localVars);
    //         }
    //     } else if (command instanceof CmdAssign) {
    //         visitAssign((CmdAssign) command, localVars);
    //     } else if (command instanceof CmdIf) {
    //         visitIf((CmdIf) command, localVars);
    //     } else if (command instanceof CmdReturn) {
    //         visitReturn((CmdReturn) command, localVars);
    //     }
    // }

    // private void visitReturn(CmdReturn ret, Map<String, Integer> localVars) {
    //     if (ret.values.isEmpty()) {
    //         code.append("    return\n");
    //         return;
    //     }

    //     Expr returnValue = ret.values.get(0);
    //     visitExpr(returnValue, localVars);
    //     Type type = ((ExprBase) returnValue).type;
    //     char typePrefix = getJasminTypePrefix(type);
    //     if(typePrefix == 'a'){ // a para object reference
    //          code.append("    areturn\n");
    //     } else {
    //          code.append("    ").append(typePrefix).append("return\n");
    //     }
    // }

    // private void visitPrint(CmdPrint print, Map<String, Integer> localVars) {
    //     code.append("    getstatic java/lang/System/out Ljava/io/PrintStream;\n");
    //     visitExpr(print.expr, localVars);
        
    //     Type type = ((ExprBase) print.expr).type;
    //     String descriptor = "Ljava/lang/String;";
    //     if (type.isA("Int")) descriptor = "I";
    //     else if (type.isA("Float")) descriptor = "F";
    //     else if (type.isA("Bool")) descriptor = "Z";
    //     else if (type.isA("Char")) descriptor = "C";

    //     code.append("    invokevirtual java/io/PrintStream/print(").append(descriptor).append(")V\n");
    // }

    // private void visitIf(CmdIf ifCmd, Map<String, Integer> localVars) {
    //     String elseLabel = newLabel();
    //     String endLabel = newLabel();

    //     // Avalia a condição
    //     visitExpr(ifCmd.condition, localVars);

    //     // Se a condição for falsa (0), pula para o bloco else (ou para o fim)
    //     code.append("    ifeq ").append(elseLabel).append("\n");

    //     // Bloco then
    //     visitCmd(ifCmd.thenBranch, localVars);
    //     code.append("    goto ").append(endLabel).append("\n");

    //     // Bloco else
    //     code.append(elseLabel).append(":\n");
    //     if (ifCmd.elseBranch != null) {
    //         visitCmd(ifCmd.elseBranch, localVars);
    //     }

    //     // Fim do if
    //     code.append(endLabel).append(":\n");
    // }

    // private void visitAssign(CmdAssign assign, Map<String, Integer> localVars) {
    //     if (!(assign.target instanceof LValueVar)) {
    //         // Por enquanto, só suportamos atribuição a variáveis simples
    //         return;
    //     }
    //     String varName = ((LValueVar) assign.target).name;
    //     visitExpr(assign.value, localVars);

    //     int varIndex;
    //     if (localVars.containsKey(varName)) {
    //         varIndex = localVars.get(varName);
    //     } else {
    //         varIndex = localVars.size(); // Próximo índice disponível
    //         localVars.put(varName, varIndex);
    //     }

    //     Type type = ((ExprBase) assign.value).type;
    //     char typePrefix = getJasminTypePrefix(type);

    //     code.append("    ").append(typePrefix).append("store ").append(varIndex).append("\n");
    // }

    // private void visitExpr(Expr expr, Map<String, Integer> localVars) {
    //     if (expr instanceof ExprInt) {
    //         code.append("    ldc ").append(((ExprInt) expr).value).append("\n");
    //     } else if (expr instanceof ExprFloat) {
    //         code.append("    ldc ").append(((ExprFloat) expr).value).append("\n");
    //     } else if (expr instanceof ExprBool) {
    //         code.append("    iconst_").append(((ExprBool) expr).value ? "1" : "0").append("\n");
    //     } else if (expr instanceof ExprChar) {
    //         code.append("    ldc ").append((int)((ExprChar) expr).value).append("\n");
    //     } else if (expr instanceof ExprBinary) {
    //         visitBinary((ExprBinary) expr, localVars);
    //     } else if (expr instanceof ExprLValue) {
    //         visitLValue(((ExprLValue) expr).lvalue, localVars);
    //     }
    // }

    // private void visitLValue(LValue lvalue, Map<String, Integer> localVars) {
    //     if (lvalue instanceof LValueVar) {
    //         String varName = ((LValueVar) lvalue).name;
    //         int varIndex = localVars.get(varName);
    //         Type type = ((LValueVar) lvalue).type;
    //         char typePrefix = getJasminTypePrefix(type);
    //         code.append("    ").append(typePrefix).append("load ").append(varIndex).append("\n");
    //     }
    // }

    // private void visitBinary(ExprBinary expr, Map<String, Integer> localVars) {
    //     visitExpr(expr.left, localVars);
    //     visitExpr(expr.right, localVars);
    //     Type type = ((ExprBase) expr.left).type;
    //     char typePrefix = getJasminTypePrefix(type);

    //     switch (expr.op) {
    //         case "+":
    //             code.append("    ").append(typePrefix).append("add\n");
    //             break;
    //         case "-":
    //             code.append("    ").append(typePrefix).append("sub\n");
    //             break;
    //         case "*":
    //             code.append("    ").append(typePrefix).append("mul\n");
    //             break;
    //         case "/":
    //             code.append("    ").append(typePrefix).append("div\n");
    //             break;
    //         case "==":
    //         case "!=":
    //         case "<":
    //             handleComparison(expr.op, typePrefix);
    //             break;
    //         case "&&":
    //             handleLogicalAnd();
    //             break;
    //     }
    // }

    // private void handleComparison(String op, char typePrefix) {
    //     String trueLabel = newLabel();
    //     String endLabel = newLabel();
    //     String instruction = "";

    //     switch (op) {
    //         case "==": instruction = "if_icmp" + (typePrefix == 'f' ? "" : "eq"); break;
    //         case "!=": instruction = "if_icmp" + (typePrefix == 'f' ? "" : "ne"); break;
    //         case "<":  instruction = "if_icmp" + (typePrefix == 'f' ? "" : "lt"); break;
    //     }
    //     if (typePrefix == 'f') { // Comparações de float são mais complexas
    //         code.append("    fcmpl\n");
    //          switch (op) {
    //             case "==": instruction = "ifeq"; break;
    //             case "!=": instruction = "ifne"; break;
    //             case "<":  instruction = "iflt"; break;
    //         }
    //     }
        
    //     code.append("    ").append(instruction).append(" ").append(trueLabel).append("\n");
    //     code.append("    iconst_0\n");
    //     code.append("    goto ").append(endLabel).append("\n");
    //     code.append(trueLabel).append(":\n");
    //     code.append("    iconst_1\n");
    //     code.append(endLabel).append(":\n");
    // }

    // private void handleLogicalAnd() {
    //     String falseLabel = newLabel();
    //     String endLabel = newLabel();

    //     // Avalia o lado esquerdo
    //     // Se for 0 (false), o resultado é 0. Pula para o fim.
    //     code.append("    ifeq ").append(falseLabel).append("\n");

    //     // Avalia o lado direito
    //     // Se o direito for 0, o resultado é 0.
    //     code.append("    ifeq ").append(falseLabel).append("\n");

    //     // Se ambos não forem 0, o resultado é 1.
    //     code.append("    iconst_1\n");
    //     code.append("    goto ").append(endLabel).append("\n");

    //     // Rótulo para o caso de resultado falso
    //     code.append(falseLabel).append(":\n");
    //     code.append("    iconst_0\n");

    //     // Fim da expressão
    //     code.append(endLabel).append(":\n");
    // }

    // private String newLabel() {
    //     return "L" + labelCounter++;
    // }

    // private char getJasminTypePrefix(Type type) {
    //     if (type.isA("Int") || type.isA("Bool") || type.isA("Char")) {
    //         return 'i';
    //     } else if (type.isA("Float")) {
    //         return 'f';
    //     } else {
    //         return 'a'; // para referências (arrays, records)
    //     }
    // }

    // Outros métodos visit...
}
