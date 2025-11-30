JAVAC = javac
JAVA = java
MAIN = ILOCScheduler

SOURCES = Token.java IRNode.java ILOCScheduler.java
CLASSES = $(SOURCES:.java=.class)

build: $(CLASSES) schedule

%.class: %.java
	$(JAVAC) $<

412alloc: 
	echo "#!/bin/bash" > schedule
	echo "$(JAVA) $(MAIN) \"\$$@\"" >> schedule
	chmod +x schedule

clean:
	rm -f *.class schedule