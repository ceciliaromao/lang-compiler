package br.ufjf.lang.compiler.ast;

public class TypeBase implements Type {
    public final String name; // "Int", "Bool", "Float", "TYID", etc.

    public TypeBase(String name) {
        this.name = name;
    }
}