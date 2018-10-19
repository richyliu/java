/**
 * Testing framework. Extend this class and override the run method with test
 * cases
 */
public class ProTester {
	
	private int numTotal;
	private int numCorrect;
	
	public ProTester() {
		numTotal = 0;
		numCorrect = 0;
		printLongBreak();
        
		run();
		printResults();
	}
	
	public void run() {
		ptl("Superclass");
	}
	
	/**
	 * Begin the next test block with name
	 * @param str	Name of the test block
	 */
	public void next(String str) {
		ptl();
		ptl();
		printShortBreak();
		ptl("TEST BLOCK:    " + str);
	}
	
	/**
	 * Print the number of correct and incorrect test cases
	 */
	public void printResults() {
		ptl();
		ptl();
		printLongBreak();
		System.out.printf("Correct : %d\nTotal: %d\nCorrect%%: %.2f%%\n\n",
			numCorrect,
			numTotal,
			(double)numCorrect/numTotal * 100);
	}
	
	/**
     * Prints to system output without newline
     * @param str Item to print (can be any type)
     */
    public <T> void pt(T str) { System.out.print(str); }
    /**
     * Prints to system output with newline
     * @param str Item to print (can be any type)
     */
    public <T> void ptl(T str) { System.out.println(str); }
    /**
     * Prints newline to system output
     * @param str Item to print (can be any type)
     */
    public void ptl() { System.out.println(); }

    /**
     * Checks if 2 values are equal using equal method or reference comparison
     * @param a First item to compare
     * @param b Second item to compare
     */
    public <T> void be(T a, T b) {
        if (a == b || a.equals(b)) {
            ptl("PASS: " + a);
            numCorrect++;
            numTotal++;
        } else {
            pt("!!FAIL: ");
			ptl(a + " should be " + b);
            numTotal++;
		}
    }
    
    /**
     * Prints an array with newlines separating the items
     * @param a Double array to print
     */
    public void pta(double[] a) {
        for (int i = 0; i < a.length; i++)
            ptl(a[i]);
    }
    
    /**
     * Prints a long line in the terminal using "="
     */
    public void printLongBreak() {
        ptl("=================================================");
	}
    
    /**
     * Prints a short line in the terminal using "="
     */
    public void printShortBreak() {
		ptl("----------------------------------------");
	}
}