//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprLValue extends ExprBase {

    public final LValue lvalue;

    public ExprLValue(LValue lvalue) {
        this.lvalue = lvalue;
    }
}