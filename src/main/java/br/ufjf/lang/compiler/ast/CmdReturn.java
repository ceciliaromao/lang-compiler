package br.ufjf.lang.compiler.ast;

import java.util.List;

public class CmdReturn implements Cmd {
    public final List<Expr> values;

    public CmdReturn(List<Expr> values) {
        this.values = values;
    }
}