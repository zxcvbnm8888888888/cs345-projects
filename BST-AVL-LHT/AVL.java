// Author: Tanner Prynn
// 08/16/2012
// CSC345 Program 2

public class AVL extends BST {
	
	public AVL() {
		root = null;
	}

	public void insert(String key) {
		root = insert(key, root);
	}
	
	private Node insert(String key, Node top) {
		if(top == null)
			top = new Node(key);
		else {
			int compare = key.compareTo(top.key);
			
			if(compare < 0) { 
				top.left = insert(key, top.left); // Move to the left

				if(balanceFactor(top) > 1) { // The tree is now left-heavy
					if(balanceFactor(top.left) == 1) {
						//single rotate right with top as root
						top = rotateRight(top);
					}
					else {
						//left rotate with top.left as root
						top.left = rotateLeft(top.left);
						//right rotate with top as root
						top = rotateRight(top);
					}
				}
			}
			
			else if (compare > 0) {
				top.right = insert(key, top.right); // Move to the right
				
				if(balanceFactor(top) < -1) { // The tree is now right-heavy
					if(balanceFactor(top.right) == -1) {
						//single rotate left with top as root
						top = rotateLeft(top);
					}
					else {
						//right rotate with top.right as root
						top.right = rotateRight(top.right);
						//left rotate with top as root
						top = rotateLeft(top);
					}
				}
			}
			
			else {
				top.frequency++; // The key already exists, increment frequency
			}
		}
		
		top.height = height(top);
		return top;
	}

	/**Rotates a node left, placing its right child above it
	 * T = top, C = child, GC = grandchild
	 * 		T               C
	 * 	     \             / \
	 *        C           T   GC
	 *         \
	 *          GC
	 */
	private Node rotateLeft(Node top) {
		Node temp = top;
		top = top.right;
		temp.right = top.left;
		top.left = temp;
		temp.height = height(temp);
		top.height = height(top);
		return top;
	}
	
	/**Rotates a node right, placing its left child above it
	 * T = top, C = child, GC = grandchild
	 * 		T        C
	 * 	   /        / \
	 *    C        T  GC 
	 *   /      
	 *  GC       
	 */
	private Node rotateRight(Node top) {
		Node temp = top;
		top = top.left;
		temp.left = top.right;
		top.right = temp;
		temp.height = height(temp);
		top.height = height(top);
		return top;
	}

	private int height(Node n) {	
		if(n == null)
			return 0;
		int leftHeight = 0, rightHeight = 0;
		if(n.left != null)
			leftHeight = n.left.height;
		if(n.right != null)
			rightHeight = n.right.height;
		return 1+Math.max(leftHeight,rightHeight);
	}
	
	private int balanceFactor(Node n) {
		int result = 0;
		if(n.left != null)
			result = n.left.height;
		if(n.right != null)
			result -= n.right.height;
		return result;
	}
	
}
