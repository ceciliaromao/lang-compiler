//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprInt extends ExprBase {

    public final int value;

    public ExprInt(int value) {
        this.value = value;
    }
}