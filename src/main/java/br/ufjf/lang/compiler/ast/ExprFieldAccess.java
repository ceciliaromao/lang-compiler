//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprFieldAccess extends ExprBase {

    public final Expr target;
    public final String field;

    public ExprFieldAccess(Expr target, String field) {
        this.target = target;
        this.field = field;
    }
}