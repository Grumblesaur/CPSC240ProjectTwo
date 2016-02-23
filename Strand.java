/** Contains a strand or substrand of DNA. 
 * @author James Murphy
 * @version v0.1
 * CPSC 240 / Object-Oriented Analysis & Design
 * Lab 5 / Writing Clases (Class File)
 */


/* Exceptions */
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class Strand {
	/** The limit to number of nucleotides that a strand can hold. */
	private static final int MAX_NUCLEOTIDES = 100;
	
	/** Nucleotides that are allowed for a strand of DNA. */
	private static final String LEGAL_NUCLEOTIDES = "acgt";
	
	/** The name of the strand of DNA. */
	private String strandName;

	/** The nucleotides in the strand. */
	private String nucleotides;
	
	/** Construct a valid Strand of DNA.
	 * @param strandName The name to be given to the strand.
	 * @param nucleotides A string composed of a, g, c, and t ONLY
	 * @throws IllegalStateException nucleotides is too long.
	 * @throws IllegalArgumentException nucleotides contains invalid
		nucleotide characters (that is, other than a, g, c, or t.
	 */
	public Strand(String strandName, String nucleotides) {
		// ensure nucleotides will fit in Strand
		if (nucleotides.length() > MAX_NUCLEOTIDES) {
			throw new IllegalStateException("DNA Strand too long!");
		}
	
		// ensure contents of string are valid nucleotides
		nucleotides.toLowerCase();
		for (char c : nucleotides.toCharArray()) {
			if (!LEGAL_NUCLEOTIDES.contains(Character.toString(c))) {
				throw new IllegalArgumentException("'" +
					Character.toString(c) + "' is not a valid nucleotide.");
			}
		}
		
		this.strandName = strandName;
		this.nucleotides = nucleotides;
	}
	
	/** Obtain Strand's name.
	 * @return A String, strandName.
	 */
	public String getStrandName() {
		return strandName;
	}
	
	/** Obtain nucleotides as a string.
	 * @return A String, nucleotides.
	 */
	public String getNucleotides() {
		return nucleotides;
	}
	
	/** Create a String representation of the Strand for terminal output.
	 * @return a String consisting of strandName: nucleotides
	 */
	public String toString() {
		return strandName + ": " + nucleotides;
	}
	
	/** Determine whether regions of two nucleotide strings match.
	 * @return A boolean indicating that the regions matched. 
	 * @param other The second Strand.
	 * @param threshold The length of the match substring.
	 * @param toffset The index at which to start matching in the first
	 * Strand's nucleotide array.
	 * @param ooffset The index at which to start matching in the second
	 * Strand's nucleotide array.
	 */
	public boolean matchRegion(int toffset, Strand other, int ooffset,
		 int threshold) {
		return this.nucleotides.regionMatches(toffset, other, ooffset,
			threshold);
	}
	
	
	/** Obtain length of Strand's nucleotide String.
	 * @return An int, the length of the Strand's nucleotide String.
	 */
	public int length() {
		return this.nucleotides.length();
	}
	
	/** Splice two Strands together based on nucleotide substring positions.
	 * @param other The second of the two Strands to be spliced.
	 * @param toffset The position of the target nucleotide substring in
	 * the calling object.
	 * @param ooffset The position of the target nucleotide substring
	 * in the argument passed to other.
	 * @param threshold An int that describes the length of the target
	 * nucleotide substring.
	 * @return A new Strand whose name and nucleotide fields are a
	 * combination of the first and second Strand's name and nucleotide
	 * fields.
	 */
	public Strand splice(Strand other, int toffset, int ooffset,
		int threshold) {
		
		
		
	}		
}
