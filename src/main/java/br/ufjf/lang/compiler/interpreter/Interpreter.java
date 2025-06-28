package br.ufjf.lang.compiler.interpreter;

import br.ufjf.lang.compiler.ast.*;
import java.util.*;

public class Interpreter {

    private final Map<String, FunDef> functionTable = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public void run(Program program) {
        // carrega as funções no "functionTable"
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

        // 3. Executar a função main
        executeFunction(mainFun, List.of(), new HashMap<>());
    }

    private List<Value> executeFunction(FunDef fun, List<Value> args, Map<String, Value> globals) {
        // Cria um ambiente local para os parâmetros
        Map<String, Value> locals = new HashMap<>(globals);

        List<FunDef.Param> params = fun.params;
        for (int i = 0; i < params.size(); i++) {
            locals.put(params.get(i).name(), args.get(i));
        }

        // Executa o corpo
        return executeCmd(fun.body, locals, globals);
    }

    private List<Value> executeCmd(Cmd cmd, Map<String, Value> locals, Map<String, Value> globals) {
        if (cmd instanceof CmdBlock block) {
            for (Cmd c : block.commands) {
                List<Value> ret = executeCmd(c, locals, globals);
                if (ret != null) return ret;
            }
            return null;
        }

        if (cmd instanceof CmdPrint p) {
            Value v = evalExpr(p.expr, locals, globals);
            System.out.println(v);
            return null;
        }

        if (cmd instanceof CmdReturn r) {
            List<Value> values = new ArrayList<>();
            for (Expr e : r.values) {
                values.add(evalExpr(e, locals, globals));
            }
            return values;
        }

        if (cmd instanceof CmdAssign assign) {
            Value val = evalExpr(assign.value, locals, globals);
            setLValue(assign.target, val, locals, globals);
            return null;
        }

        if (cmd instanceof CmdIf iff) {
            Value cond = evalExpr(iff.condition, locals, globals);
            if (asBool(cond)) {
                return executeCmd(iff.thenBranch, locals, globals);
            } else if (iff.elseBranch != null) {
                return executeCmd(iff.elseBranch, locals, globals);
            }
            return null;
        }

        if (cmd instanceof CmdIterate loop) {
            while (true) {
                Value cond = evalExpr(loop.cond(), locals, globals);
                if (!asBool(cond)) break;
                executeCmd(loop.body, locals, globals);
            }
            return null;
        }

        if (cmd instanceof CmdRead read) {
            System.out.print("> ");
            String line = scanner.nextLine();
            locals.put(read.target().name(), new ValueInt(Integer.parseInt(line)));
            return null;
        }

        if (cmd instanceof CmdCall call) {
            FunDef callee = functionTable.get(call.name());
            if (callee == null) throw new RuntimeException("Função não encontrada: " + call.name());

            List<Value> args = new ArrayList<>();
            for (Expr arg : call.args()) {
                args.add(evalExpr(arg, locals, globals));
            }

            List<Value> results = executeFunction(callee, args, globals);

            if (call.outputs() != null && results != null) {
                for (int i = 0; i < call.outputs().size(); i++) {
                    setLValue(call.outputs().get(i), results.get(i), locals, globals);
                }
            }
            return null;
        }

        throw new RuntimeException("Comando não suportado: " + cmd);
    }

    private Value evalExpr(Expr expr, Map<String, Value> locals, Map<String, Value> globals) {
        if (expr instanceof ExprInt i) return new ValueInt(i.value());
        if (expr instanceof ExprBool b) return new ValueBool(b.value());
        if (expr instanceof ExprFloat f) return new ValueFloat(f.value());
        if (expr instanceof ExprChar c) return new ValueChar(c.value());
        if (expr instanceof ExprNull) return ValueNull.INSTANCE;

        if (expr instanceof ExprUnary u) {
            Value val = evalExpr(u.expr(), locals, globals);
            return evalUnary(u.op(), val);
        }

        if (expr instanceof ExprBinary b) {
            Value l = evalExpr(b.left(), locals, globals);
            Value r = evalExpr(b.right(), locals, globals);
            return evalBinary(b.op(), l, r);
        }

        if (expr instanceof ExprLValue lval) {
            return getLValue(lval.lvalue(), locals, globals);
        }

        throw new RuntimeException("Expressão não suportada: " + expr);
    }

    private Value evalUnary(String op, Value val) {
        // Implementar de verdade depois
        throw new UnsupportedOperationException("Unary op não implementado: " + op);
    }

    private Value evalBinary(String op, Value left, Value right) {
        // Implementar de verdade depois
        throw new UnsupportedOperationException("Binary op não implementado: " + op);
    }

    private Value getLValue(LValue lvalue, Map<String, Value> locals, Map<String, Value> globals) {
        if (lvalue instanceof LValueVar v) {
            if (locals.containsKey(v.name())) return locals.get(v.name());
            if (globals.containsKey(v.name())) return globals.get(v.name());
            throw new RuntimeException("Variável não encontrada: " + v.name());
        }
        throw new UnsupportedOperationException("Acesso LValue complexo não implementado");
    }

    private void setLValue(LValue lvalue, Value val, Map<String, Value> locals, Map<String, Value> globals) {
        if (lvalue instanceof LValueVar v) {
            locals.put(v.name(), val);
            return;
        }
        throw new UnsupportedOperationException("Atribuição LValue complexo não implementado");
    }

    private boolean asBool(Value v) {
        if (v instanceof ValueBool b) return b.value();
        throw new RuntimeException("Esperado Bool, obtido: " + v);
    }
}
