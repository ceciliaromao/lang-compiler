package br.ufjf.lang.compiler.ast;

public class ExprLValue implements Expr {
    public Type type;
    public final LValue lvalue;

    public ExprLValue(LValue lvalue) {
        this.lvalue = lvalue;
    }
}