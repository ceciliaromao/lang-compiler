package br.ufjf.lang.compiler.interpreter.value;

public class ValueInt extends Value {
    public int value;
    public ValueInt(int v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}