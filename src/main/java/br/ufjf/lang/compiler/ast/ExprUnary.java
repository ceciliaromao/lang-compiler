package br.ufjf.lang.compiler.ast;

public class ExprUnary implements Expr {
    public Type type;
    public final String op; // "-" ou "!"
    public final Expr expr;

    public ExprUnary(String op, Expr expr) {
        this.op = op;
        this.expr = expr;
    }
}