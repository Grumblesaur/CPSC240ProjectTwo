// James Murphy
// CPSC 240 / Object-Oriented Analysis & Design
// DNA Sequencer Project / Main Program

import java.util.Scanner;
import java.util.ArrayList;

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
		sequences = sr.parse();
		
		//TEST:
		for (Strand s : sequences) {
			System.out.println(s.toString());
		}
	
		/* Match logic */
		// for convenience, try to start with the largest Strand
		Strand target = null;
		int length = 0;
		for (Strand s : sequences) {
			if (s.length() > length) {
				length = s.length;
				target = s;
			}
		}
		
		List<Strand> matched = new List<Strand>();
		matched.append(target);
		boolean failure = false;
		for (Strand s : sequences) {
			if (s == target) {
				continue;
			}
			
			if (target.matchRegion(target.length() - threshold, s, 0,
				threshold)) {
				target = target.splice(s, target.length() - threshold, 0,
					threshold);
				matched.append(s);
			} else if (target.matchRegion(0, s, s.length() - threshold,
				threshold)) {
				target = s.splice(target, s.length - threshold, 0,
					threshold);
				matched.append(s);
			} else {
				System.err.println("Strand " + s.toString() + " could not"
					+ " be matched!");
				failure = true;
				break;
			}
		}
		
		if (failure) {
			System.err.println("Sequenced: " + target.toString());
			System.err.println("Remaining: ");
			for (Strand r : matched) {
				System.err.println("\t" + r.toString());
			}
		} else {
			System.err.println("Strands fully sequenced. Result: " +
				target.getNucleotides());
		}
			
	}
}
