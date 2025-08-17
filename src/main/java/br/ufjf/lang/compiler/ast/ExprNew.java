//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class ExprNew extends ExprBase {

    public final Type typeToCreate;
    public final Expr size; // pode ser null se for apenas "new T"

    public ExprNew(Type typeToCreate, Expr size) {
        this.typeToCreate = typeToCreate;
        this.size = size;
    }
}