package br.ufjf.lang.compiler.ast;

import java.util.List;

public class ExprCall implements Expr {
    public final String functionName;
    public final List<Expr> arguments;
    public final Expr index; // para acesso [n] / pode ser null se não houver acesso [n]

    public ExprCall(String functionName, List<Expr> arguments, Expr index) {
        this.functionName = functionName;
        this.arguments = arguments;
        this.index = index;
    }
}