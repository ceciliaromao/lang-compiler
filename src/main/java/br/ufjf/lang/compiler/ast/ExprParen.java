package br.ufjf.lang.compiler.ast;

public class ExprParen extends ExprBase {
    public final Expr inner;

    public ExprParen(Expr inner) {
        this.inner = inner;
    }
}