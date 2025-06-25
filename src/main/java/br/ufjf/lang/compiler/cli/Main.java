//Maria Cecília Romão Santos    202165557C
//Maria Luisa Riolino Guimarães 202165563C
package br.ufjf.lang.compiler.cli;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Uso: java -jar lang-compiler.jar [-syn|-i] arquivo.lang");
            System.exit(1);
        }

        String directive = args[0];
        String filename = args[1];

        switch (directive) {
            case "-syn":
                System.out.println("Executar análise sintática...");
                break;
            case "-i":
                System.out.println("Interpretar programa...");
                break;
            default:
                System.err.println("Diretiva inválida: " + directive);
                System.exit(1);
        }
    }
}
