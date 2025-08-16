package br.ufjf.lang.compiler.ast;

public class ExprBool extends ExprBase {

    public final boolean value;

    public ExprBool(boolean value) {
        this.value = value;
    }
}