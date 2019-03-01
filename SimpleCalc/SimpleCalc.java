import java.util.List;		// used by expression evaluator

/**
 *	<Description goes here>
 *
 *	@author	Richard Liu
 *	@since	Feb. 26, 2019
 */
public class SimpleCalc {

	private ExprUtils utils;	// expression utilities

	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	private static final boolean DEBUG = false;

	private static final String RED = DEBUG ? "\u001b[31m" : "";
	private static final String GREEN = DEBUG ? "\u001b[32m" : "";
	private static final String BLUE = DEBUG ? "\u001b[34m" : "";
	private static final String WHITE = DEBUG ? "\u001b[37m" : "";
	private static final String BG_BLACK = DEBUG ? "\u001b[40m" : "";
	private static final String RESET = DEBUG ? "\u001b[0m" : "";

	// constructor	
	public SimpleCalc() {
		valueStack = new ArrayStack<Double>();
		operatorStack = new ArrayStack<String>();
		utils = new ExprUtils();
	}

	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}

	public void run() {
		System.out.println(WHITE + BG_BLACK + "\nWelcome to SimpleCalc!!!" + RESET);
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}

	/**
	 *	Prompt the user for expressions, run the expression evaluator,
	 *	and display the answer.
	 */
	public void runCalc() {
		String input = "";

		while (!input.equals("q")) {
			input = Prompt.getString("");
			if (input.equals("h"))
				printHelp();
			else if (!input.equals("q") && isValid(input))
				System.out.println(GREEN + evaluateExpression(utils.tokenizeExpression(input)) + RESET);
		}
	}

	/**	Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'");
	}

	/**
	 *	Evaluate expression and return the value
	 *	@param tokens	a List of String tokens making up an arithmetic expression
	 *	@return			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {
		for (String token : tokens) {
			if (token.length() == 1 && utils.isOperator(token.charAt(0))) {
				// current token is an operator
				if (operatorStack.isEmpty()) {
					operatorStack.push(token);
				} else if (token.equals("(")) {   
					operatorStack.push(token);
				} else if (token.equals(")")) {
					while (!operatorStack.peek().equals("("))
						doOp();
					operatorStack.pop();
				} else if (token.equals("^") && operatorStack.peek().equals("^")) {
					doOp();
					operatorStack.push(token);
				} else if (hasPrecedence(token, operatorStack.peek())) {
					doOp();
					operatorStack.push(token);
				} else {
					operatorStack.push(token);
				}
			} else {				
				try {
					// current token is a number, push onto value stack 
					valueStack.push(Double.parseDouble(token));
				} catch (NumberFormatException e) {
					System.err.println(RED + "ERROR encountered unknown operation: " + token + RESET);
				}
			}
		}
		while (!operatorStack.isEmpty())
			doOp();

		return valueStack.pop();
	}

	/**
	 * Pop an operation off of the operator stack and apply it to the 2 values
	 * on the value stack, pushing the result back onto the value stack
	 */
	private void doOp() {
		if (DEBUG)
			System.out.printf("%s[debug]: %-30s%-30s\n%s", BLUE, valueStack, operatorStack, RESET);

		double b = valueStack.pop();
		double a = valueStack.pop();
		valueStack.push(resolveOp(a, b, operatorStack.pop().charAt(0)));

		if (DEBUG)
			System.out.printf("%s[debug]: %-30s%-30s\n%s", BLUE, valueStack, operatorStack, RESET);
	}

	/**
	 * Do the actual math operation
	 * 
	 * @param a		Left of operator
	 * @param b		Right of operator
	 * @param op	Actual operator
	 * @return		Result of calculation
	 */
	private double resolveOp(double a, double b, char op) {
		switch (op) {
			case '+': return a + b;
			case '-': return a - b;
			case '*': return a * b;
			case '/': return a / b;
			case '%': return a % b;
			case '^': return Math.pow(a, b);
			default:
					  System.err.println(RED + "ERROR encountered unknown operation: " + op + RESET);
					  return 0;
		}
	}

	/**
	 * Checks if the input string is a valid math expression that can be
	 * processed using a dumb regex filter. Some edge cases that get accepted
	 * are:
	 *		(3 + 2))	<-- unbalanced parentheses
	 * 
	 * @param input	Input string from user
	 * @return		Whether input is valid or not
	 */
	private boolean isValid(String input) {
		if (input.length() < 1) return false;
		return input.replaceAll(" ", "").matches("(\\(*[\\d.]+\\)*[-+*/%\\^]\\)*)*\\(*[\\d.]+\\)*");
	}

	/**
	 *	Precedence of operators
	 *	@param op1	operator 1
	 *	@param op2	operator 2
	 *	@return		true if op2 has higher or same precedence as op1; false otherwise
	 *	Algorithm:
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

}
