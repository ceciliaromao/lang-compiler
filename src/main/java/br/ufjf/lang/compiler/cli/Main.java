//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C

package br.ufjf.lang.compiler.cli;

import br.ufjf.lang.compiler.parser.LangLexer;
import br.ufjf.lang.compiler.parser.LangParser;

import br.ufjf.lang.compiler.ast.DataDef;
import br.ufjf.lang.compiler.ast.Program;
import br.ufjf.lang.compiler.ast.AstBuilderVisitor;

import br.ufjf.lang.compiler.interpreter.Interpreter;

import br.ufjf.lang.compiler.analyzer.SemanticAnalyzer;
import br.ufjf.lang.compiler.analyzer.SemanticError;

import br.ufjf.lang.compiler.generator.S2SGenerator;
import br.ufjf.lang.compiler.generator.JasminGenerator;

import org.antlr.v4.runtime.*;

import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java -jar lang-compiler.jar [-syn|-i|-t|-src|-gen] caminho/arquivo.lang");
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
            else if (mode.equals("-src")) {
                runS2SGenerator(filePath);
            } 
            else if (mode.equals("-gen")) {
                runJasminGenerator(filePath);
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

    private static void runS2SGenerator(String filePath) throws IOException {
        try {
            // análise sintática e construção da AST
            Program ast = buildAst(filePath);

            // análise semântica
            SemanticAnalyzer analyzer = new SemanticAnalyzer();
            analyzer.analyze(ast);

            Map<String, DataDef> recordTable = analyzer.getRecordTable();

            // geração de código source-to-source
            S2SGenerator generator = new S2SGenerator(recordTable);
            String pythonCode = generator.generate(ast);

            String outputFilePath;
            if (filePath.endsWith(".lan")) {
                outputFilePath = filePath.substring(0, filePath.length() - ".lan".length()) + ".py";
            } else {
                outputFilePath = filePath + ".py";
            }

            Files.writeString(Paths.get(outputFilePath), pythonCode);
            
            System.out.println("Arquivo '" + outputFilePath + "' gerado com sucesso.");

        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void runJasminGenerator(String filePath) throws IOException {
    //     try {
    //         Program ast = buildAst(filePath);

    //         SemanticAnalyzer analyzer = new SemanticAnalyzer();
    //         analyzer.analyze(ast);

    //         String baseName = new File(filePath).getName();
    //         baseName = baseName.substring(0, baseName.lastIndexOf('.'));

    //         JasminGenerator generator = new JasminGenerator();
    //         String jasminCode = generator.generate(ast, baseName);

    //         File outputDir = new File("output");
    //         if (!outputDir.exists()) {
    //             outputDir.mkdir();
    //         }

    //         String outputFilePath = "output/" + baseName + ".j";

    //         try (PrintWriter out = new PrintWriter(outputFilePath)) {
    //             out.println(jasminCode);
    //         }

    //         System.out.println("Arquivo '" + outputFilePath + "' gerado com sucesso.");

    //     } catch (RuntimeException e) {
    //         System.err.println(e.getMessage());
    //         System.exit(1);
    //     }
    }
}