package br.ufjf.lang.compiler.ast;

public class ExprNew extends ExprBase {

    public final Type typeToCreate;
    public final Expr size; // pode ser null se for apenas "new T"

    public ExprNew(Type typeToCreate, Expr size) {
        this.typeToCreate = typeToCreate;
        this.size = size;
    }
}