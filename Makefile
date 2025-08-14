# Gerar parser ANTLR
generate:
	java -jar antlr-4.13.1-complete.jar -Dlanguage=Java -visitor -package br.ufjf.lang.compiler.parser -o ./ src/main/java/br/ufjf/lang/compiler/parser/Lang.g4

# Executar análise sintática:
run-syn:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -syn src/main/resources/sintaxe/errado/attrCHAR.lan

# Interpretar programa:
run-i:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -i src/main/resources/semantica/certo/full/AFD.lan

clean:
	mvn clean
	rm -rf src/main/java/br/ufjf/lang/compiler/parser/Lang*.java
	rm -rf src/main/java/br/ufjf/lang/compiler/parser/*.interp
	rm -rf src/main/java/br/ufjf/lang/compiler/parser/*.tokens

install:
	mvn install

compile:
	mvn compile