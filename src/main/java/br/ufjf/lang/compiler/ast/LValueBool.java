package br.ufjf.lang.compiler.ast;

public class LValueBool implements LValue {
    public final boolean value;

    public LValueBool(boolean value) {
        this.value = value;
    }
}