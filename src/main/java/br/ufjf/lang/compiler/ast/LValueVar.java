package br.ufjf.lang.compiler.ast;

public class LValueVar extends LValueBase {
    public final String name;

    public LValueVar(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}