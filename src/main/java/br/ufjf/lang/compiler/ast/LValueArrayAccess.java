package br.ufjf.lang.compiler.ast;

public class LValueArrayAccess extends LValueBase {
    
    public final LValue array;
    public final Expr index;

    public LValueArrayAccess(LValue array, Expr index) {
        this.array = array;
        this.index = index;
    }
}