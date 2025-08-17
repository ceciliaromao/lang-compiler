//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprParen extends ExprBase {
    public final Expr inner;

    public ExprParen(Expr inner) {
        this.inner = inner;
    }
}