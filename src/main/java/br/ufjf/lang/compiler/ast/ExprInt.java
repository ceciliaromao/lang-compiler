package br.ufjf.lang.compiler.ast;

public class ExprInt extends ExprBase {

    public final int value;

    public ExprInt(int value) {
        this.value = value;
    }
}