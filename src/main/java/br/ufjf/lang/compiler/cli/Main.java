//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C

package br.ufjf.lang.compiler.cli;

import br.ufjf.lang.compiler.parser.LangLexer;
import br.ufjf.lang.compiler.parser.LangParser;

import br.ufjf.lang.compiler.ast.Program;
import br.ufjf.lang.compiler.ast.AstBuilderVisitor;

import br.ufjf.lang.compiler.interpreter.Interpreter;

import br.ufjf.lang.compiler.analyzer.SemanticAnalyzer;
import br.ufjf.lang.compiler.analyzer.SemanticError;

import org.antlr.v4.runtime.*;

import java.io.*;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java -jar lang-compiler.jar [-syn|-i|-t] caminho/arquivo.lang");
            System.exit(1);
        }

        String mode = args[0];
        String filePath = args[1];

        try {
            if (mode.equals("-syn")) {
                runSyntaxCheck(filePath);
            } else if (mode.equals("-i")) {
                runInterpreter(filePath);
            } else if (mode.equals("-t")) {
                runSemanticCheck(filePath);
            } 
            else {
                System.err.println("Diretiva inválida: " + mode);
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            System.exit(1);
        }
    }

    private static Program buildAst(String filePath) throws IOException {
        CharStream input = CharStreams.fromPath(Paths.get(filePath));
        LangLexer lexer = new LangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LangParser parser = new LangParser(tokens);

        lexer.removeErrorListeners();
        parser.removeErrorListeners();

        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                System.out.println("reject");
                // Lança uma exceção para parar a execução em caso de erro sintático
                throw new RuntimeException("Erro de sintaxe: " + msg, e);
            }
        });

        LangParser.ProgContext tree = parser.prog();

        AstBuilderVisitor builder = new AstBuilderVisitor();
        return (Program) builder.visit(tree);
    }

    private static void runSyntaxCheck(String filePath) throws IOException {
        try {
            buildAst(filePath);
            System.out.println("accept");
        } catch (RuntimeException e) {
            // Se buildAst lançar uma exceção, a sintaxe é inválida.
            System.exit(1);
        }
    }

    private static void runInterpreter(String filePath) throws IOException {
        try {
            Program ast = buildAst(filePath);

            Interpreter interpreter = new Interpreter();
            interpreter.run(ast);
            System.out.println();

        } catch (RuntimeException e) {
            // captura tanto erros de sintaxe (do buildAst) quanto erros de execução.
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void runSemanticCheck(String filePath) throws IOException {
        try {
            Program ast = buildAst(filePath);

            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            analyzer.analyze(ast);

            System.out.println("accept");

        } catch (RuntimeException e) {
            System.out.println("reject");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

}