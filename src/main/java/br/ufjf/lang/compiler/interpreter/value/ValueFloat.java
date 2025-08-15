package br.ufjf.lang.compiler.interpreter.value;

public class ValueFloat extends Value {
    public double value;
    public ValueFloat(double v) { this.value = v; }
    @Override public String toString() { return String.valueOf(value); }
}