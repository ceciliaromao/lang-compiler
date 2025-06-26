package br.ufjf.lang.compiler.ast;

public class ExprNew implements Expr {
    public final Type type;
    public final Expr size; // pode ser null se for apenas "new T"

    public ExprNew(Type type, Expr size) {
        this.type = type;
        this.size = size;
    }
}