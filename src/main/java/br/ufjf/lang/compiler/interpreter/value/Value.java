package br.ufjf.lang.compiler.interpreter.value;

public abstract class Value {
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}