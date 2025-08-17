//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprUnary extends ExprBase {
    public final String op; // "-" ou "!"
    public final Expr expr;

    public ExprUnary(String op, Expr expr) {
        this.op = op;
        this.expr = expr;
    }
}