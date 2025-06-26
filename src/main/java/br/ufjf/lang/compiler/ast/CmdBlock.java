package br.ufjf.lang.compiler.ast;

import java.util.List;

public class CmdBlock implements Cmd {
    public final List<Cmd> commands;

    public CmdBlock(List<Cmd> commands) {
        this.commands = commands;
    }
}