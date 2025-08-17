//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)

package br.ufjf.lang.compiler.ast;

import java.util.List;

public class CmdBlock implements Cmd {
    public final List<Cmd> commands;

    public CmdBlock(List<Cmd> commands) {
        this.commands = commands;
    }
}