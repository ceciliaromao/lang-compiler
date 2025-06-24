#Executar análise sintática:
run-syn:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -syn exemplo.lang

#Interpretar programa:
run-i:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -i exemplo.lang