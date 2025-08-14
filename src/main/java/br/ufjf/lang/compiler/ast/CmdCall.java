package br.ufjf.lang.compiler.ast;

import java.util.List;

public class CmdCall implements Cmd {
    public final String functionName;
    public final List<Expr> arguments;
    public final List<LValue> outputTargets; // pode ser null

    public CmdCall(String functionName, List<Expr> arguments, List<LValue> outputTargets) {
        this.functionName = functionName;
        this.arguments = arguments;
        this.outputTargets = outputTargets;
    }
}