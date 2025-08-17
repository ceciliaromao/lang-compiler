//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)

package br.ufjf.lang.compiler.ast;

public class CmdIf implements Cmd {
    public final Expr condition;
    public final Cmd thenBranch;
    public final Cmd elseBranch; // pode ser null

    public CmdIf(Expr condition, Cmd thenBranch, Cmd elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
}