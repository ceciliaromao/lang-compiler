//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprBinary extends ExprBase {

    public final String op; // "+", "-", "*", etc.
    public final Expr left;
    public final Expr right;

    public ExprBinary(String op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
}