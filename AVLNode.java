/* Project3_AVLNodeMain.java
 *
 * CSc 345 Spring 16 - Project 3
 *
 * Author: Benlong Huang
 */

import java.util.ArrayList;

//First, construct the tree and the private that we will always be used in the code below.
public class AVLNode {
	private ArrayList<String> list = new ArrayList<String>();
	private AVLNode left;
	private AVLNode right;
	private String data;
	private String result3;

	public AVLNode(String dataRef) {
		data = dataRef;
		left = null;
		right = null;
	}

	private AVLNode root;

	public AVLNode() {
		root = null;
	}

	public boolean contains(String search) {
		AVLNode n = root;
		while (n != null) {
			if (search.compareTo(n.data) == 0)
				return true;
			else if (search.compareTo(n.data) > 0)
				n = n.right;
			else
				n = n.left;
		}
		return false;
	}

	// use the helper recursion to find whether it has next or not and the
	// condition that ref is greater, less and equal to dataString.
	public AVLNode insert(String val) {
		AVLNode temp = root;
		root = insertHelper(val, temp);
		return root;
	}

	private AVLNode insertHelper(String dataString, AVLNode ref) {
		if (ref == null) {
			ref = new AVLNode(dataString);
		} else if (dataString.compareTo(ref.data) < 0 && ref.left == null) {
			ref.left = new AVLNode(dataString);
			return ref;
		}

		else if (dataString.compareTo(ref.data) > 0 && ref.right == null) {
			ref.right = new AVLNode(dataString);
			return ref;
		}

		else {
			if (dataString.compareTo(ref.data) < 0 && ref.left != null)
				insertHelper(dataString, ref.left);
			if (dataString.compareTo(ref.data) > 0 && ref.right != null)
				insertHelper(dataString, ref.right);
		}
		return ref;
	}

	// just get the content.
	public String getKey() {
		return data;
	}

	// use helper recursion to calculate the height, compare left and right that
	// which side is larger, always go to left can find current maximum height.
	// The recursion goes one time then the height add 1.
	public int getHeight() {
		AVLNode temp = this;
		return helper(temp);
	}

	private int helper(AVLNode ref) {
		if (ref == null)
			return -1;
		else
			return 1 + Math.max(helper(ref.left), helper(ref.right));
	}

	// use recursion to count every leaf. recursion goes one time, then like 1 +
	// left + right, if there is no leaf, then add 0.
	public int getCount() {

		return helper2(root);
	}

	private int helper2(AVLNode ref) {
		if (ref == null)
			return 0;
		else
			return 1 + helper2(ref.left) + helper2(ref.right);
	}

	// need to create an array list result, use the recursion to put all the
	// left first in the list, then
	// add right. then return the list. just like in-order.
	public String[] toArray_inOrder() {
		list.clear();
		String[] result = new String[getCount()];
		ArrayHelper(root);
		for (int i = 0; i < getCount(); i++) {
			result[i] = list.get(i);
		}

		return result;
	}

	private void ArrayHelper(AVLNode ref) {
		if (ref != null) {
			ArrayHelper(ref.left);
			list.add(ref.data);
			ArrayHelper(ref.right);
		}
	}

	// same as last one, this one is pre-order, just need to change the position
	// of the instructions.
	public String[] toArray_preOrder() {
		list.clear();
		String[] result2 = new String[getCount()];
		OrderHelper(root);
		for (int i = 0; i < getCount(); i++) {
			result2[i] = list.get(i);
		}

		return result2;
	}

	private void OrderHelper(AVLNode ref) {
		if (ref != null) {
			list.add(ref.data);
			OrderHelper(ref.left);
			OrderHelper(ref.right);
		}
	}

	// If element equals an element in this OrderedSet, remove it
	public void delete(String val) {
		this.root = delete(this.root, val);
	}

	public AVLNode delete(AVLNode node, String val) {
		if (node.data.compareTo(val) < 0) {
			node.right = delete(node.right, val);
		} else if (node.data.compareTo(val) > 0) {
			node.left = delete(node.left, val);
		} else {
			if (node.right == null) {
				return node.left;
			}
			if (node.left == null) {
				return node.right;
			}
			AVLNode temp = node;
			node = min(temp.right);
			node.right = deleteMin(temp.right);
			node.left = temp.left;
		}

		return node;
	}

	private AVLNode min(AVLNode node) {
		if (node.left == null) {
			return node;
		} else {
			return min(node.left);
		}
	}

	private AVLNode deleteMin(AVLNode node) {
		if (node.left == null) {
			return node.right;
		}
		node.left = deleteMin(node.left);
		return node;
	}

	// use the recursion to use the vertax + random number instead of the
	// content of key, check each side has next or not. add the string, change
	// to another space and label that required. digraph just appear once, so
	// write it in the public not in recursion.
	public String toDotFile() {
		if(root == null)
			return "digraph{}";
		String represent = "vertex" + this.hashCode();
		result3 = "";
		result3 = result3 + "digraph" + "\n" + "{" + "\n";
		result3 = result3 + represent + " [label=\"" + root.getKey() + "\nh="
				+ root.getHeight() + "\"]" + ";" + "\n";
		FileHelper(root, represent);
		result3 = result3 + "}";
		return result3;
	}

	private void FileHelper(AVLNode ref, String represent) {
		if (ref == null)
			return;
		String representLeft = "";
		String representRight = "";
		if (ref.left != null) {
			representLeft = representLeft + "vertex" + ref.left.hashCode();
			result3 = result3 + representLeft + " [label=\""
					+ ref.left.getKey() + "\nh=" + ref.left.getHeight() + "\"]"
					+ ";" + "\n";
			result3 = result3 + represent + " -> " + representLeft
					+ "[label=\"left\"];" + "\n";
		}

		if (ref.right != null) {
			representRight = representRight + "vertex" + ref.right.hashCode();
			result3 = result3 + representRight + " [label=\""
					+ ref.right.getKey() + "\nh=" + ref.right.getHeight()
					+ "\"]" + ";" + "\n";
			result3 = result3 + represent + " -> " + representRight
					+ "[label=\"right\"];" + "\n";
		}

		FileHelper(ref.left, representLeft);
		FileHelper(ref.right, representRight);
	}

}
