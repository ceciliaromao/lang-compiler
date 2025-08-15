package br.ufjf.lang.compiler.ast;

public class ExprFieldAccess implements Expr {
    public Type type;
    public final Expr target;
    public final String field;

    public ExprFieldAccess(Expr target, String field) {
        this.target = target;
        this.field = field;
    }
}