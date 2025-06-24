run-syn:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -syn exemplo.lang

run-i:
	mvn package
	java -jar target/lang-compiler-1.0-SNAPSHOT.jar -i exemplo.lang