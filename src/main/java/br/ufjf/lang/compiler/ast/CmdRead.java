//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class CmdRead implements Cmd {
    public final LValue target;

    public CmdRead(LValue target) {
        this.target = target;
    }

    public LValue target() {
        return target;
    }
}