run-syn:
	mvn package
	java -cp target/lang-compiler-1.0-SNAPSHOT.jar br.ufjf.lang.compiler.cli.Main -syn exemplo.lang

run-i:
	mvn package
	java -cp target/lang-compiler-1.0-SNAPSHOT.jar br.ufjf.lang.compiler.cli.Main -i exemplo.lang