// Author: Tanner Prynn
// 08/16/2012
// CSC345 Program 2

public class BST { // Binary Search Tree implementation
	
	protected Node root;
	
	public BST() {
		root = null;
	}
	
	protected class Node {
		protected String key;
		protected int frequency;
		protected int accessCount;
		protected int height;
		protected Node left;
		protected Node right;
		
		protected Node(String key) {
			this.key = key;
			frequency = 1;
			accessCount = 0;
			height = 0;
			left = null;
			right = null;
		}
	}

	public void insert(String key) {
		if(root == null) {
			root = new Node(key);
			return;
		}
		
		Node parent = null;
		Node current = root;
		int compare = 0;
		while(current != null) {
			compare = key.compareTo(current.key);
			if(compare == 0) {
				current.frequency++;
				break;
			}
			else if(compare < 0) {
				parent = current;
				current = current.left;
			}
			else {
				parent = current;
				current = current.right;
			}
		}
		
		if(current == null) {
			if(compare < 0)
				parent.left = new Node(key);
			else parent.right = new Node(key);
		}
			
	}

	public boolean find(String key) {
		Node current = root;
		
		while(current != null) {
			current.accessCount++;
			int compare = key.compareTo(current.key);
			if(compare == 0)
				return true;
			else if(compare < 0)
				current = current.left;
			else 
				current = current.right;
		}
		
		return false;
	}

	public int size() {
		return size(root);
	}
	
	private int size(Node node) {
		if(node == null)
			return 0;
		else
			return 1+size(node.left)+size(node.right);
	}

	public int sumFreq() {
		return sumFreq(root);
	}
	
	private int sumFreq(Node node) {
		if(node == null)
			return 0;
		else
			return node.frequency+sumFreq(node.left)+sumFreq(node.right);
	}

	public int sumProbes() {
		return sumProbes(root);
	}
	
	private int sumProbes(Node node) {
		if(node == null)
			return 0;
		else
			return node.accessCount+sumProbes(node.left)+sumProbes(node.right);
	}

	public void resetCounters() {
		resetCounters(root);
	}
	
	private void resetCounters(Node node) {
		if(node == null)
			return;
		resetCounters(node.left);
		resetCounters(node.right);
		node.frequency = 1;
		node.accessCount = 0;
	}

	public void print() {
		print(root);
	}
	
	private void print(Node node) {
		if(node == null)
			return;
		print(node.left);
		System.out.println("[" + node.key + ":" + node.frequency + ":" + node.accessCount + "]");
		print(node.right);
	}
	
	public void custPrint() {
		custPrint(root);
	}
	private void custPrint(Node node) {
		if(node == null)
			return;
		custPrint(node.left);
		System.out.println("[" + node.key + ":" + node.height + ":" + node.frequency + ":" + node.accessCount + "]");
		custPrint(node.right);
	}

}
