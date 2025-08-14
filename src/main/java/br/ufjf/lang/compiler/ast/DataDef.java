package br.ufjf.lang.compiler.ast;

import java.util.List;

public class DataDef implements Def {
    public final String name;
    public final boolean isAbstract;
    public final List<FieldDecl> fields;

    public DataDef(String name, boolean isAbstract, List<FieldDecl> fields) {
        this.name = name;
        this.isAbstract = isAbstract;
        this.fields = fields;
    }
}