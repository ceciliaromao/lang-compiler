//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C

package br.ufjf.lang.compiler.ast;

public class CmdAssign implements Cmd {
    public final LValue target;
    public final Expr value;

    public CmdAssign(LValue target, Expr value) {
        this.target = target;
        this.value = value;
    }
}