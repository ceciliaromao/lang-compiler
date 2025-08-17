//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

import java.util.List;

public class ExprCall extends ExprBase {

    public final String functionName;
    public final List<Expr> arguments;
    public final Expr index; // para acesso [n] / pode ser null se não houver acesso [n]

    public ExprCall(String functionName, List<Expr> arguments, Expr index) {
        this.functionName = functionName;
        this.arguments = arguments;
        this.index = index;
    }
}