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
            System.out.println(v);
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
                LValue cond = evalExpr(loop.range, locals, globals);
                if (!asBool(cond)) break;
                executeCmd(loop.body, locals, globals);
            }
            return null;
        }

        if (cmd instanceof CmdRead read) {
            System.out.print("> ");
            String line = scanner.nextLine();
            locals.put(new ExprInt(Integer.parseInt(line)).toString(), read.target);
            return null;
        }

        if (cmd instanceof CmdCall call) {
            FunDef callee = functionTable.get(call.functionName);
            if (callee == null) throw new RuntimeException("Função não encontrada: " + call.functionName);

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
        if (expr instanceof ExprInt i) return (LValue) new ExprInt(i.value);
        if (expr instanceof ExprBool b) return (LValue) new ExprBool(b.value);
        if (expr instanceof ExprFloat f) return (LValue) new ExprFloat(f.value);
        if (expr instanceof ExprChar c) return (LValue) new ExprChar(c.value);
        if (expr instanceof ExprNull) return (LValue) new ExprNull();

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

        throw new RuntimeException("Expressão não suportada: " + expr);
    }

    private LValue evalUnary(String op, LValue val) {
        // Implementar de verdade depois
        throw new UnsupportedOperationException("Unary op não implementado: " + op);
    }

    private LValue evalBinary(String op, LValue left, LValue right) {
        // Implementar de verdade depois
        throw new UnsupportedOperationException("Binary op não implementado: " + op);
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
        if (lvalue instanceof LValueVar v) {
            locals.put(v.name, val);
            return;
        }
        throw new UnsupportedOperationException("Atribuição LValue complexo não implementado");
    }

    private boolean asBool(LValue v) {
        if (v instanceof ExprBool b) return b.value;
        throw new RuntimeException("Esperado Bool, obtido: " + v);
    }
}
