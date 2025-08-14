package br.ufjf.lang.compiler.utils;

public class ErrorHandler {
    public static void reportError(String message, int line, int column) {
        System.err.printf("Error at line %d, column %d: %s%n", line, column, message);
    }

    public static void reportWarning(String message, int line, int column) {
        System.out.printf("Warning at line %d, column %d: %s%n", line, column, message);
    }

    public static void reportInfo(String message, int line, int column) {
        System.out.printf("Info at line %d, column %d: %s%n", line, column, message);
    }

    public static void reportFatalError(String message, int line, int column) {
        System.err.printf("Fatal error at line %d, column %d: %s%n", line, column, message);
        System.exit(1);
    }

    public static void reportSyntaxError(String message, int line, int column) {
        System.err.printf("Syntax error at line %d, column %d: %s%n", line, column, message);
        System.exit(1);
    }

    public static void reportSemanticError(String message, int line, int column) {
        System.err.printf("Semantic error at line %d, column %d: %s%n", line, column, message);
        System.exit(1);
    }

    public static void reportRuntimeError(String message, int line, int column) {
        System.err.printf("Runtime error at line %d, column %d: %s%n", line, column, message);
        System.exit(1);
    }

    public static void reportTypeError(String message, int line, int column) {
        System.err.printf("Type error at line %d, column %d: %s%n", line, column, message);
        System.exit(1);
    }
    
}
