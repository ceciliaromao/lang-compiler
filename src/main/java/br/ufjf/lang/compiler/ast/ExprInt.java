package br.ufjf.lang.compiler.ast;

public class ExprInt implements Expr {
    public final int value;

    public ExprInt(int value) {
        this.value = value;
    }
}