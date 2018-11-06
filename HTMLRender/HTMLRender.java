import java.util.Scanner;
import java.util.ArrayList;

/**
 *	HTMLRender
 *	This program renders HTML code into a JFrame window.
 *	It requires your HTMLUtilities class and
 *	the SimpleHtmlRenderer and HtmlPrinter classes.
 *
 *	The tags supported:
 *		<html>, </html> - start/end of the HTML file
 *		<body>, </body> - start/end of the HTML code
 *		<p>, </p> - Start/end of a paragraph.
 *					Causes a newline before and a blank line after. Lines are restricted
 *					to 80 characters maximum.
 *		<hr>	- Creates a horizontal rule on the following line.
 *		<br>	- newline (break)
 *		<b>, </b> - Start/end of bold font print
 *		<i>, </i> - Start/end of italic font print
 *		<q>, </q> - Start/end of quotations
 *		<hX>, </hX> - Start/end of heading with size X = 1, 2, 3, 4, 5, 6
 *		<pre>, </pre> - Preformatted text
 *
 *	@author		Richard Liu
 *	@since		Oct. 30 2018
 */
public class HTMLRender {
	
	// the array holding all the tokens of the HTML file
	private String [] tokens;
	private final int TOKENS_SIZE = 100000;	// size of array

	// SimpleHtmlRenderer fields
	private SimpleHtmlRenderer render;
	private HtmlPrinter browser;
	
	// name of html file to render
	private String fileName;
	
	private enum TextState {
		TEXT,
		BOLD,
		ITALIC,
		H1,
		H2,
		H3,
		H4,
		H5,
		H6,
		PRE,
		NONE,
	};
	// type of text currently parsing
	private TextState state;
	
	private HTMLUtilities util;
	
		
	public HTMLRender(String fileName) {
		this.fileName = fileName;
		
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
		
		state = TextState.TEXT;
		
		util = new HTMLUtilities();
	}
	
	
	public static void main(String[] args) {
		String fileName = "";
		
		// if the command line contains the file name, then store it
		if (args.length > 0)
			fileName = args[0];
		// otherwise print out usage message
		else {
			System.out.println("Usage: java -cp .:SimpleHtmlRenderer.jar " +
				"HTMLRender <htmlFileName>");
			System.exit(0);
		}
		
		HTMLRender hf = new HTMLRender(fileName);
		hf.run();
	}
	
	
	/**
	 * Read the file specified by the field variable fileName and tokenize it
	 * using HTMLUtilities.
	 */
	public void readFile() {
		Scanner input = null;
		int index = 0;
		String[] curTokens;
		
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
		
		// Read each line of the HTML file, tokenize, then print tokens
		while (input.hasNext()) {
			String line = input.nextLine();
			curTokens = util.tokenizeHTMLString(line);
			
			// add tokens of this line to big tokens array
			for (int i = 0; i < curTokens.length; i++) {
				tokens[index] = curTokens[i];
				index++;
			}
		}
		
		input.close();
		
		// shrink array to appropriate size
		tokens = shrinkArray(tokens, index);
	}
	
	
	/**
	 * Main runner method, called by the main method
	 */
	public void run() {
		// read file
		readFile();
		
		// draw the html file contents to the "browser"
		render();
	}
	
	
	/**
	 * Render the HTML file using the tokens array and the browser
	 */
	public void render() {
		// previous, current and next token
		String prev = "";
		String cur = "";
		String next = "";
		// token that will be printed to the browser
		String printToken = "";
		// current character in the line
		int lineChar = 0;
		// maximum characters on this line
		int maxChars = 80;
		
		
		/* Loop over tokens, parse each one */
		
		// for now skip html and body tokens
		for (int i = 2; i < tokens.length - 2; i++) {
			prev = tokens[i-1];
			cur = tokens[i];
			next = tokens[i+1];
			
			/* Handle ending tags first */
			switch(cur.toLowerCase()) {
				case "</h1>":
				case "</h2>":
				case "</h3>":
				case "</h4>":
				case "</h5>":
				case "</h6>":
				case "</p>":
				case "</pre>":
					// reset to normal text length
					maxChars = 80;
					lineChar = 0;
					// needs break to separate text from the other blocks
					browser.printBreak();
				case "</i>":
				case "</b>":
					// reset state to text
					state = TextState.TEXT;
					break;
				case "</q>":
					browser.print("\"");
					break;
			}
			
			/* Wrap around to new line */
			if (
				// current line exceeds maximum chars
				lineChar >= maxChars
				// and don't break punctuation
				&& !(cur.length() == 1 && isPunctuation(cur.charAt(0)))
			) {
				browser.println();
				lineChar = 0;
			}
			
			/* Print the token if it isn't a tag*/
			if (!isTag(cur)) {
				printToken = cur;
				// add space before
				if (
					// if not on a punctuation
					!(cur.length() == 1 && isPunctuation(cur.charAt(0)))
					// and previous was not a quote tag
					&& !prev.equalsIgnoreCase("<q>")
				) {
					printToken = ' ' + printToken;
				}
				
				// print token formatted based on state
				switch(state) {
					case TEXT:
						browser.print(printToken);
						break;
					case BOLD:
						browser.printBold(printToken);
						break;
					case ITALIC:
						browser.printItalic(printToken);
						break;
					case H1:
						browser.printHeading1(printToken);
						break;
					case H2:
						browser.printHeading2(printToken);
						break;
					case H3:
						browser.printHeading3(printToken);
						break;
					case H4:
						browser.printHeading4(printToken);
						break;
					case H5:
						browser.printHeading5(printToken);
						break;
					case H6:
						browser.printHeading6(printToken);
						break;
					case PRE:
						browser.printPreformattedText(cur);
						browser.printBreak();
						break;
				}
				
				// add to lineChar based on printed length
				lineChar += printToken.length();
			}
			
			
			/* Handle starting tags */
			switch(cur.toLowerCase()) {
				case "<br>":
					// break line, reset lineChar
					lineChar = 0;
					browser.printBreak();
					break;
				case "<hr>":
					browser.printHorizontalRule();
					break;
				case "<p>":
					// set to text mode
					state = TextState.TEXT;
					// limit maxChars
					maxChars = 80;
					lineChar = 0;
					browser.printBreak();
					break;
				// set the state according to the tag
				case "<b>":
					state = TextState.BOLD;
					break;
				case "<i>":
					state = TextState.ITALIC;
					break;
				case "<q>":
					browser.print(" \"");
					break;
				case "<pre>":
					state = TextState.PRE;
					break;
				case "<h1>":
					// set the state
					state = TextState.H1;
					// different max chars for different headers
					maxChars = 40;
					lineChar = 0;
					browser.printBreak();
					break;
				case "<h2>":
					state = TextState.H2;
					maxChars = 50;
					lineChar = 0;
					browser.printBreak();
					break;
				case "<h3>":
					state = TextState.H3;
					maxChars = 60;
					lineChar = 0;
					browser.printBreak();
					break;
				case "<h4>":
					state = TextState.H4;
					maxChars = 80;
					lineChar = 0;
					browser.printBreak();
					break;
				case "<h5>":
					state = TextState.H5;
					maxChars = 100;
					lineChar = 0;
					browser.printBreak();
					continue;
				case "<h6>":
					state = TextState.H6;
					maxChars = 120;
					lineChar = 0;
					browser.printBreak();
					break;
			}
		}
	}
	
	
	/**
	 * Shrink array of Strings to smaller size
	 * @param arr	String array to shrink
	 * @param len	Length to shrink to
	 * @return		Shrunken array of len size
	 */
	private String[] shrinkArray(String[] arr, int len) {
		String[] ret = new String[len];
		for (int i = 0; i < len; i++)
			ret[i] = arr[i];
		
		return ret;
	}
	
	/**
	 * Check if a string is an HTML tag. A tag starts with "<" and ends with ">"
	 * @param str	To check if it's an HTML tag
	 * @return		True if str is an HTML tag
	 */
	private boolean isTag(String str) {
		return
			str.length() > 2 &&
			str.charAt(0) == '<' &&
			str.charAt(str.length()-1) == '>';
	}
	
	/**
	 * Check if a tag is a block tag. Block tags begin and end with a newline.
	 * @param str	To check if it's a block tag
	 * @return		True if str is a block tag
	 */
	private boolean isBlockTag(String str) {
		if (isTag(str)) {
			// lowercase to avoid checking case
			switch (str.toLowerCase()) {
				// starting and ending tags
				case "<p>":
				case "<h1>":
				case "<h2>":
				case "<h3>":
				case "<h4>":
				case "<h5>":
				case "<h6>":
				case "</p>":
				case "</h1>":
				case "</h2>":
				case "</h3>":
				case "</h4>":
				case "</h5>":
				case "</h6>":
					return true;
				default:
					return false;
			}
		} else {
			return false;
		}
	}
	
	
	/**
	 * Checks if a character is a punctuation in the list:
	 * '.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-'
	 * @param c		Character to check
	 * @return		True if character is a punctuation
	 */
	private boolean isPunctuation(char c) {
		char[] PUNCTUATION = new char[]{'.', ',', ';', ':', '(', ')', '?', '!',
			'=', '&', '~', '+', '-'};
		
		for (int i = 0; i < PUNCTUATION.length; i++)
			if (c == PUNCTUATION[i])
				return true;
		return false;
	}
	
}