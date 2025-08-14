package br.ufjf.lang.compiler.ast;

public class LValueArrayAccess implements LValue {
    public final LValue array;
    public final Expr index;

    public LValueArrayAccess(LValue array, Expr index) {
        this.array = array;
        this.index = index;
    }
}