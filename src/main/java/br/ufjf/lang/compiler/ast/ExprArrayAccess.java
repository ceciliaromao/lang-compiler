package br.ufjf.lang.compiler.ast;

public class ExprArrayAccess implements Expr {
    public final Expr array;
    public final Expr index;

    public ExprArrayAccess(Expr array, Expr index) {
        this.array = array;
        this.index = index;
    }
}