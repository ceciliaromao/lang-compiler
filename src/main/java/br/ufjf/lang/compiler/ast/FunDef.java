package br.ufjf.lang.compiler.ast;

import java.util.List;

public class FunDef implements Def {
    public final String name;
    public final List<Param> params;
    public final List<Type> returnTypes;
    public final Cmd body;

    public FunDef(String name, List<Param> params, List<Type> returnTypes, Cmd body) {
        this.name = name;
        this.params = params;
        this.returnTypes = returnTypes;
        this.body = body;
    }

    public record Param(String name, Type type) {}
}