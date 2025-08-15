package br.ufjf.lang.compiler.ast;

import java.util.Objects;

import java.util.Map;

public class TypeRecord implements Type {
    public final String name;
    public final Map<String, Type> fields;

    public TypeRecord(String name, Map<String, Type> fields) {
        this.name = name;
        this.fields = fields;
    }

    @Override
    public boolean isA(String typeName) {
        return false; // Um tipo record não é um tipo base
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TypeRecord that = (TypeRecord) obj;
        return Objects.equals(name, that.name);
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