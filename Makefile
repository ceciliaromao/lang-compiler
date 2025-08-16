# Gerar parser ANTLR
generate:
	java -jar antlr-4.13.1-complete.jar -Dlanguage=Java -visitor -package br.ufjf.lang.compiler.parser -o ./ src/main/java/br/ufjf/lang/compiler/parser/Lang.g4

# Executar análise sintática:
run-syn:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -syn $(file)

# Executar a verificação de tipos:
run-t:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -t $(file)

# Interpretar programa:
run-i:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -i $(file)

# Executar geração source-to-source (Python):
run-src:
	# mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -i $(file)

# Executar geração de código baixo nível (Jasmin):
run-gen:
	mvn -q package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -gen $(file)
	java -jar jasmin.jar -d output output/*.j
	java -cp output $$(basename -s .lan $(file))

# Limpar arquivos gerados
clean:
	rm -rf target output *.class

install:
	mvn install

compile:
	mvn compile