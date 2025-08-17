//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprFloat extends ExprBase {

    public final double value;

    public ExprFloat(double value) {
        this.value = value;
    }
}