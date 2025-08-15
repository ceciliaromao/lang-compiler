package br.ufjf.lang.compiler.ast;

import java.util.Objects;

public class TypeArray implements Type {
    public final Type elementType;

    public TypeArray(Type elementType) {
        this.elementType = elementType;
    }

    @Override
    public boolean isA(String typeName) {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TypeArray typeArray = (TypeArray) obj;
        
        // Compara recursivamente os tipos internos
        return Objects.equals(elementType, typeArray.elementType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementType);
    }

    @Override
    public String toString() {
        return elementType.toString() + "[]";
    }
}