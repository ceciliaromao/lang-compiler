package br.ufjf.lang.compiler.ast;

public class LValueFloat implements LValue {
    public final double value;

    public LValueFloat(double value) {
        this.value = value;
    }
}