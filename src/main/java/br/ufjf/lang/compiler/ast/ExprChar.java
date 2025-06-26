package br.ufjf.lang.compiler.ast;

public class ExprChar implements Expr {
    public final char value;

    public ExprChar(char value) {
        this.value = value;
    }
}