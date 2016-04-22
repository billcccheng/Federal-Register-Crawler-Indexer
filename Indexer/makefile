JFLAGS = -g
JC = javac
JVM = java
MAIN = Indexer
.SUFFIXES: .java .class
#.java.class: suffix rule (obsolete)
#pattern rule
%.class: %.java 
	$(JC) $(JFLAGS) -classpath lib/*: $*.java

CLASSES = \
		Indexer.java \
        Get_Doc.java \
        Put_Doc.java \
        Parse_Document.java

default: clean classes run

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) -classpath lib/*: $(MAIN)

clean:
	$(RM) *.class