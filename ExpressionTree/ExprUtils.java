import java.util.ArrayList;	// tokenizeExpression uses this
import java.util.List;		// tokenizeExpression uses this

/**
 *	Arithmetic expression utilities
 *	o Methods to convert a String infix expression to list of tokens
 *	  in postfix order. (you write)
 *	o Methods to tokenize an arithmetic expression. (Already written)
 *	o Methods to validate the List of expression tokens (Extra credit)
 *
 *	@author	Mr Greenstein - tokenizeExpression()
 *	@author	Richard Liu
 *	@since	May 21, 2019
 *
 */
public class ExprUtils {

	private ArrayStack<String> operatorStack;	// stack for operators (toPostfix)

	public ExprUtils() {
		operatorStack = new ArrayStack<String>();
	}


	/*************************************************************************/
	/********************** Tree printing methods ****************************/
	/*************************************************************************/

	/**
	 *	Print Binary Tree Inorder
	 */
	public void printInorder(TreeNode<String> node) {
		// do nothing if end of branch is reached
		if (node == null) return;
		printInorder(node.getLeft());
		System.out.print(node.getValue() + " ");
		printInorder(node.getRight());
	}

	/**
	 *	Print Binary Tree Preorder
	 */
	public void printPreorder(TreeNode<String> node) {
		// do nothing if end of branch is reached
		if (node == null) return;
		System.out.print(node.getValue() + " ");
		printPreorder(node.getLeft());
		printPreorder(node.getRight());
	}

	/**
	 *	Print Binary Tree Postorder
	 */
	public void printPostorder(TreeNode<String> node) {
		// do nothing if end of branch is reached
		if (node == null) return;
		printPostorder(node.getLeft());
		printPostorder(node.getRight());
		System.out.print(node.getValue() + " ");
	}



	/*************************************************************************/
	/******************** Infix to Postfix methods ***************************/
	/*************************************************************************/
	/**	Convert a String from infix notation to a token list in postfix order
	 *	@param expr		the String expression in infix notation
	 *	@return			an ArrayList of the expression tokens in postfix order
	 *
	 *	Algorithm:	Places numbers directly in result, puts operators after the
	 * 				operands have been placed in their order of precedence.
	 * 				Maintains a stack of operators to accomplish this.
	 */
	public List<String> toPostfix(String expr) {
		// Create list to hold the postfix notation
		List<String> result = new ArrayList<String>();
		// Create operator stack
		ArrayStack<String> operatorStack = new ArrayStack<String>();
		// List of tokens of the expression
		List<String> tokens = tokenizeExpression(expr);

		// loop over expression tokens
		for (String token : tokens) {
			// if current token is operator
			if (isOperator(token)) {
				// currently no operator on the stack, must push current one
				// or if left paren, push onto operator stack to be used as a marker
				// or if both ops are exponents, push it and do it later
				if (operatorStack.isEmpty() || token.equals("(")) {
					operatorStack.push(token);
				// once right paren is reached, ...
				} else if (token.equals(")")) {
					// ... push all the operations until the left paren
					while (!operatorStack.peek().equals("("))
						result.add(operatorStack.pop());
					// then remove the left paren
					operatorStack.pop();
				// if left op has precedence, push it first ...
				} else if (hasPrecedence(token, operatorStack.peek())) {
					// ... along with all operators that have precedence ...
					while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek()))
						result.add(operatorStack.pop());
					// ... then push the current op onto the operator stack
					operatorStack.push(token);
				// otherwise just push the token onto the operator stack
				} else {
					operatorStack.push(token);
				}
			} else {
				// add to result if it is a number
				result.add(token);
			}
		}
		// if there are still operators that need to be done, do them
		while (!operatorStack.isEmpty())
			result.add(operatorStack.pop());

		return result;
	}

	/**	Helper method for toPostfix()
	 *	Precedence of operators
	 *	@param op1		operator 1
	 *	@param op2		operator 2
	 *	@return			true if op2 has higher or same precedence as op1;
	 *					false otherwise
	 *		if op1 is exponent, then false
	 *		if op2 is either left or right parenthesis, then false
	 *		if op1 is multiplication or division or modulus and
	 *				op2 is addition or subtraction, then false
	 *		otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%"))
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}


	/*************************************************************************/
	/****************** Expression Tokenizer methods *************************/
	/*************************************************************************/
	/**
	 *	Tokenize a mathematical expression. This can handle the following
	 *	situations:
	 *	1. The unary operator "-" or "+".
	 *	2. An integer or decimal number. The decimal number can start with
	 *		a digit or a decimal (".").
	 *	3. An identifier for a variable which contains only letters.
	 *	4. Parentheses "(" and ")".
	 *	5. Assignment operator "=".
	 *	6. A binary operator, like "+", "-", "*", "/", "%", or "^".
	 *
	 *	@param expression	the expression to tokenize
	 *	@return				a List of tokens, each token has a length of one or greater
	 */
	public List<String> tokenizeExpression(String expression) {
		// remove extraneous characters from expression
		expression = cleanExpr(expression);

		// the expression tokens
		List<String> result = new ArrayList<String>();

		// Keep track of last token processed
		String lastToken = "";

		int ind = 0;	// index into String expression

		// while there are characters in the expression
		while (ind < expression.length()) {
			char c = expression.charAt(ind++);
			String token = "";

			// if character is "-" or "+"
			if (c == '-' || c == '+') {
				// Check for:
				// 	Expression starts with unary operator like "-4".
				//	Expression preceded by an assignment operator (=).
				// 	Expression contains consecutive binary and unary operators "3/-2".
				if (lastToken.length() == 0 || lastToken.equals("=") ||
						lastToken.length() == 1 && isBinaryOperator(lastToken.charAt(0))) {
					// precede number or variable with unary operator
					token += c;
					// If what follows is a digit, then input number
					if (Character.isDigit(expression.charAt(ind))) {
						// Add number to unary operator, e.g. "-" + "4.3" => "-4.3"
						while (ind < expression.length() &&
							( Character.isDigit(expression.charAt(ind)) ||
								expression.charAt(ind) == '.') )
							token += expression.charAt(ind++);
					}
					// else what follows is a variable, input variable
					else {
						while (ind < expression.length() &&
								Character.isLetter(expression.charAt(ind)))
							token += expression.charAt(ind++);
					}
					result.add(token);
				}
				else {
					token = "" + c;
					result.add(token);
				}
			}
			// if character is digit or decimal, read in number
			else if (Character.isDigit(c) || c == '.') {
				token += c;
				while (ind < expression.length() &&
					( Character.isDigit(expression.charAt(ind)) ||
						expression.charAt(ind) == '.') )
					token += expression.charAt(ind++);
				result.add(token);
			}
			// if character is a letter, read in alpha identifier
			else if (Character.isLetter(c)) {
				token += c;
				while (ind < expression.length() &&
							Character.isLetter(expression.charAt(ind)))
					token += expression.charAt(ind++);
				result.add(token);
			}
			// if character is operator or parentheses (, ), +, -, *, /, or '='
			else if (isOperator(c)) {
				token = "" + c;
				result.add(token);
			}

			// if anything else, do nothing

			lastToken = token;
		}

		return result;
	}

	/**
	 *	Remove extraneous characters (like spaces)
	 *	@param expr		the expression String
	 *	@return			the expression with extraneous characters removed
	 */
	private String cleanExpr(String expr) {
		String result = "";
		for (int a = 0; a < expr.length(); a++)
			if (validChar(expr.charAt(a))) result += expr.charAt(a);
		return result;
	}

	/**
	 *	Test if the character is valid:
	 *		letter, digit, arithmetic operator, or decimal point
	 *	@param c	character to check
	 *	@return		true if character is valid; false otherwise
	 */
	private boolean validChar(char c) {
		if (Character.isLetterOrDigit(c) || isOperator(c) || c == '.')
			return true;
		return false;
	}


	/**	Determine if character is valid arithmetic operator including parentheses
	 *	@param c	the character to check
	 *	@return		true if the character is '+', '-', '*', '/', '^', '=','(', or ')'
	 */
	public boolean isOperator(char c) {
		return isBinaryOperator(c) || c == '(' || c == ')';
	}

	/**	Determine if character is valid binary arithmetic operator excluding
	 *	parentheses.
	 *	@param c	the character to check
	 *	@return		true if the character is '+', '-', '*', '/', '^', or '='
	 */
	private boolean isBinaryOperator(char c) {
		switch (c) {
			case '+': case '-': case '*': case '/':
			case '%': case '=': case '^':
				return true;
		}
		return false;
	}

	/**
	 *	Determine if string is a number
	 *	@param str		the String to check
	 *	@return			true if a number; false otherwise
	 */
	public boolean isNumber(String str) {
		try {
			Double.parseDouble(str);
		}
		catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 *	Tests if String is operator
	 *	@param str		the String to test
	 *	@return			true if "+", "-", "*", "/", "%", or "^"
	 */
	public boolean isOperator(String str) {
		return isOperator(str.charAt(0));
	}


	/*************************************************************************/
	/************ Expression Validation methods (Extra Credit) ***************/
	/*************************************************************************/
	/**
	 *	Evaluate expression and determine if it is valid (extra credit)
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			true if expression is valid; false otherwise
	 *
	 *	Algorithm: (describe here)
	 */
	public boolean hasValidExpression(List<String> tokens) {

		return false;
	}


	/******************************************************************/
	/************************** For Testing ***************************/
	/******************************************************************/
	public static void main(String[] args) {
		ExprUtils et = new ExprUtils();
		et.run();
	}

	public void run() {
		System.out.println("\nTesting tokenizeExpression method\n");
		List<String> tokens;
		String expr;
		System.out.println();

		expr = "2 + 3 * 5";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");

		expr = "xa = 2.1 + 3 * (5 - 4)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");

		expr = "3.456 * 23 / (.5 - 23)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");

		expr = "- 54 + - .12";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");

		expr = "4 * (3 + 2) - 18 / (6 * 3)";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");

		expr = "- 1 + 1";
		tokens = tokenizeExpression(expr);
		System.out.println("expr = \"" + expr + "\"   tokens = " + tokens + "\n");
	}
}
