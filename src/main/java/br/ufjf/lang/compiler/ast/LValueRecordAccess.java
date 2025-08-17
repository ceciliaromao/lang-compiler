//Maria Cecília Romão Santos (202165557C)
//Maria Luisa Riolino Guimarães (202165563C)
package br.ufjf.lang.compiler.ast;

public class LValueRecordAccess extends LValueBase {
    public final LValue record;
    public final String field;

    public LValueRecordAccess(LValue record, String field) {
        this.record = record;
        this.field = field;
    }
}