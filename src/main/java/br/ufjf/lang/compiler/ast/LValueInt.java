package br.ufjf.lang.compiler.ast;

public class LValueInt implements LValue {
    public final int value;

    public LValueInt(int value) {
        this.value = value;
    }
}