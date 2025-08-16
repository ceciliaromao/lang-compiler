package br.ufjf.lang.compiler.ast;

public class ExprLValue extends ExprBase {

    public final LValue lvalue;

    public ExprLValue(LValue lvalue) {
        this.lvalue = lvalue;
    }
}