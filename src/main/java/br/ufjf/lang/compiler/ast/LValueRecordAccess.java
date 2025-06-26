package br.ufjf.lang.compiler.ast;

public class LValueRecordAccess implements LValue {
    public final LValue record;
    public final String field;

    public LValueRecordAccess(LValue record, String field) {
        this.record = record;
        this.field = field;
    }
}