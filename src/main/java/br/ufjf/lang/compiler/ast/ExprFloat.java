package br.ufjf.lang.compiler.ast;

public class ExprFloat implements Expr {
    public final double value;

    public ExprFloat(double value) {
        this.value = value;
    }
}