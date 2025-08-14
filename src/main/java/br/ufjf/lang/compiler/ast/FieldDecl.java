package br.ufjf.lang.compiler.ast;

public class FieldDecl {
    public final String name;
    public final Type type;

    public FieldDecl(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}