/* Project3_AVLNodeMain.java
 *
 * CSc 345 Spring 16 - Project 3
 *
 * Author: Benlong Huang
 * 
 *
 * This class is a AVLNode Tree to draw the tree depends on the input that we scan.
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class AVLNodeMain {

	public static void main(String[] args) throws IOException {
		// error condition that the input's length is not equal to 1
		if (args.length != 1) {
			System.out.println("Error");
			return;
		}
		Scanner input;
		String inputname = args[0];
		// an other error condition, which is no input.
		try {
			input = new Scanner(new File(inputname));
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			return;
		}
		// create a new AVLNode that we can use it in the this Main.
		AVLNode node = new AVLNode(null);
		// this while loop is use to check every input. the input.next() has
		// five types s(search), i(insert), d(delete), p(in-order), P(pre-order)
		while (input.hasNext()) {
			String temp = input.next();
			if (temp.compareTo("s") == 0) {
				String element = input.next();
				boolean test = node.contains(element);
				if (test == true) {
					System.out.println("'" + element + "' found.");
				} else {
					System.out.println("'" + element + "' NOT found.");
				}
			}
			if (temp.compareTo("i") == 0) {
				String element = input.next();
				boolean test = node.contains(element);
				if (test == true) {
					System.out
							.println("'"
									+ element
									+ "' could not be inserted, because it was already in the AVL tree.");
				} else {
					node.insert(element);
					System.out.println("'" + element + "' inserted.");
					dotfile(node);
				}
			}
			if (temp.compareTo("d") == 0) {
				String element = input.next();
				boolean test = node.contains(element);
				if (test == true) {
					System.out.println("'" + element + "' deleted.");
					node.delete(element);
					dotfile(node);
				} else {
					System.out
							.println("'"
									+ element
									+ "' could not be deleted, because it was not in the AVL tree.");
				}
			}
			if (temp.compareTo("p") == 0) {
				String[] arr = node.toArray_inOrder();
				System.out.print(node.getCount() + " elements: ");
				for (int i = 0; i < arr.length; i++) {
					System.out.print(" " + arr[i]);
				}
				System.out.println();
			}
			if (temp.compareTo("P") == 0) {
				String[] arr = node.toArray_preOrder();
				System.out.print(node.getCount() + " elements: ");
				for (int i = 0; i < arr.length; i++) {
					System.out.print(" " + arr[i]);
				}
				System.out.println();
			}
		}
	}

	// this step is use to create a new dot file. the deletion and insertion
	// part can call this method to create a new dot file after it works.
	private static int number = 0;

	public static void dotfile(AVLNode node) throws IOException {
		String store = node.toDotFile();
		String name = String.format("AVL.%04d.dot", number);
		Writer write = new FileWriter(name);
		write.write(store);
		write.close();
		number++;
	}
}
