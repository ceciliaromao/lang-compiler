//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)

package br.ufjf.lang.compiler.ast;

import java.util.List;

public class CmdReturn implements Cmd {
    public final List<Expr> values;

    public CmdReturn(List<Expr> values) {
        this.values = values;
    }
}