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
	
		//TODO: matching algorithm
		//TEST:
		System.out.println("For threshold = " +
			Integer.toString(threshold));
		for (Strand s : sequences) {
			for (Strand z : sequences) {
				if (s == z) {
					System.out.println("s == z; skipped");
					continue;
				}
				int[] temp = s.matchIndex(z, threshold);
				if (temp[1] == -1) {
					System.out.println("s does not match with z");
				} else {
					System.out.println("s at " + Integer.toString(temp[0]) +
					" matches z at " + Integer.toString(temp[1]));
				}		
		}
		}
	}
}
