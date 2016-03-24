all:
	javac -classpath lib/ -d lib/ src/*.java 
	
docs:
	javadoc -classpath lib/ -d docs/ -private mrfoops src/*.java

run:
	java -classpath lib mrfoops.Application
