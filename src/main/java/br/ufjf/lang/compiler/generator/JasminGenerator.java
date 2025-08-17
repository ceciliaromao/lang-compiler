package br.ufjf.lang.compiler.generator;

import br.ufjf.lang.compiler.ast.*;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class JasminGenerator {

    private StringBuilder code;
    private int labelCounter = 0;
    private String className;
    private Map<String, FunDef> functionTable;
    
    public JasminGenerator(Map<String, FunDef> functionTable) {
        this.functionTable = functionTable;
    }

    public Map<String, String> generate(Program program, String className) {
        this.className = className;
        Map<String, String> generatedFiles = new HashMap<>();

        // Gera a classe principal com as funções
        code = new StringBuilder();
        generateMainClass(program);
        generatedFiles.put(this.className, code.toString());

        // Gera uma classe para cada tipo de dado
        for (Def def : program.definitions) {
            if (def instanceof DataDef) {
                code = new StringBuilder();
                visitDataDef((DataDef) def);
                generatedFiles.put(((DataDef) def).name, code.toString());
            }
        }

        return generatedFiles;
    }

    private void generateMainClass(Program program) {
        // Cabeçalho padrão do Jasmin para a classe principal
        code.append(".class public ").append(className).append("\n");
        code.append(".super java/lang/Object\n\n");

        // Construtor padrão
        code.append(".method public <init>()V\n");
        code.append("    aload_0\n");
        code.append("    invokenonvirtual java/lang/Object/<init>()V\n");
        code.append("    return\n");
        code.append(".end method\n\n");

        // Gera o ponto de entrada padrão da JVM
        generateMainEntryPoint(program, className);

        // Gera um método para cada função definida na linguagem
        for (Def def : program.definitions) {
            if (def instanceof FunDef) {
                visitFun((FunDef) def);
            }
        }
    }

    private void visitDataDef(DataDef dataDef) {
        code.append(".class public ").append(dataDef.name).append("\n");
        code.append(".super java/lang/Object\n\n");

        // Gera os campos da classe
        for (FieldDecl field : dataDef.fields) {
            code.append(".field public ").append(field.name).append(" ").append(getTypeDescriptor(field.type)).append("\n");
        }

        // Gera o construtor padrão que inicializa os campos
        code.append("\n.method public <init>()V\n");
        code.append("    aload_0\n");
        code.append("    invokenonvirtual java/lang/Object/<init>()V\n");
        
        // Inicializa cada campo com um valor padrão
        for (FieldDecl field : dataDef.fields) {
            code.append("    aload_0\n");
            // Empilha o valor padrão
            if (getJasminTypePrefix(field.type) == 'i') {
                code.append("    iconst_0\n"); // 0 para int, bool, char
            } else if (getJasminTypePrefix(field.type) == 'f') {
                code.append("    fconst_0\n"); // 0.0 para float
            } else {
                code.append("    aconst_null\n"); // null para referências
            }
            code.append("    putfield ").append(dataDef.name).append("/").append(field.name).append(" ").append(getTypeDescriptor(field.type)).append("\n");
        }
        
        code.append("    return\n");
        code.append(".end method\n");
    }

    private void generateMainEntryPoint(Program program, String className) {
        FunDef langMain = null;
        for (Def def : program.definitions) {
            if (def instanceof FunDef && ((FunDef) def).name.equals("main")) {
                langMain = (FunDef) def;
                break;
            }
        }

        if (langMain == null) {
            throw new RuntimeException("Função 'main' não encontrada.");
        }

        code.append(".method public static main([Ljava/lang/String;)V\n");
        code.append("    .limit stack 10\n");
        code.append("    .limit locals 10\n");
        
        // Empilha valores padrão para os parâmetros da main da linguagem, como faz o interpretador
        for (FunDef.Param param : langMain.params) {
            if (param.type().isA("Int") || param.type().isA("Bool") || param.type().isA("Char")) {
                code.append("    iconst_0\n");
            } else if (param.type().isA("Float")) {
                code.append("    fconst_0\n");
            } else if (param.type() instanceof TypeArray) {
                code.append("    iconst_0\n"); // Tamanho do array
                code.append("    newarray int\n"); // Assumindo array de int por simplicidade, precisaria de mais lógica
            } else {
                code.append("    aconst_null\n");
            }
        }

        // Chama a função 'main' da nossa linguagem (renomeada para _main)
        String mainSignature = getMethodSignature(langMain);
        code.append("    invokestatic ").append(className).append("/_main").append(mainSignature).append("\n");

        // Se a main da linguagem retornar algo, o valor é descartado pois a main da JVM é void.
        if (!langMain.returnTypes.isEmpty()) {
            code.append("    pop\n");
        }

        code.append("    return\n");
        code.append(".end method\n\n");
    }

    private void visitFun(FunDef fun) {
        // Renomeia 'main' para '_main' para evitar conflito com o entry point da JVM
        String funName = fun.name.equals("main") ? "_main" : fun.name;
        String signature = getMethodSignature(fun);
        
        code.append(".method public static ").append(funName).append(signature).append("\n");
        
        Map<String, Integer> localVars = new HashMap<>();
        int varIndex = 0;
        for (FunDef.Param param : fun.params) {
            localVars.put(param.name(), varIndex++);
        }

        // Defina um limite razoável para a pilha e locais. 
        // Ferramentas mais avançadas calculariam isso.
        code.append("    .limit stack 10\n");
        code.append("    .limit locals ").append(Math.max(varIndex, 10)).append("\n");

        visitCmd(fun.body, localVars, fun);

        // Se a função for void, adiciona um return explícito
        if (fun.returnTypes.isEmpty()) {
            code.append("    return\n");
        }
        
        code.append(".end method\n\n");
    }

    private String getMethodSignature(FunDef fun) {
        String params = fun.params.stream()
            .map(p -> getTypeDescriptor(p.type()))
            .collect(Collectors.joining());

        String returnType;
        if (fun.returnTypes.isEmpty()) {
            returnType = "V";
        } else {
            returnType = getTypeDescriptor(fun.returnTypes.get(0));
            // Múltiplos retornos serão tratados depois (ex: retornando um array)
        }
        return "(" + params + ")" + returnType;
    }

    private String getTypeDescriptor(Type type) {
        if (type.isA("Int")) return "I";
        if (type.isA("Float")) return "F";
        if (type.isA("Bool")) return "Z";
        if (type.isA("Char")) return "C";
        // Para tipos de referência (records, arrays), o descritor seria "Lpath/to/Class;"
        return "Ljava/lang/Object;"; 
    }

    private void visitCmd(Cmd command, Map<String, Integer> localVars, FunDef currentFunction) {
        if (command instanceof CmdPrint) {
            visitPrint((CmdPrint) command, localVars);
        } else if (command instanceof CmdBlock) {
            for (Cmd cmd : ((CmdBlock) command).commands) {
                visitCmd(cmd, localVars, currentFunction);
            }
        } else if (command instanceof CmdAssign) {
            visitAssign((CmdAssign) command, localVars);
        } else if (command instanceof CmdIf) {
            visitIf((CmdIf) command, localVars, currentFunction);
        } else if (command instanceof CmdReturn) {
            visitReturn((CmdReturn) command, localVars, currentFunction);
        } else if (command instanceof CmdCall) {
            visitCmdCall((CmdCall) command, localVars);
        } else if (command instanceof CmdRead) {
            visitRead((CmdRead) command, localVars);
        } else if (command instanceof CmdIterate) {
            visitIterate((CmdIterate) command, localVars, currentFunction);
        }
    }

    private void visitIterate(CmdIterate iter, Map<String, Integer> localVars, FunDef currentFunction) {
        String loopStartLabel = newLabel();
        String loopEndLabel = newLabel();
        Type rangeType = ((ExprBase) iter.range).type;

        Map<String, Integer> loopVars = new HashMap<>(localVars);

        if (rangeType instanceof TypeArray) {
            // --- Iteração sobre Array ---
            visitExpr(iter.range, loopVars);
            int arrayVarIndex = loopVars.size();
            loopVars.put("__arr" + arrayVarIndex, arrayVarIndex);
            code.append("    astore ").append(arrayVarIndex).append("\n");
            
            int indexVarIndex = loopVars.size();
            loopVars.put("__idx" + indexVarIndex, indexVarIndex);
            code.append("    iconst_0\n");
            code.append("    istore ").append(indexVarIndex).append("\n");

            code.append(loopStartLabel).append(":\n");

            // Condição: __idx < array.length
            code.append("    iload ").append(indexVarIndex).append("\n");
            code.append("    aload ").append(arrayVarIndex).append("\n");
            code.append("    arraylength\n");
            code.append("    if_icmpge ").append(loopEndLabel).append("\n");

            // Se for for-each, atribui o elemento à variável
            if (iter.varName != null) {
                int elementVarIndex = loopVars.size();
                loopVars.put(iter.varName, elementVarIndex);

                code.append("    aload ").append(arrayVarIndex).append("\n");
                code.append("    iload ").append(indexVarIndex).append("\n");
                char elementPrefix = getJasminTypePrefix(((TypeArray) rangeType).elementType);
                code.append("    ").append(elementPrefix).append("aload\n");
                code.append("    ").append(elementPrefix).append("store ").append(elementVarIndex).append("\n");
            }

            visitCmd(iter.body, loopVars, currentFunction);
            
            // Incremento: __idx++
            code.append("    iinc ").append(indexVarIndex).append(" 1\n");
            code.append("    goto ").append(loopStartLabel).append("\n");

        } else { // --- Iteração sobre Inteiro ---
            int loopVarIndex;
            boolean isTempVar = false;

            // Se não houver variável de loop (ex: iterate(10)), cria uma temporária
            if (iter.varName == null) {
                loopVarIndex = loopVars.size();
                loopVars.put("__temp_iter" + loopVarIndex, loopVarIndex);
                isTempVar = true;
            } else {
                loopVarIndex = loopVars.size();
                loopVars.put(iter.varName, loopVarIndex);
            }
            
            // Inicializa a variável do loop com 0
            code.append("    iconst_0\n");
            code.append("    istore ").append(loopVarIndex).append("\n");
            
            code.append(loopStartLabel).append(":\n");

            // Condição: loopVar < Range
            code.append("    iload ").append(loopVarIndex).append("\n");
            visitExpr(iter.range, loopVars);
            code.append("    if_icmpge ").append(loopEndLabel).append("\n");

            visitCmd(iter.body, loopVars, currentFunction);

            // Incremento: loopVar++
            code.append("    iinc ").append(loopVarIndex).append(" 1\n");
            
            code.append("    goto ").append(loopStartLabel).append("\n");
        }
        
        code.append(loopEndLabel).append(":\n");
    }

    private void visitRead(CmdRead read, Map<String, Integer> localVars) {
        if (!(read.target instanceof LValueVar)) {
            // Só suportamos leitura para variáveis simples por enquanto
            return;
        }
        LValueVar var = (LValueVar) read.target;
        
        int varIndex;
        if (localVars.containsKey(var.name)) {
            varIndex = localVars.get(var.name);
        } else {
            varIndex = localVars.size();
            localVars.put(var.name, varIndex);
        }

        // Cria um novo Scanner para ler da entrada padrão
        code.append("    new java/util/Scanner\n");
        code.append("    dup\n");
        code.append("    getstatic java/lang/System/in Ljava/io/InputStream;\n");
        code.append("    invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V\n");
        
        // Lê a próxima linha
        code.append("    invokevirtual java/util/Scanner/nextLine()Ljava/lang/String;\n");
        
        // Converte a String para o tipo apropriado e armazena na variável
        Type type = var.type;
        if (type.isA("Int")) {
            code.append("    invokestatic java/lang/Integer/parseInt(Ljava/lang/String;)I\n");
            code.append("    istore ").append(varIndex).append("\n");
        } else if (type.isA("Float")) {
            code.append("    invokestatic java/lang/Double/parseDouble(Ljava/lang/String;)D\n");
            code.append("    dstore ").append(varIndex).append("\n");
        } else {
            // Para outros tipos (Char, Bool, etc.), a conversão é mais complexa.
            // Por enquanto, vamos focar em Int e Float.
        }
    }

    private String getLValueName(LValue lvalue) {
        if (lvalue instanceof LValueVar) {
            return ((LValueVar) lvalue).name;
        } else if (lvalue instanceof LValueArrayAccess) {
            return getLValueName(((LValueArrayAccess) lvalue).array);
        } else if (lvalue instanceof LValueRecordAccess) {
            return getLValueName(((LValueRecordAccess) lvalue).record);
        }
        return null;
    }

    private void visitCmdCall(CmdCall call, Map<String, Integer> localVars) {
        // Empilha todos os argumentos
        for (Expr arg : call.arguments) {
            visitExpr(arg, localVars);
        }

        // Obtém a definição da função para construir a assinatura
        // (Isso idealmente viria de uma tabela de símbolos passada para o gerador)
        FunDef funDef = functionTable.get(call.functionName);
        String signature = getMethodSignature(funDef);
        
        code.append("    invokestatic ").append(className).append("/").append(call.functionName).append(signature).append("\n");
        
        // Se a função retorna um valor mas a chamada não o usa, remove-o da pilha
        if (!funDef.returnTypes.isEmpty()) {
            code.append("    pop\n");
        }
    }

    private void visitReturn(CmdReturn ret, Map<String, Integer> localVars, FunDef currentFunction) {
        if (currentFunction.returnTypes.isEmpty()) {
            if (!ret.values.isEmpty()) {
                // Função é void, mas retorna um valor. Calcula, descarta e retorna.
                visitExpr(ret.values.get(0), localVars);
                code.append("    pop\n");
            }
            code.append("    return\n");
            return;
        }

        // Lógica existente para funções com tipo de retorno
        Expr returnValue = ret.values.get(0);
        visitExpr(returnValue, localVars);
        Type type = ((ExprBase) returnValue).type;
        char typePrefix = getJasminTypePrefix(type);
        if(typePrefix == 'a'){
             code.append("    areturn\n");
        } else {
             code.append("    ").append(typePrefix).append("return\n");
        }
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

    private void visitIf(CmdIf ifCmd, Map<String, Integer> localVars, FunDef currentFunction) {
        String elseLabel = newLabel();
        String endLabel = newLabel();

        // Avalia a condição
        visitExpr(ifCmd.condition, localVars);

        // Se a condição for falsa (0), pula para o bloco else (ou para o fim)
        code.append("    ifeq ").append(elseLabel).append("\n");

        // Bloco then
        visitCmd(ifCmd.thenBranch, localVars, currentFunction);
        code.append("    goto ").append(endLabel).append("\n");

        // Bloco else
        code.append(elseLabel).append(":\n");
        if (ifCmd.elseBranch != null) {
            visitCmd(ifCmd.elseBranch, localVars, currentFunction);
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
        } else if (expr instanceof ExprCall) {
            visitExprCall((ExprCall) expr, localVars);
        } else if (expr instanceof ExprNew) {
            visitNew((ExprNew) expr, localVars);
        }
    }

    private void visitNew(ExprNew expr, Map<String, Integer> localVars) {
        if (expr.size != null) { // É uma criação de array
            visitExpr(expr.size, localVars); // Empilha o tamanho do array
            Type elementType = expr.typeToCreate;
             if (elementType instanceof TypeArray) { // Se for array de array...
                elementType = ((TypeArray)elementType).elementType;
            }
            if (elementType.isA("Int")) {
                code.append("    newarray int\n");
            } else if (elementType.isA("Float")) {
                code.append("    newarray float\n");
            } else {
                code.append("    anewarray ").append(getTypeDescriptor(elementType)).append("\n");
            }
        } else { // É uma criação de record
            String typeName = expr.typeToCreate.toString();
            code.append("    new ").append(typeName).append("\n");
            code.append("    dup\n");
            code.append("    invokespecial ").append(typeName).append("/<init>()V\n");
        }
    }

    private void visitExprCall(ExprCall call, Map<String, Integer> localVars) {
        for (Expr arg : call.arguments) {
            visitExpr(arg, localVars);
        }
        FunDef funDef = functionTable.get(call.functionName);
        String signature = getMethodSignature(funDef);
        code.append("    invokestatic ").append(className).append("/").append(call.functionName).append(signature).append("\n");
        // O valor de retorno já está na pilha, pronto para ser usado
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
                handleLogicalAnd(localVars); // Passando as variáveis locais
                break;
        }
    }

    private void handleComparison(String op, char typePrefix) {
        String trueLabel = newLabel();
        String endLabel = newLabel();
        String instruction;

        if (typePrefix == 'f') {
            code.append("    fcmpl\n"); // Para floats, primeiro compara e coloca -1, 0, ou 1 na pilha
            switch (op) {
                case "==": instruction = "ifeq"; break; // salta se for 0 (igual)
                case "!=": instruction = "ifne"; break; // salta se não for 0 (diferente)
                case "<":  instruction = "iflt"; break; // salta se for -1 (menor que)
                default: return;
            }
        } else { // Para inteiros
            switch (op) {
                case "==": instruction = "if_icmpeq"; break;
                case "!=": instruction = "if_icmpne"; break;
                case "<":  instruction = "if_icmplt"; break;
                default: return;
            }
        }
        
        code.append("    ").append(instruction).append(" ").append(trueLabel).append("\n");
        code.append("    iconst_0\n"); // false
        code.append("    goto ").append(endLabel).append("\n");
        code.append(trueLabel).append(":\n");
        code.append("    iconst_1\n"); // true
        code.append(endLabel).append(":\n");
    }

    private void handleLogicalAnd(Map<String, Integer> localVars) {
        String falseLabel = newLabel();
        String endLabel = newLabel();

        // A expressão da esquerda já foi avaliada e está na pilha.
        // Se for 0 (false), o resultado já é false. Pula para o fim.
        code.append("    ifeq ").append(falseLabel).append("\n");

        // Se a esquerda era true, a expressão da direita (que também foi avaliada) está na pilha.
        // O resultado da expressão '&&' é simplesmente o resultado da expressão da direita.
        code.append("    goto ").append(endLabel).append("\n");

        // Rótulo para o caso de resultado falso
        code.append(falseLabel).append(":\n");
        code.append("    pop\n");      // Remove o resultado da direita da pilha
        code.append("    iconst_0\n"); // Coloca false na pilha

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
