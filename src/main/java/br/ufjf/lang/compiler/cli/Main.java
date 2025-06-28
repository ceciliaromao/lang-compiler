package br.ufjf.lang.compiler.cli;

import br.ufjf.lang.compiler.parser.LangLexer;
import br.ufjf.lang.compiler.parser.LangParser;
import br.ufjf.lang.compiler.ast.Program;
import br.ufjf.lang.compiler.ast.AstBuilderVisitor;
import br.ufjf.lang.compiler.interpreter.Interpreter;

import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java -jar lang-compiler.jar [-syn|-i] caminho/arquivo.lang");
            System.exit(1);
        }

        String mode = args[0];
        String filePath = args[1];

        try {
            if (mode.equals("-syn")) {
                runSyntaxCheck(filePath);
            } else if (mode.equals("-i")) {
                runInterpreter(filePath);
            } else {
                System.err.println("Diretiva inválida: " + mode);
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void runSyntaxCheck(String filePath) throws IOException {
        CharStream input = CharStreams.fromPath(Paths.get(filePath));
        LangLexer lexer = new LangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LangParser parser = new LangParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                System.out.println("reject");
                System.exit(1);
            }
        });

        parser.prog();
        System.out.println("accept");
    }

    private static void runInterpreter(String filePath) throws IOException {
        
        // 1. Parser
        CharStream input = CharStreams.fromPath(Paths.get(filePath));
        LangLexer lexer = new LangLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LangParser parser = new LangParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                    int line, int charPositionInLine,
                                    String msg, RecognitionException e) {
                System.out.println("reject");
                System.exit(1);
            }
        });

        LangParser.ProgContext tree = parser.prog();

        // 2. Constroi AST
        AstBuilderVisitor builder = new AstBuilderVisitor();
        Program ast = (Program) builder.visit(tree);

        // 3. Executa interpreter
        // Interpreter interpreter = new Interpreter();
        // interpreter.run(ast);
    }
}
