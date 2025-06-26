package br.ufjf.lang.compiler.ast;

public class ExprBool implements Expr {
    public final boolean value;

    public ExprBool(boolean value) {
        this.value = value;
    }
}