package br.ufjf.lang.compiler.ast;

import java.util.Map;

public class TypeRecord implements Type {
    public final String name;
    public final Map<String, Type> fields;

    public TypeRecord(String name, Map<String, Type> fields) {
        this.name = name;
        this.fields = fields;
    }
}