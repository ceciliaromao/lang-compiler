package br.ufjf.lang.compiler.ast;

public class ExprChar extends ExprBase {

    public final char value;

    public ExprChar(char value) {
        this.value = value;
    }
}