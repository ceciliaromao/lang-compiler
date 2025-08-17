//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)

package br.ufjf.lang.compiler.ast;

public class CmdPrint implements Cmd {
    public final Expr expr;

    public CmdPrint(Expr expr) {
        this.expr = expr;
    }
}