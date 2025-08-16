package br.ufjf.lang.compiler.ast;

public class ExprChar extends ExprBase {

    public final String rawValue;
    public final char value;

    public ExprChar(String rawValue, char value) {
        this.rawValue = rawValue;
        this.value = value;
    }
}