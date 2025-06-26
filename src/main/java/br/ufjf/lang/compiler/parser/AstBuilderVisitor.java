package br.ufjf.lang.compiler.parser;

import br.ufjf.lang.compiler.ast.*;
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

        if (ctx.lvalue() != null && ctx.exp().size() == 1 && ctx.getChild(1).getText().equals("=")) {
            LValue target = (LValue) visit(ctx.lvalue(0));
            Expr value = (Expr) visit(ctx.exp(0));
            return new CmdAssign(target, value);
        }

        if (ctx.ID() != null) {
            // procedure call without output
            String fname = ctx.ID().getText();

            List<Expr> args = new ArrayList<>();
            if (ctx.exps() != null) {
                for (var e : ctx.exps().exp()) {
                    args.add((Expr) visit(e));
                }
            }

            if(ctx.getText().contains("<")) {
                List<LValue> out = new ArrayList<>();
                for (var l : ctx.lvalue()) {
                    out.add((LValue) visit(l));
                }
                return new CmdCall(fname, args, out);
            }

            return new CmdCall(fname, args, null);
        }

        if (ctx.block() != null) {
            return visit(ctx.block());
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
    public Object visitExp(LangParser.ExpContext ctx) {
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

        if (ctx.exp().size() == 1 && ctx.getChildCount() == 2) {
            String op = ctx.getChild(0).getText(); // '!' ou '-'
            Expr expr = (Expr) visit(ctx.exp(0));
            return new ExprUnary(op, expr);
        }

        if (ctx.exp().size() == 2) {
            String op = ctx.getChild(1).getText(); // operador binário
            Expr left = (Expr) visit(ctx.exp(0));
            Expr right = (Expr) visit(ctx.exp(1));
            return new ExprBinary(op, left, right);
        }

        if (ctx.lvalue() != null) {
            return new ExprLValue((LValue) visit(ctx.lvalue()));
        }

        if (ctx.getChildCount() == 3 && ctx.getChild(0).getText().equals("(")) {
            return new ExprParen((Expr) visit(ctx.exp(0)));
        }

        if (ctx.getChildCount() >= 2 && ctx.getChild(0).getText().equals("new")) {
            Type type = (Type) visit(ctx.type());
            Expr size = ctx.exp().isEmpty() ? null : (Expr) visit(ctx.exp(0));
            return new ExprNew(type, size);
        }

        if (ctx.ID() != null && ctx.exps() != null && ctx.exp().size() == 1) {
            String fname = ctx.ID().getText();
            List<Expr> args = new ArrayList<>();
            for (var e : ctx.exps().exp()) {
                args.add((Expr) visit(e));
            }
            Expr index = (Expr) visit(ctx.exp(0));
            return new ExprCall(fname, args, index);
        }

        throw new RuntimeException("Expressão não reconhecida");
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