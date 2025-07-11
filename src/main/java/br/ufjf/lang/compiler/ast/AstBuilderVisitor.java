//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C

package br.ufjf.lang.compiler.ast;

import br.ufjf.lang.compiler.parser.*;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class AstBuilderVisitor extends LangBaseVisitor<Object> {

    @Override
    public Object visitProg(LangParser.ProgContext ctx) {
        List<Def> defs = new ArrayList<>();
        for (var defCtx : ctx.def()) {
            defs.add((Def) visit(defCtx));
        }
        return new Program(defs);
    }

    @Override
    public Object visitFun(LangParser.FunContext ctx) {
        String name = ctx.ID().getText();

        List<FunDef.Param> params = new ArrayList<>();
        if (ctx.params() != null) {
            for (int i = 0; i < ctx.params().ID().size(); i++) {
                String paramName = ctx.params().ID(i).getText();
                Type paramType = (Type) visit(ctx.params().type(i));
                params.add(new FunDef.Param(paramName, paramType));
            }
        }

        List<Type> returnTypes = new ArrayList<>();
        if (ctx.type() != null) {
            for (var t : ctx.type()) {
                returnTypes.add((Type) visit(t));
            }
        }

        Cmd body = (Cmd) visit(ctx.cmd());

        return new FunDef(name, params, returnTypes, body);
    }

    @Override
    public Object visitType(LangParser.TypeContext ctx) {
        if (ctx.btype() != null) {
            return visit(ctx.btype());
        } else {
            Type inner = (Type) visit(ctx.type());
            return new TypeArray(inner);
        }
    }

    @Override
    public Object visitBtype(LangParser.BtypeContext ctx) {
        return new TypeBase(ctx.getText());
    }

    @Override
    public Object visitCmd(LangParser.CmdContext ctx) {
        // Verifica se o comando é um bloco
        if (ctx.block() != null) {
            return visit(ctx.block());
        }

        // Obtém o primeiro token do comando
        String first = ctx.getChild(0).getText();

        switch (first) {
            case "print" -> {
                Expr expr = (Expr) visit(ctx.exp(0));
                return new CmdPrint(expr);
            }
            case "return" -> {
                List<Expr> values = new ArrayList<>();
                for (var e : ctx.exp()) {
                    values.add((Expr) visit(e));
                }
                return new CmdReturn(values);
            }
            case "if" -> {
                Expr cond = (Expr) visit(ctx.exp(0));
                Cmd thenBranch = (Cmd) visit(ctx.cmd(0));
                Cmd elseBranch = ctx.cmd().size() > 1 ? (Cmd) visit(ctx.cmd(1)) : null;
                return new CmdIf(cond, thenBranch, elseBranch);
            }
            case "iterate" -> {
                String var = ctx.ID() != null ? ctx.ID().getText() : null;
                Expr cond = (Expr) visit(ctx.exp(0));
                Cmd body = (Cmd) visit(ctx.cmd(0));
                return new CmdIterate(var, cond, body);
            }
            case "read" -> {
                return new CmdRead((LValue) visit(ctx.lvalue(0)));
            }
        }

        // Verifica se é uma atribuição
        if (ctx.lvalue() != null && ctx.getChild(1).getText().equals("=")) {
            LValue target = (LValue) visit(ctx.lvalue(0));
            Expr value = (Expr) visit(ctx.exp(0));
            return new CmdAssign(target, value);
        }

        // Verifica se é uma chamada de função
        if (ctx.ID() != null) {
            String fname = ctx.ID().getText();

            List<Expr> args = new ArrayList<>();
            if (ctx.exps() != null) {
                for (var e : ctx.exps().exp()) {
                    args.add((Expr) visit(e));
                }
            }

            if (ctx.getText().contains("<")) {
                List<LValue> out = new ArrayList<>();
                for (var l : ctx.lvalue()) {
                    out.add((LValue) visit(l));
                }
                return new CmdCall(fname, args, out);
            }

            return new CmdCall(fname, args, null);
        }

        throw new RuntimeException("Comando não reconhecido");
    }

    @Override
    public Object visitBlock(LangParser.BlockContext ctx) {
        List<Cmd> cmds = new ArrayList<>();
        for (var c : ctx.cmd()) {
            cmds.add((Cmd) visit(c));
        }
        return new CmdBlock(cmds);
    }

    @Override
    public Object visitExpAnd(LangParser.ExpAndContext ctx) {
        Expr expr = (Expr) visit(ctx.expEq(0));
        for (int i = 1; i < ctx.expEq().size(); i++) {
            Expr right = (Expr) visit(ctx.expEq(i));
            expr = new ExprBinary("&&", expr, right);
        }
        return expr;
    }

    @Override
    public Object visitExpEq(LangParser.ExpEqContext ctx) {
        Expr expr = (Expr) visit(ctx.expRel(0));
        for (int i = 1; i < ctx.expRel().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expr right = (Expr) visit(ctx.expRel(i));
            expr = new ExprBinary(op, expr, right);
        }
        return expr;
    }

    @Override
    public Object visitExpRel(LangParser.ExpRelContext ctx) {
        Expr left = (Expr) visit(ctx.expAdd(0));
        if (ctx.expAdd().size() > 1) {
            Expr right = (Expr) visit(ctx.expAdd(1));
            return new ExprBinary("<", left, right);
        }
        return left;
    }

    @Override
    public Object visitExpAdd(LangParser.ExpAddContext ctx) {
        Expr expr = (Expr) visit(ctx.expMul(0));
        for (int i = 1; i < ctx.expMul().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expr right = (Expr) visit(ctx.expMul(i));
            expr = new ExprBinary(op, expr, right);
        }
        return expr;
    }

    @Override
    public Object visitExpMul(LangParser.ExpMulContext ctx) {
        Expr expr = (Expr) visit(ctx.expUnary(0));
        for (int i = 1; i < ctx.expUnary().size(); i++) {
            String op = ctx.getChild(2 * i - 1).getText();
            Expr right = (Expr) visit(ctx.expUnary(i));
            expr = new ExprBinary(op, expr, right);
        }
        return expr;
    }

    @Override
    public Object visitExpUnary(LangParser.ExpUnaryContext ctx) {
        if (ctx.getChildCount() == 2) {
            String op = ctx.getChild(0).getText(); // '!' ou '-'
            Expr operand = (Expr) visit(ctx.expUnary());
            return new ExprUnary(op, operand);
        }
        return visit(ctx.expPostfix());
    }

    @Override
    public Object visitExpPostfix(LangParser.ExpPostfixContext ctx) {
        Expr expr = (Expr) visit(ctx.expPrimary());
        int index = 1;
        while (index < ctx.getChildCount()) {
            String op = ctx.getChild(index).getText();
            if (op.equals("[")) {
                Expr accessIndex = (Expr) visit(ctx.exp(index / 2));
                expr = new ExprArrayAccess(expr, accessIndex);
                index += 3;
            } else if (op.equals(".")) {
                String field = ctx.getChild(index + 1).getText();
                expr = new ExprFieldAccess(expr, field);
                index += 2;
            } else {
                index++;
            }
        }
        return expr;
    }

    @Override
    public Object visitExpPrimary(LangParser.ExpPrimaryContext ctx) {
        if (ctx.INT() != null) {
            return new ExprInt(Integer.parseInt(ctx.INT().getText()));
        }
        if (ctx.FLOAT() != null) {
            return new ExprFloat(Double.parseDouble(ctx.FLOAT().getText()));
        }
        if (ctx.CHAR() != null) {
            String raw = ctx.CHAR().getText();
            char c = raw.charAt(1); // assumindo aspas simples válidas
            return new ExprChar(c);
        }

        String text = ctx.getText();
        if (text.equals("true") || text.equals("false")) {
            return new ExprBool(text.equals("true"));
        }
        if (text.equals("null")) {
            return new ExprNull();
        }

        if (ctx.lvalue() != null) {
            return new ExprLValue((LValue) visit(ctx.lvalue()));
        }

        if (ctx.getChildCount() == 3 && ctx.getChild(0).getText().equals("(")) {
            return new ExprParen((Expr) visit(ctx.exp()));
        }

        if (ctx.getChildCount() >= 2 && ctx.getChild(0).getText().equals("new")) {
            Type type = (Type) visit(ctx.type());
            Expr size = ctx.exp() != null ? (Expr) visit(ctx.exp()) : null;
            return new ExprNew(type, size);
        }

        if (ctx.ID() != null && ctx.exps() != null) {
            String fname = ctx.ID().getText();
            List<Expr> args = new ArrayList<>();
            for (var e : ctx.exps().exp()) {
                args.add((Expr) visit(e));
            }
            Expr index = (Expr) visit(ctx.exp());
            return new ExprCall(fname, args, index);
        }

        throw new RuntimeException("Expressão primária não reconhecida");
    }


    @Override
    public Object visitLvalue(LangParser.LvalueContext ctx) {
        if (ctx.ID() != null && ctx.lvalue() == null) {
            return new LValueVar(ctx.ID().getText());
        }

        if (ctx.lvalue() != null && ctx.exp() != null) {
            return new LValueArrayAccess((LValue) visit(ctx.lvalue()), (Expr) visit(ctx.exp()));
        }

        if (ctx.lvalue() != null && ctx.ID() != null) {
            return new LValueRecordAccess((LValue) visit(ctx.lvalue()), ctx.ID().getText());
        }

        throw new RuntimeException("Lvalue não reconhecido");
    }
}