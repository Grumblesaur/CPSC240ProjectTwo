/** StrandReader class for obtaining DNA Strand data from file.
 * CPSC 240 / Object-Oriented Analysis & Design
 * DNA Project
 * @author James Murphy
 * @version 0.1
 */

/* Utilities */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

/* Exceptions */
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.lang.IndexOutOfBoundsException;

public class StrandReader {
	/** Name of file containing DNA Strand information. */
	private String filename;
	
	/** Constructs a StrandReader object */
	public StrandReader(String filename) {
		this.filename = filename;
	}
	
	/** Reads file stored at filepath and creates an ArrayList of Strands.
	 * @return ArrayList of type Strand
	 */ 
	public ArrayList<Strand> parse() {
		/* Resources for data gathering and Strand construction. */
		ArrayList<Strand> DNA = new ArrayList<Strand>();
		String label = "", bases = "";
		List<String> lines = null;
		
		/* Attempt to read in the whole file. */
		try {
			lines = Files.readAllLines(Paths.get(filename));
		} catch (IOException e) {
			System.err.println("Something borked!");
			System.exit(1);
		}
		
		/* Loop to collect DNA strand from specified file. First line is
			just a number we don't need to read in the whole list. */
		for (int i = 1; i < lines.size(); i += 2) {
			/* Lines are listed pairwise with no separators but newlines.
				Empty lines are not an issue. */
			try {
				label = lines.get(i);
				bases = lines.get(i + 1);
			} catch (IndexOutOfBoundsException e) {
				throw new IllegalStateException("File is improperly " +
					"formatted!");
			}
			
			/* Transform label-bases pair into a Strand */
			Strand current = null;
			try {
				current = new Strand(label, bases);
				DNA.add(current);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(
					"Invalid nucleotide present in '" + bases + "'.");
			} catch (IllegalStateException e) {
				throw new IllegalStateException(
					"Too many nucleotides in '" + bases + "'!");
			} finally {
				System.out.println("...");
			}
		}
		return DNA;
	}
}	
