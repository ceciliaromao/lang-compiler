//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprBool extends ExprBase {

    public final boolean value;

    public ExprBool(boolean value) {
        this.value = value;
    }
}