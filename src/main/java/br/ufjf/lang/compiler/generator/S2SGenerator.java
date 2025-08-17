package br.ufjf.lang.compiler.generator;

import br.ufjf.lang.compiler.ast.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class S2SGenerator {

    private static final Set<String> PYTHON_KEYWORDS = Set.of(
        "False", "None", "True", "and", "as", "assert", "async", "await",
        "break", "class", "continue", "def", "del", "elif", "else", "except",
        "finally", "for", "from", "global", "if", "import", "in", "is",
        "lambda", "nonlocal", "not", "or", "pass", "raise", "return",
        "try", "while", "with", "yield"
    );

    private final Map<String, DataDef> recordTable;
    private StringBuilder pythonCode;
    private int indentLevel = 0;

    public S2SGenerator(Map<String, DataDef> recordTable) {
        this.recordTable = recordTable;
    }

    public String generate(Program program) {
        this.pythonCode = new StringBuilder();
        
        for (Def def : program.definitions) {
            if (def instanceof DataDef d) {
                visit(d);
            } else if (def instanceof FunDef f) {
                visit(f);
            }
            appendLine("");
        }

        appendLine("if __name__ == \"__main__\":");
        indent();
        appendLine("main()");
        dedent();

        return this.pythonCode.toString();
    }

    private void visit(DataDef data) {
        appendLine("class " + mangle(data.name) + ":");
        indent();
        appendLine("def __init__(self):");
        indent();
        if (data.fields.isEmpty()) {
            appendLine("pass");
        } else {
            for (var field : data.fields) {
                String mangledName = mangle(field.name);
                String finalFieldName = data.isAbstract ? "_" + mangledName : mangledName;
                appendLine("self." + finalFieldName + " = " + getDefaultValue(field.type));
            }
        }
        dedent();
        dedent();
    }

    private void visit(FunDef fun) {
        String params = fun.params.stream()
                              .map(p -> mangle(p.name()))
                              .collect(Collectors.joining(", "));
                              
        appendLine("def " + mangle(fun.name) + "(" + params + "):");
        indent();
        visit(fun.body);
        dedent();
    }

    private void visit(Cmd cmd) {
        if (cmd == null) return;

        if (cmd instanceof CmdBlock block) {
            if (block.commands.isEmpty()) {
                appendLine("pass");
            } else {
                for (Cmd c : block.commands) {
                    visit(c);
                }
            }
        } 
        else if (cmd instanceof CmdAssign assign) {
            appendLine(visit(assign.target) + " = " + visit(assign.value));
        } 
        else if (cmd instanceof CmdIf iff) {
            appendLine("if " + visit(iff.condition) + ":");
            indent();
            visit(iff.thenBranch);
            dedent();
            if (iff.elseBranch != null) {
                appendLine("else:");
                indent();
                visit(iff.elseBranch);
                dedent();
            }
        } 
        else if (cmd instanceof CmdPrint p) {
            if (p.expr instanceof ExprChar c && c.rawValue.equals("'\\n'")) {
                appendLine("print()");
            } else {
                appendLine("print(" + visit(p.expr) + ", end='')");
            }
        }
        else if (cmd instanceof CmdRead r) {
            br.ufjf.lang.compiler.ast.LValue targetLval = r.target;
            Type targetType = ((LValueBase)targetLval).type;
            if (targetType == null) {
                throw new RuntimeException("Erro de Geração: O tipo do alvo do 'read' não foi determinado.");
            }
            String targetStr = visit(targetLval);
            String pythonInput;
            if (targetType.isA("Int")) {
                pythonInput = "int(input())";
            } else if (targetType.isA("Float")) {
                pythonInput = "float(input())";
            } else if (targetType.isA("Bool")) {
                pythonInput = "(input().lower() == 'true')";
            } else { 
                pythonInput = "input()";
            }
            appendLine(targetStr + " = " + pythonInput);
        }
        else if (cmd instanceof CmdIterate loop) {
            Type rangeType = ((ExprBase)loop.range).type;
            if (rangeType == null) {
                throw new RuntimeException("Erro de Geração: O tipo da expressão do iterate não foi determinado pela análise semântica.");
            }
            String rangeExpr = visit(loop.range);
            if (rangeType.isA("Int")) {
                String loopVar = (loop.varName != null) ? mangle(loop.varName) : "_";
                appendLine("for " + loopVar + " in range(" + rangeExpr + "):");
            } else if (rangeType instanceof TypeArray) {
                String loopVar = mangle(loop.varName);
                appendLine("for " + loopVar + " in " + rangeExpr + ":");
            }
            indent();
            visit(loop.body);
            dedent();
        }
        else if (cmd instanceof CmdReturn r) {
            if (r.values.isEmpty()) {
                appendLine("return");
            } else {
                String returnValues = r.values.stream()
                                              .map(this::visit)
                                              .collect(Collectors.joining(", "));
                if (r.values.size() == 1) {
                    returnValues = "(" + returnValues + ",)";
                }
                appendLine("return " + returnValues);
            }
        }
        else if (cmd instanceof CmdCall call) {
            String args = call.arguments.stream()
                                        .map(this::visit)
                                        .collect(Collectors.joining(", "));
            String functionCallStr = mangle(call.functionName) + "(" + args + ")";

            if (call.outputTargets != null && !call.outputTargets.isEmpty()) {
                String targets = call.outputTargets.stream()
                                                 .map(this::visit)
                                                 .collect(Collectors.joining(", "));
                appendLine(targets + " = " + functionCallStr);
            } else {
                appendLine(functionCallStr);
            }
        }
        else {
            throw new RuntimeException("Geração S2S não implementada para o comando: " + cmd.getClass().getSimpleName());
        }
    }

    private String visit(Expr expr) {
        if (expr instanceof ExprInt i) { return String.valueOf(i.value); }
        if (expr instanceof ExprFloat f) { return String.valueOf(f.value); }
        if (expr instanceof ExprBool b) { return b.value ? "True" : "False"; }
        if (expr instanceof ExprNull) { return "None"; }
        
        if (expr instanceof ExprChar c) {
            String raw = c.rawValue;
            String content = raw.substring(1, raw.length() - 1);
            if (content.length() > 1 && content.charAt(0) == '\\') {
                char escapeType = content.charAt(1);
                if (Character.isDigit(escapeType)) {
                    int charValue = Integer.parseInt(content.substring(1));
                    return String.format("'\\x%02x'", charValue);
                }
            }
            return raw;
        }
        
        if (expr instanceof ExprLValue lvalExpr) { return visit(lvalExpr.lvalue); }
        if (expr instanceof ExprParen p) { return "(" + visit(p.inner) + ")"; }
        
        if (expr instanceof ExprUnary u) {
            String op = u.op.equals("!") ? "not " : u.op;
            return op + "(" + visit(u.expr) + ")";
        }
        
        if (expr instanceof ExprBinary b) {
            String left = visit(b.left);
            String right = visit(b.right);
            String op;
            if (b.op.equals("/")) {
                if (((ExprBase)b.left).type.isA("Int") && ((ExprBase)b.right).type.isA("Int")) {
                    op = "//";
                } else {
                    op = "/";
                }
            } else {
                op = b.op.equals("&&") ? "and" : b.op;
            }
            return "(" + left + " " + op + " " + right + ")";
        }
        
        if (expr instanceof ExprNew n) {
            if (n.size != null) {
                String sizeExpr = visit(n.size);
                String defaultValue = getDefaultValue(n.typeToCreate);
                return "[" + defaultValue + "] * (" + sizeExpr + ")";
            } else {
                String typeName = ((TypeBase)n.typeToCreate).name;
                return mangle(typeName) + "()";
            }
        }
        
        if (expr instanceof ExprCall call) {
            String args = call.arguments.stream()
                                        .map(this::visit)
                                        .collect(Collectors.joining(", "));
            String index = visit(call.index);
            return mangle(call.functionName) + "(" + args + ")[" + index + "]";
        }

        throw new RuntimeException("Geração S2S não implementada para a expressão: " + expr.getClass().getSimpleName());
    }

    private String visit(br.ufjf.lang.compiler.ast.LValue lvalue) {
        if (lvalue instanceof LValueVar var) { return mangle(var.name); }
        if (lvalue instanceof LValueArrayAccess arrAccess) { return visit(arrAccess.array) + "[" + visit(arrAccess.index) + "]"; }
        if (lvalue instanceof LValueRecordAccess recAccess) {
            String record = visit(recAccess.record);
            String field = recAccess.field;
            if (((LValueBase)recAccess.record).type instanceof TypeBase tb) {
                DataDef recordDef = recordTable.get(tb.name);
                if (recordDef != null && recordDef.isAbstract) {
                    field = "_" + field;
                }
            }
            return record + "." + mangle(field);
        }
        throw new RuntimeException("Geração S2S não implementada para o LValue: " + lvalue.getClass().getSimpleName());
    }

    // Métodos auxiliares
    private void indent() { indentLevel++; }
    private void dedent() { indentLevel--; }
    private String mangle(String name) {
        if (PYTHON_KEYWORDS.contains(name)) {
            return name + "_";
        }
        return name;
    }
    private void append(String code) { pythonCode.append(code); }
    private void appendLine(String line) {
        for (int i = 0; i < indentLevel; i++) {
            pythonCode.append("    ");
        }
        pythonCode.append(line).append("\n");
    }
    private String getDefaultValue(Type type) {
        if (type.isA("Int") || type.isA("Float")) return "0";
        if (type.isA("Bool")) return "False";
        if (type.isA("Char")) return "''";
        return "None";
    }
}