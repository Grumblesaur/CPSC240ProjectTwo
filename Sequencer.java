// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// DNA Sequencer Project / Main Program

/* Utilities */
import java.util.Scanner;
import java.util.ArrayList;

/* Exceptions */
import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

public class Sequencer {
	public static void main(String[] args) {
		/* Initialize system resources */
		String filename = "";
		Scanner input = new Scanner(System.in);
		int threshold = 0;
		StrandReader sr = null;
		ArrayList<Strand> sequences = null;
		
		/* Gather user input, with command line shortcut if arguments were
			entered correctly. */
		if (args.length == 2) {
			try {
				filename = args[1];
				threshold = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.err.println("Usage: java Sequencer file threshold");
				System.exit(1);
			}
		} else {
			System.out.print("Enter match threshold (min = 1): ");
			threshold = input.nextInt();
			System.out.print("Enter DNA file list: ");
			filename = input.next();
		}
		
		/* Refuse to match zero or negative nucleotides. */
		if (threshold < 1) {
			System.err.println("Must set matching threshold of at least " +
				"one character!");
			System.exit(1);
		}
		
		/* Retrieve Strand data */
		sr = new StrandReader(filename);
		try {
			sequences = sr.parse();
		} catch(IllegalArgumentException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		} catch(IllegalStateException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		/* Begin matching logic with largest strand since that gives us the
			most opportunities to match initially.
		*/
		Strand target = null;
		int length = 0;
		for (Strand s : sequences) {
			if (s.length() > length) {
				length = s.length();
				target = s;
			}
		}
		
		/* Initialize ArrayList to track confirmed sequence matches. */
		ArrayList<Strand> matched = new ArrayList<Strand>();
		matched.add(target);
		sequences.remove(target);
		
		/* Initialize variables to control Strand confirmation and loop
			iteration.
		*/
		boolean removep = false;
		int iterationsLeft = sequences.size();
		Strand temp = null;
		
		/* Loop for every item in the list of sequences */
		while (iterationsLeft >= 0) {
			/* Attempt to match and splice each Strand with the current
				target Strand.
			*/
			for (Strand s : sequences) {
				/* Set maximum match values for different splicing
					scenarios.
				*/
				int prependMax = s.maxMatchSize(target, threshold);
				int appendMax = target.maxMatchSize(s, threshold);
				
				/* Set switches for else-if blocks */
				boolean prepend = prependMax >= threshold &&
					prependMax > appendMax;
				boolean append = appendMax >= threshold &&
					appendMax > prependMax;
				
				removep = false;
				if (target.contains(s) || target.equals(s)) {
					removep = true;
					target.setStrandName(target.getStrandName() +
						s.getStrandName());
				} else if (prepend) {
					/* s is spliced onto the beginning of target */
					target = s.splice(target, s.length() - prependMax,
						0, prependMax);
					removep = true;
				} else if (append) {
					/* s is spliced onto the end of target */
					target = target.splice(s, target.length() - appendMax,
						0, appendMax);
						removep = true;
				} else {
					/* s cannot yet be added to target */
					System.out.println("Strand " + s.toString() + " not " +
						"yet ready to be spliced.");
					removep = false;
				}
				
				System.out.println("...");
				
				/* Add to our matched list and break out of inner loop. */
				if (removep) {
					matched.add(s);
					temp = s;
					break;
				}
			}
			
			/* Removing from a collection during iteration causes runtime
				exceptions. Perform removal in non-iterating outer loop.
			*/
			if (removep) {
				sequences.remove(temp);
			}
			iterationsLeft--;
		}
		
		System.out.println("...");
		
		/* Check for success or failure of process. */
		if (!sequences.isEmpty()) {
			System.err.println("Error! Could not complete master strand!");
			System.err.println("Sequenced: " + target.toString());
			System.err.println("Remaining: ");
			for (Strand r : sequences) {
				System.err.println("\t" + r.toString());
			}
		} else {
			System.out.println("Strands fully sequenced. Result:\n" +
				target.toString());
		}
			
	}
	// Used to ease debugging; DEBUG() calls removed from final program
	static void DEBUG(String message) {
		System.out.println(message);
	}
}
