package br.ufjf.lang.compiler.ast;

public class LValueVar implements LValue {
    public final String name;
    public Type type;

    public LValueVar(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}