package br.ufjf.lang.compiler.interpreter.value;

public class ValueChar extends Value {
    public char value;
    public ValueChar(char v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}
