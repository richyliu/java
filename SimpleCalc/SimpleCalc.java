import java.util.List;		// used by expression evaluator
import java.util.HashMap;	// used to store variables
import java.util.Map;		// used to store variables
import java.util.Set;

/**
 *	A simple calculator that takes in user input and computes the output. This
 * 	accepts the symbols "+", "-", "/", "*", "%", and "^". This calculator also
 * 	works with variables, declaring them with the "=" operator. Variables can
 * 	only contain letters and are case sensitive.
 *
 *	@author	Richard Liu
 *	@since	Feb. 26, 2019
 */
public class SimpleCalc {

	private ExprUtils utils;	// expression utilities

	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack
	private Map<String, Identifier> variables;		// variables map

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

		// initialize variables with pi and e
		variables = new HashMap<String, Identifier>();
		variables.put("pi", new Identifier("pi", Math.PI));
		variables.put("e", new Identifier("e", Math.E));
	}

	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}

	/**
	 * Main method, calls the runCalc to do most of the work
	 */
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

		// while user has not tried to quit
		while (!input.equals("q")) {
			// get input
			input = Prompt.getString("");
			// print help if user requests it
			if (input.equals("h"))
				printHelp();
			// list the variables
			else if (input.equals("l")) {
				System.out.println("\nVariables:");
				// get the keys (which are the variable names)
				Set<String> names = variables.keySet();
				for (String name : names)
					System.out.printf("    %-10s=%10f\n", name, variables.get(name).getValue());
			// if not quitting and input has text
			} else if (!input.equals("q") && input.length() > 0) {
				// only process if input is valid
				if (isValid(input)) {
					// tokenize the input and evaluate it
					List<String> tokens = utils.tokenizeExpression(input);
					// put the result into a variable if doing assignment
					double result = 0;
					if (tokens.size() >= 3 && tokens.get(1).equals("=") && tokens.get(0).matches("[a-zA-Z]+")) {
						result = evaluateExpression(tokens.subList(2, tokens.size()));
						variables.put(tokens.get(0), new Identifier(tokens.get(0), result));
					} else
						result = evaluateExpression(tokens);
					// truncate to 6 decimal places
					result = Math.floor(result * 1e6) / 1e6;
					// print result
					if (DEBUG)
						System.out.println(GREEN + "Result: " + result + RESET);
					else
						System.out.println(result);
				// print invalid input
				} else if (DEBUG) {
					System.out.println(RED + "Invalid input" + RESET);
				}
			}
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
		if (DEBUG)
			System.out.printf("%s         %-30s%-30s%s\n", BLUE, "Value stack", "Operator stack", RESET);

		// loop through the tokens
		for (String token : tokens) {
			// if current token is operator
			if (token.length() == 1 && utils.isOperator(token.charAt(0))) {
				// currently no operator on the stack, must push current one
				// or if left paren, push onto operator stack to be used as a marker
				// or if both ops are powers, push it and do it later
				if (operatorStack.isEmpty() || token.equals("(")) {
					operatorStack.push(token);
				// once right paren is reached, ...
				} else if (token.equals(")")) {
					// ... do all the operations until the left paren
					while (!operatorStack.peek().equals("("))
						doOp();
					// then remove the left paren
					operatorStack.pop();
				// if left op has precedence, do it first ...
				} else if (hasPrecedence(token, operatorStack.peek())) {
					while (!operatorStack.isEmpty() && hasPrecedence(token, operatorStack.peek()))
						doOp();
					// ... then push the current op
					operatorStack.push(token);
				// otherwise just push the token
				} else {
					operatorStack.push(token);
				}
			// otherwise it could be an variable
			} else if (token.matches("[a-zA-Z]+")) {
				// get the variable value and push it onto the valueStack
				Identifier variable = variables.get(token);
				if (variable != null)
					valueStack.push(variable.getValue());
				else {
					valueStack.push(0.0);
					System.err.println(RED + "ERROR undefined variable: " + token + RESET);
				}
			// or a number
			} else {
				try {
					// current token is a number, push onto value stack 
					valueStack.push(Double.parseDouble(token));
				} catch (NumberFormatException e) {
					System.err.println(RED + "ERROR encountered unknown operation: " + token + RESET);
				}
			}
		}
		// if there are still operators that need to be done, do them
		while (!operatorStack.isEmpty())
			doOp();

		// the last value is the result
		return valueStack.pop();
	}

	/**
	 * Pop an operation off of the operator stack and apply it to the 2 values
	 * on the value stack, pushing the result back onto the value stack
	 */
	private void doOp() {
		if (DEBUG)
			System.out.printf("%s[debug]: %-30s%-30s%s\n", BLUE, valueStack, operatorStack, RESET);

		// get operators in correct order
		double b = valueStack.pop();
		double a = valueStack.pop();
		// do the operation and oush back onto value stack
		valueStack.push(resolveOp(a, b, operatorStack.pop().charAt(0)));

		if (DEBUG)
			System.out.printf("%s[debug]: %-30s%-30s%s\n", BLUE, valueStack, operatorStack, RESET);
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
		// do the actual operation based on the operator
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
	 * processed using a regex filter and parenthesis balance checking.
	 * 
	 * @param input	Input string from user
	 * @return		Whether input is valid or not
	 */
	private boolean isValid(String input) {
		// check if input is valid
		if (input
			// remove all spaces
			.replaceAll("\\s", "")
			// this regex checks for validity
			.matches("([a-zA-Z]+=)?(\\(*([\\d.]+|[a-zA-Z]+)\\)*[-+*/%\\^]\\)*)*\\(*([\\d.]+|[a-zA-Z]+)\\)*")) {
			// check for parenthesis balancing
			int depth = 0;
			for (int i = 0; i < input.length(); i++) {
				if (input.charAt(i) == '(') depth++;
				else if (input.charAt(i) == ')') depth--;
			}

			return depth == 0;
		} else
			return false;
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
