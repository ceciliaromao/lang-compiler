package br.ufjf.lang.compiler.ast;

public class ExprBinary implements Expr {
    public final String op; // "+", "-", "*", etc.
    public final Expr left;
    public final Expr right;

    public ExprBinary(String op, Expr left, Expr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }
}