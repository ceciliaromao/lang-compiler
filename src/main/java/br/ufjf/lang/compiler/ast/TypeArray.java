package br.ufjf.lang.compiler.ast;

public class TypeArray implements Type {
    public final Type elementType;

    public TypeArray(Type elementType) {
        this.elementType = elementType;
    }
}