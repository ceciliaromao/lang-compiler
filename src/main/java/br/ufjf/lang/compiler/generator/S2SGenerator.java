package br.ufjf.lang.compiler.generator;

import br.ufjf.lang.compiler.ast.*;
import java.util.stream.Collectors;

import java.util.Map;

public class S2SGenerator {

    private final Map<String, DataDef> recordTable;

    private StringBuilder pythonCode;
    private int indentLevel = 0;

    public S2SGenerator(Map<String, DataDef> recordTable) {
        this.recordTable = recordTable;
    }

    // Método principal que inicia a geração
    public String generate(Program program) {
        this.pythonCode = new StringBuilder();
        
        // gera o código para todas as definições (classes e funções)
        for (Def def : program.definitions) {
            if (def instanceof DataDef d) {
                visit(d);
            } else if (def instanceof FunDef f) {
                visit(f);
            }
            appendLine("");
        }

        // adiciona o ponto de entrada padrão para scripts Python
        appendLine("if __name__ == \"__main__\":");
        indent();
        appendLine("main()");
        dedent();

        return this.pythonCode.toString();
    }

    private void append(String code) {
        pythonCode.append(code);
    }

    private void appendLine(String line) {
        for (int i = 0; i < indentLevel; i++) {
            pythonCode.append("    "); // 4 espaços para indentação
        }
        pythonCode.append(line).append("\n");
    }

    private void indent() {
        indentLevel++;
    }

    private void dedent() {
        indentLevel--;
    }

    private void visit(DataDef data) {
        appendLine("class " + data.name + ":");
        indent();
        appendLine("def __init__(self):");
        indent();
        if (data.fields.isEmpty()) {
            appendLine("pass");
        } else {
            for (var field : data.fields) {
                // se for 'abstract data', prefixamos com '_'
                String fieldName = data.isAbstract ? "_" + field.name : field.name;
                // inicializa com um valor padrão Python
                appendLine("self." + fieldName + " = " + getDefaultValue(field.type));
            }
        }
        dedent();
        dedent();
    }

    private void visit(FunDef fun) {
        // constrói a lista de parâmetros para a assinatura da função
        String params = fun.params.stream()
                                .map(p -> p.name())
                                .collect(Collectors.joining(", "));
                                
        appendLine("def " + fun.name + "(" + params + "):");
        indent();
        
        // visita os comandos do corpo da função
        visit(fun.body);
        
        dedent();
    }

    private void visit(Cmd cmd) {
        if (cmd == null) return;

        if (cmd instanceof CmdBlock block) {
            if (block.commands.isEmpty()) {
                appendLine("pass");
            } else {
                for(Cmd c : block.commands) {
                    visit(c);
                }
            }
        } 
        else if (cmd instanceof CmdAssign assign) {
            String target = visit(assign.target);
            String value = visit(assign.value);
            appendLine(target + " = " + value);
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
        else if (cmd instanceof CmdIterate loop) {

            // pega o tipo da expressão do alcance
            Type rangeType = ((ExprBase)loop.range).type;
            if (rangeType == null) {
                throw new RuntimeException("Erro de Geração: O tipo da expressão do iterate não foi determinado pela análise semântica.");
            }

            // traduz a expressão do alcance para python
            String rangeExpr = visit(loop.range);

            // decide qual tipo de 'for' gerar com base no tipo da expressão
            if (rangeType.isA("Int")) {
                String loopVar = (loop.varName != null) ? loop.varName : "_";
                appendLine("for " + loopVar + " in range(" + rangeExpr + "):");

            } else if (rangeType instanceof TypeArray) {
                String loopVar = loop.varName; 
                appendLine("for " + loopVar + " in " + rangeExpr + ":");
            }

            indent();
            visit(loop.body);
            dedent();
        }
        else if (cmd instanceof CmdPrint p) {
            // print() em python pula uma linha.
            if (p.expr instanceof ExprChar c && c.value == '\n') {
                appendLine("print()");
            } else {
                // utiliza o parâmetro end='' para que o print do python não pule uma linha automaticamente.
                String exprStr = visit(p.expr);
                appendLine("print(" + exprStr + ", end='')");
            }
        }
        else {
            throw new RuntimeException("Geração S2S não implementada para o comando: " + cmd.getClass().getSimpleName());
        }
    }

    private String visit(Expr expr) {
        if (expr instanceof ExprInt i) {
            return String.valueOf(i.value);
        }
        else if (expr instanceof ExprFloat f) {
            return String.valueOf(f.value);
        }
        else if (expr instanceof ExprBool b) {
            return b.value ? "True" : "False";
        }
        else if (expr instanceof ExprChar c) {
            return switch (c.value) {
                case '\n' -> "'\\n'";
                case '\t' -> "'\\t'";
                case '\'' -> "'\\''";
                case '\\' -> "'\\\\'";
                default -> "'" + c.value + "'";
            };
        } 
        else if (expr instanceof ExprNull n) {
            return "None";
        }
        else if (expr instanceof ExprUnary u) {
            // '!' => 'not'
            String op = u.op.equals("!") ? "not " : u.op;

            String operand = visit(u.expr);

            // retorna a expressão Python
            return op + "(" + operand + ")";
        }
        else if (expr instanceof ExprBinary b) {
            // visita recursivamente lado esquerdo e direito
            String left = visit(b.left);
            String right = visit(b.right);

            // '&&' => 'and'
            String op = b.op.equals("&&") ? "and" : b.op;
            
            return "(" + left + " " + op + " " + right + ")";
        }
        else if (expr instanceof ExprParen p) {
            return "(" + visit(p.inner) + ")";
        }
        else if (expr instanceof ExprLValue lvalExpr) {
            return visit(lvalExpr.lvalue);
        }
        
        throw new RuntimeException("Geração S2S não implementada para a expressão: " + expr.getClass().getSimpleName());
    }

    private String visit(LValue lvalue) {
        if (lvalue instanceof LValueVar var) {
            return var.name;
        }
        else if (lvalue instanceof LValueArrayAccess arrAccess) {
            // v[i] é traduzido recursivamente para 'visit(v)[visit(i)]'
            String array = visit(arrAccess.array);
            String index = visit(arrAccess.index);
            return array + "[" + index + "]";
        }
        else if (lvalue instanceof LValueRecordAccess recAccess) {
            // p.x é traduzido recursivamente para 'visit(p).x'
            String record = visit(recAccess.record);
            String field = recAccess.field;
            
            // lógica para dados abstratos (adiciona '_')
            if (((LValueBase)recAccess.record).type instanceof TypeBase tb) {
                DataDef recordDef = recordTable.get(tb.name);
                if (recordDef != null && recordDef.isAbstract) {
                    field = "_" + field;
                }
            }
            
            return record + "." + field;
        }

        throw new RuntimeException("Geração S2S não implementada para o LValue: " + lvalue.getClass().getSimpleName());
    }
    // método auxiliar para obter valores padrão em Python
    private String getDefaultValue(Type type) {
        if (type.isA("Int") || type.isA("Float")) return "0";
        if (type.isA("Bool")) return "False";
        if (type.isA("Char")) return "''";
        // para arrays e registros, o padrão é None
        return "None";
    }
}