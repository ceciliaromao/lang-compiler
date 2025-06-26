package br.ufjf.lang.compiler.ast;

public class CmdPrint implements Cmd {
    public final Expr expr;

    public CmdPrint(Expr expr) {
        this.expr = expr;
    }
}