package br.ufjf.lang.compiler.ast;

import java.util.List;

public class Program {
    public final List<Def> definitions;

    public Program(List<Def> definitions) {
        this.definitions = definitions;
    }
}