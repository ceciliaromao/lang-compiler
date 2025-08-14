package br.ufjf.lang.compiler.ast;

public class CmdIterate implements Cmd {
    public final String varName; // pode ser null
    public final Expr range;
    public final Cmd body;

    public CmdIterate(String varName, Expr range, Cmd body) {
        this.varName = varName;
        this.range = range;
        this.body = body;
    }
}