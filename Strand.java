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
	
	/** Determine index of matching point in first nucleotide String for a
	 * substring in second nucleotide String.
	 * @return An array of ints, whose first element is the index of the
	 * matching substring in the first string, and whose second element is
	 * the index of the matching substring in the second string.
	 * @param other The second Strand.
	 * @param threshold The length of the match substring.
	 */
	public int[] matchIndex(Strand other, int threshold) {
		String sub = "";
		int[] temp = {-1, -1};
		for (int i = 0; i < other.nucleotides.length() - threshold; i++) {
			sub = other.nucleotides.substring(i, i + threshold);
			if (this.nucleotides.contains(sub)) {
				temp[0] = this.nucleotides.indexOf(sub);
				temp[1] = i;
			}
		}
		return temp;
	}
	
	/** Obtain length of Strand's nucleotide String.
	 * @return An int, the length of the Strand's nucleotide String.
	 */
	public int length() {
		return this.nucleotides.length();
	}
	
	/** Splice two Strands together based on nucleotide substring positions.
	 * @param other The second of the two Strands to be spliced.
	 * @param firstOffset The position of the target nucleotide substring in
	 * the calling object.
	 * @param secondOffset The position of the target nucleotide substring
	 * in the argument passed to other.
	 * @param threshold An int that describes the length of the target
	 * nucleotide substring.
	 * @return A new Strand whose name and nucleotide fields are a
	 * combination of the first and second Strand's name and nucleotide
	 * fields.
	 */
	public Strand splice(Strand other, int firstOffset, int secondOffset,
		int threshold) {
		boolean spliceError = false;
		
		/* Do nucleotide strings neatly overlap? */
		if (firstOffset > secondOffset) {
			/* first + second */
			if ((this.length() - firstOffset) != threshold ||
				(0 + threshold) != secondOffset) {
				spliceError = true;
			}
		} else if (secondOffset > firstOffset) {
			/* second + first */
			if ((other.length() - secondOffset) != threshold ||
				(0 + threshold) != firstOffset) {
				spliceError = true;
			}
		} else if (secondOffset == firstOffset) {
			/* first & second have matching heads, tails, or other invalid
				splice condition */
			spliceError = true;
		}
		
		if (spliceError) {
			throw new Error("Strands cannot be spliced!");
		}
				
		
		/* If second strand is included in first strand with no additional
			characters to attach to the nucleotide string, do nothing.
		*/
		String label = this.getStrandName() + other.getStrandName();
		String bases = "";
		String firstBases = this.getNucleotides();
		String secondBases = other.getNucleotides();
		if (threshold == other.length()) {
			return this;
		}
		
		/* Splice first + second */
		if (firstOffset > secondOffset) {
			firstBases = firstBases.replace(firstBases.substring(
				firstOffset, firstOffset + threshold), "");
			bases = firstBases + secondBases;
		} else {
			/* Spliace second + first */
			secondBases = secondBases.replace(secondBases.substring(
				secondOffset, secondOffset + threshold), "");
			bases = secondBases + firstBases;
		}
		
		return new Strand(label, bases);
		
		
	}
}
