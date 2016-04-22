JFLAGS = -g
JC = javac
JVM = java
MAIN = Crawler
.SUFFIXES: .java .class
#.java.class: suffix rule (obsolete)
#pattern rule
%.class: %.java 
	$(JC) $(JFLAGS) -classpath lib/json.jar: $*.java

CLASSES = \
        Crawler.java \
        Date_Tracker.java \
        Generate_Queries.java \
        Get_Doc.java \
        Get_List_Docs.java \
        Network.java

default: clean classes run

classes: $(CLASSES:.java=.class)

run: classes
	$(JVM) -classpath lib/json.jar: $(MAIN)

clean:
	$(RM) *.class