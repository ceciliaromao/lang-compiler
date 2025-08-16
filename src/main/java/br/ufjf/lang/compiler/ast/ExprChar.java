package br.ufjf.lang.compiler.ast;

public class ExprChar extends ExprBase {

    public final String rawValue;

    public ExprChar(String rawValue) {
        this.rawValue = rawValue;
    }
}