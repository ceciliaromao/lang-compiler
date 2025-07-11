package br.ufjf.lang.compiler.ast;

public class LValueChar implements LValue {
    public final char value;

    public LValueChar(char value) {
        this.value = value;
    }
}