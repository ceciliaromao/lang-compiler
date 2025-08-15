package br.ufjf.lang.compiler.ast;

import java.util.Objects;

public class TypeBase implements Type {
    public final String name; // "Int", "Bool", "Float", "TYID", etc.

    public TypeBase(String name) {
        this.name = name;
    }

    @Override
    public boolean isA(String typeName) {
        return this.name.equals(typeName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        TypeBase typeBase = (TypeBase) obj;
        return Objects.equals(name, typeBase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

}