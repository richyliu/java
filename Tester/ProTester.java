/**
 * Testing framework. Extend this class and override the run method with test
 * cases
 */
public class ProTester {
	
	private int numTotal;
	private int numCorrect;
	private String print;
	
	public static final String COLOR_RESET = "\u001B[0m";
	
	public static final int FG_BLACK = 30;
	public static final int FG_RED = 31;
	public static final int FG_GREEN = 32;
	public static final int FG_YELLOW = 33;
	public static final int FG_BLUE = 34;
	public static final int FG_PINK = 35;
	public static final int FG_CYAN = 36;
	public static final int FG_WHITE = 37;
	public static final int BG_BLACK = 40;
	public static final int BG_RED = 41;
	public static final int BG_GREEN = 42;
	public static final int BG_YELLOW = 43;
	public static final int BG_BLUE = 44;
	public static final int BG_PINK = 45;
	public static final int BG_CYAN = 46;
	public static final int BG_WHITE = 47;
	
	public static String genColorCode(int fg) {
		return "\u001B[" + fg + "m";
	}
	
	public static String genColorCode(int fg, int bg) {
		return "\u001B[" + bg + ";" + fg + "m";
	}
	
	public static void printColor(int fg, String str) {
		System.out.print(genColorCode(fg) + str + COLOR_RESET);
	}
	
	public static void printColor(int fg, int bg, String str) {
		System.out.print(genColorCode(fg, bg) + str + COLOR_RESET);
	}
	
	
	public ProTester() {
		numTotal = 0;
		numCorrect = 0;
		
		System.out.println();
		printLongBreak();
        run();
		printResults();
	}
	
	public void run() {
		System.out.println("Superclass");
	}
	
	/**
	 * Begin the next test block with name
	 * @param str	Name of the test block
	 */
	public void next(String str) {
		System.out.println();
		printShortBreak();
		System.out.println("TEST BLOCK:    " + str);
		ptClear();
	}
	
	/**
	 * Print the number of correct and incorrect test cases
	 */
	public void printResults() {
		System.out.println();
		printLongBreak();
		
		System.out.printf("%-12s %d / %d\n%-12s %.2f%%\n\n\n",
			"Correct:",
			numCorrect,
			numTotal,
			"Percentage:",
			(double)numCorrect/numTotal * 100
		);
	}
	
	/**
     * Prints to print buffer without newline
     * @param str Item to print (can be any type)
     */
    public <T> void pt(T str) {
		print += str;
	}
    /**
     * Prints to print buffer with newline
     * @param str Item to print (can be any type)
     */
    public <T> void ptl(T str) {
		print += str + "\n";
	}
    /**
     * Prints newline to print buffer
     * @param str Item to print (can be any type)
     */
    public void ptl() {
		print += "\n";
	}

    /**
     * Checks if 2 values are equal using equal method or reference comparison
     * @param a First item to compare
     * @param b Second item to compare
     */
    public <T> void be(T a, T b) {
        if (a == b || a.equals(b)) {
			printColor(FG_GREEN, "PASS");
            System.out.println(": \"" + a + "\"");
            numCorrect++;
            numTotal++;
        } else {
            printColor(FG_RED, "FAIL");
            System.out.print(": Expected: \"" + b + "\", received \"");
            printColor(FG_WHITE, BG_RED, (String)a);
            System.out.println("\"");
			
            numTotal++;
		}
    }
    
    /**
     * Check if the current print buffer is equal to string
     * @param a	To check if print buffer is equal to
     */
    public void ptBe(String a) {
		be(a, print);
	}
	
	/**
	 * Clear print buffer
	 */
	public void ptClear() {
		print = "";
	}
    
    /**
     * Prints an array with newlines separating the items
     * @param a Double array to print
     */
    public void pta(double[] a) {
        for (int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }
    
    /**
     * Prints a long line in the terminal using "="
     */
    public void printLongBreak() {
        System.out.println(
			"=================================================================="
		);
	}
    
    /**
     * Prints a short line in the terminal using "-"
     */
    public void printShortBreak() {
		System.out.println(
			"------------------------------------------------------------------"
		);
	}
}