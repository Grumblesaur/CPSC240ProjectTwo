run: all
	java Sequencer

all: Sequencer.java Strand.java StrandReader.java
	javac Sequencer.java

clean:
	rm *.class
