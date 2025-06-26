package br.ufjf.lang.compiler.ast;

public class LValueVar implements LValue {
    public final String name;

    public LValueVar(String name) {
        this.name = name;
    }
}