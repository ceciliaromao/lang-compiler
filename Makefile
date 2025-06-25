# Gerar parser ANTLR
generate:
	java -jar antlr-4.13.1-complete.jar -Dlanguage=Java -visitor -package br.ufjf.lang.compiler.parser -o ./ src/antlr4/Lang.g4

# Compilar projeto
compile:
	mvn compile

# Executar análise sintática:
run-syn:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -syn src/main/resources/exemplo.lang

# Interpretar programa:
run-i:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -i src/main/resources/exemplo.lang

# Limpar arquivos gerados
clean:
	mvn clean
	rm -rf src/antlr4/Lang*.java