package br.ufjf.lang.compiler.ast;

public class ExprParen implements Expr {
    public Type type;
    public final Expr inner;

    public ExprParen(Expr inner) {
        this.inner = inner;
    }
}