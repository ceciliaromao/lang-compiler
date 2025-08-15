package br.ufjf.lang.compiler.analyzer;

public class SemanticError extends RuntimeException {

    public SemanticError(String message) {
        super(message);
    }
    
}