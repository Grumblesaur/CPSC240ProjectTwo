// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// DNA Sequencer Project / Main Program

import java.util.Scanner;
import java.util.ArrayList;

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
				filename = args[0];
				threshold = Integer.parseInt(args[1]);
			} catch (Exception e) {
				System.err.println("Usage: java Sequencer file threshold");
				System.exit(1);
			}
		} else {
			System.out.print("Enter DNA file list: ");
			filename = input.nextLine();
			System.out.print("Enter match threshold (min = 1): ");
			threshold = input.nextInt();
		}
		
		/* Refuse to match zero or negative nucleotides. */
		if (threshold < 1) {
			System.err.println("Must set matching threshold of at least " +
				"one character!");
			System.exit(1);
		}
		
		/* Retrieve strand data */
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
		
		//TEST:
		for (Strand s : sequences) {
			System.out.println(s.toString());
		}
		
		/* Begin matching logic with largest strand */
		Strand target = null;
		int length = 0;
		for (Strand s : sequences) {
			if (s.length() > length) {
				length = s.length();
				target = s;
			}
		}
		
		ArrayList<Strand> matched = new ArrayList<Strand>();
		matched.add(target);
		sequences.remove(target);
		boolean failure = false, removep = false;
		int iterationsLeft = sequences.size() + 1;
		Strand temp = null;
		while (iterationsLeft >= 0) {
			for (Strand s : sequences) {
				removep = false;
				/* Don't add yourself. */
				if (s == target) {
					continue;
				}
				/* Match-Splice Logic */
				if (target.equals(s)) {
					/* identical nucleotides */
					removep = true;
				} else if (target.matchRegion(target.length() - threshold,
					s, 0, threshold)) {
					/* target + s */
					target = target.splice(s, target.length() - threshold,
					0, threshold);
					removep = true;
				} else if (target.matchRegion(0, s, s.length() - threshold,
					threshold)) {
					/* s + target */
					target = s.splice(target, s.length() - threshold, 0,
					threshold);
					removep = true;
				} else if (target.contains(s)) {
					/* s in target */
					removep = true;
				} else {
					System.err.println("Strand " + s.toString() +
					" not yet matched!");
				}
				
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
		
		System.out.println("");
		
		if (!sequences.isEmpty()) {
			System.err.println("Error! Could not complete master strand!");
			System.err.println("Sequenced: " + target.toString());
			System.err.println("Remaining: ");
			for (Strand r : sequences) {
				System.err.println("\t" + r.toString());
			}
		} else {
			System.err.println("Strands fully sequenced. Result: " +
				target.getNucleotides());
		}
			
	}
	// Used to ease debugging; DEBUG() calls removed from final program
	static void DEBUG(String message) {
		System.out.println(message);
	}
}
