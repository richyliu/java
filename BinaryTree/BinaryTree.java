/**
 *	Binary Tree of Comparable values.
 *	The tree only has unique values. It does not add duplicate values.
 *
 *	@author
 *	@since
 */
public class BinaryTree<E extends Comparable<E>> {

	private TreeNode<E> root;		// the root of the tree

	private final int PRINT_SPACES = 3;	// print spaces between tree levels
										// used by printTree()

	/**	constructor for BinaryTree */
	public BinaryTree() { }

	/**	Field accessors and modifiers */

	/**	Add a node to the tree
	 *	@param value		the value to put into the tree
	 */
	public void add(E value) {
		//addIterative(value);
		addRecursive(value);
	}

	/** Adds the node by looping through the levels of the tree iteratively */
	public void addIterative(E value) {
		// create new node to insert into tree
		TreeNode<E> newNode = new TreeNode<E>(value);
		// set root if it is null
		if (root == null) {
			root = newNode;
			return;
		}

		// current node to compare
		TreeNode<E> cur = root;
		// current compare result
		int comp = cur.getValue().compareTo(value);

		while (cur != null) {
			// node is greater than new value, choose left branch
			if (comp > 0) {
				// insert node if no node
				if (cur.getLeft() == null) {
					cur.setLeft(newNode);
					return;
				} else
					// otherwise advance pointer
					cur = cur.getLeft();
			// choose right branch
			} else {
				// insert node if no node
				if (cur.getRight() == null) {
					cur.setRight(newNode);
					return;
				} else
					// otherwise advance pointer
					cur = cur.getRight();
			}
			// compare the new nodes
			comp = cur.getValue().compareTo(value);
		}
	}

	public void addRecursive(E value) {
		// create new node to insert into tree
		TreeNode<E> newNode = new TreeNode<E>(value);
		// set root if it is null
		if (root == null) {
			root = newNode;
			return;
		}

		addRecurse(value, root);
	}

	public void addRecurse(E value, TreeNode<E> node) {
		// node is greater, use left branch
		if (node.getValue().compareTo(value) > 0) {
			// set new node if no left branch
			if (node.getLeft() == null)
				node.setLeft(new TreeNode<E>(value));
			else
				addRecurse(value, node.getLeft());
		// node is lesser, use right branch
		} else {
			// set new node if no left branch
			if (node.getRight() == null)
				node.setRight(new TreeNode<E>(value));
			else
				addRecurse(value, node.getRight());
		}
	}

	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder() {
		printInorderRecurse(root);
	}

	public void printInorderRecurse(TreeNode<E> node) {
		if (node == null)
			return;
		printInorderRecurse(node.getLeft());
		System.out.println(node.getValue());
		printInorderRecurse(node.getRight());
	}

	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder() {
		printPreorderRecurse(root);
	}

	public void printPreorderRecurse(TreeNode<E> node) {
		if (node == null)
			return;
		System.out.println(node.getValue());
		printPreorderRecurse(node.getLeft());
		printPreorderRecurse(node.getRight());
	}

	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder() {
		printPostorderRecurse(root);
	}

	public void printPostorderRecurse(TreeNode<E> node) {
		if (node == null)
			return;
		printPostorderRecurse(node.getLeft());
		printPostorderRecurse(node.getRight());
		System.out.println(node.getValue());
	}

	/**	Return a balanced version of this binary tree
	 *	@return		the balanced tree
	 */
	public BinaryTree<E> makeBalancedTree() {
		BinaryTree<E> balancedTree = new BinaryTree<E>();

		return balancedTree;
	}


	/*******************************************************************************/
	/********************************* Utilities ***********************************/
	/*******************************************************************************/
	/**
	 *	Print binary tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example Integer tree:
	 *			  11
	 *			/	 \
	 *		  /		   \
	 *		5			20
	 *				  /	  \
	 *				14	   32
	 *
	 *	would be output as:
	 *
	 *				 32
	 *			20
	 *				 14
	 *		11
	 *			5
	 ***********************************************************************/
	public void printTree() {
		printLevel(root, 0);
	}

	/**
	 *	Recursive node printing method
	 *	Prints reverse order: right subtree, node, left subtree
	 *	Prints the node spaced to the right by level number
	 *	@param node		root of subtree
	 *	@param level	level down from root (root level = 0)
	 */
	private void printLevel(TreeNode<E> node, int level) {
		if (node == null) return;
		// print right subtree
		printLevel(node.getRight(), level + 1);
		// print node: print spaces for level, then print value in node
		for (int a = 0; a < PRINT_SPACES * level; a++) System.out.print(" ");
		System.out.println(node.getValue());
		// print left subtree
		printLevel(node.getLeft(), level + 1);
	}


}
