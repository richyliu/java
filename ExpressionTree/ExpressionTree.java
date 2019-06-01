import java.util.ArrayList;
import java.util.List;

/**
 *	ExpressionTree - Creates an expression tree from an expression given
 *				in infix notation.
 *
 *	@author Richard Liu
 *	@since  May 23, 2019
 */
public class ExpressionTree {

	private String expr;				// expression

	private TreeNode<String> root;		// the root node of the expression tree

	private ExprUtils utils;			// utilities to tokenize expression

	private final int PRINT_SPACES = 3;	// number of spaces between tree level
										// used by printTree()

	// constructor
	public ExpressionTree() {
		utils = new ExprUtils();
		root = null;
		expr = "";
	}

	public static void main(String[] args) {
		ExpressionTree et = new ExpressionTree();
		et.run();
	}

	public void run() {
		System.out.println("\nWelcome to ExpressionTree!!!");
		treeMakerInterface();
		System.out.println("\nThanks for using ExpressionTree! Goodbye.\n");
	}

	/**
	 *	The user interface for the Expression Tree
	 */
	public void treeMakerInterface() {
		String input = "";

		while(!input.equals("q")) {
			System.out.println();
			printMenu();
			input = Prompt.getString("");
			System.out.println();

			switch(input) {
				case "i":
					expr = "";
					boolean valid = false;
					while (!valid) {
						expr = Prompt.getString("expression");
						List<String> tokens = null;
						// try to parse the tokens
						try {
							// can be parsed, then check if its valid
							tokens = utils.tokenizeExpression(expr);
							valid = utils.hasValidExpression(tokens);
						} catch (Exception e) {}
						// warn user if its invalid
						if (!valid)
							System.out.println("Invalid expression\n");
					}
					// valid expression, build the tree
					root = buildTree();
					break;

				case "pre":
					System.out.println("Prefix order");
					utils.printPreorder(root);
					break;

				case "in":
					System.out.println("Infix order");
					utils.printInorder(root);
					break;

				case "post":
					System.out.println("Postfix order");
					utils.printPostorder(root);
					break;

				case "e":
					System.out.println("Answer: " + evaluateExpression());
					break;

				case "p":
					System.out.println("Print tree");
					printTree();
					break;
			}
		}
	}

	/**	Print help */
	public void printMenu() {
		System.out.println("\nCurrent expression: " + expr);
		System.out.println("\nChoose:");
		System.out.println("  (i) input new expression");
		System.out.println("  (pre) print prefix notation");
		System.out.println("  (in) print infix notation");
		System.out.println("  (post) print postfix notation");
		System.out.println("  (e) evaluate expression");
		System.out.println("  (p) print tree");
		System.out.println("  (q) quit");
	}

	/**	Builds a Binary Expression Tree from tokens.
	 *	@return		root of expression tree
	 *
	 *	Algorithm: (describe here)
	 */
	public TreeNode<String> buildTree() {
	 	// create TreeNode stack
	 	ArrayStack<TreeNode<String>> treeStack = new ArrayStack<TreeNode<String>>();
	 	// tokenize expression
	 	List<String> tokens = utils.toPostfix(expr);

	 	// loop over token
	 	for (String token : tokens) {
			TreeNode<String> node = new TreeNode<String>(token);
			// pop nodes off of treestack and onto operator
			if (utils.isOperator(token)) {
				node.setRight(treeStack.pop());
				node.setLeft(treeStack.pop());
			}
			// push node onto treestack (for operator and operand)
			treeStack.push(node);
		}

	 	// root is the last node
		return treeStack.pop();
	}

	/**
	 *	Evaluate the expression in the ExpressionTree
	 *	@return		the evaluated answer
	 */
	public double evaluateExpression() {
		if (root == null) return 0;
		return evaluateNode(root);
	}

	/**
	 * Evaluate a treeNode recursively.
	 * @param node	TreeNode to evaluate from
	 * @return		Computed result number of the tree node
	 */
	public double evaluateNode(TreeNode<String> node) {
		// node could contain an operator or a number
		try {
			// formatted correctly, then just return the number
			return Double.parseDouble(node.getValue());
		// not a number, must be an operator
		} catch (NumberFormatException e) {
			// evaluate then left and right nodes and return the result
			return doOp(
					node.getValue(),
					evaluateNode(node.getLeft()),
					evaluateNode(node.getRight())
					);
		}
	}

	/**
	 * Computes an operation: a op b.
	 * @param op	Operator to do operation
	 * @param a		Left hand number
	 * @param b		Right hand number
	 * @return		Result of operation
	 */
	public double doOp(String op, double a, double b) {
		switch (op) {
			case "+": return a + b;
			case "-": return a - b;
			case "*": return a * b;
			case "/": return a / b;
			case "%": return a % b;
			case "^": return Math.pow(a, b);
		}

		System.err.println("Received invalid operation: " + op);
		return 0;
	}

	/**
	 *	Print expression tree
	 *	@param root		root node of binary tree
	 *
	 *	Prints in vertical order, top of output is right-side of tree,
	 *			bottom is left side of tree,
	 *			left side of output is root, right side is deepest leaf
	 *	Example tree (expression "5 + 2 * 3""):
	 *			  +
	 *			/
	 *		  /
	 *		5			*
	 *				  /
	 *				2		3
	 *
	 *	would be output as:
	 *
	 *				3
	 *			*
	 *				2
	 *		+
	 *			5
	 */
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
	public void printLevel(TreeNode<String> node, int level) {
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