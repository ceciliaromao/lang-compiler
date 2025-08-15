package br.ufjf.lang.compiler.interpreter.value;

public class ValueBool extends Value {
    public boolean value;
    public ValueBool(boolean v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}