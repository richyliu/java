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
		PARAGRAPH,
		QUOTATION,
		BOLD,
		ITALIC,
		H1,
		H2,
		H3,
		H4,
		H5,
		H6,
		PRE,
		NONE
	};
	// type of text currently parsing
	private TextState state;
	
		
	public HTMLRender(String fileName) {
		this.fileName = fileName;
		
		// Initialize token array
		tokens = new String[TOKENS_SIZE];
		
		// Initialize Simple Browser
		render = new SimpleHtmlRenderer();
		browser = render.getHtmlPrinter();
		
		state = TextState.NONE;
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
		HTMLUtilities util = new HTMLUtilities();
		int index = 0;
		String[] curTokens;
		
		// Open the HTML file
		input = FileUtils.openToRead(fileName);
		
		// Read each line of the HTML file, tokenize, then print tokens
		while (input.hasNext()) {
			String line = input.nextLine();
			curTokens = util.tokenizeHTMLString(line);
			
			for (int i = 0; i < curTokens.length; i++) {
				tokens[index] = curTokens[i];
				index++;
			}
		}
		
		input.close();
		
		tokens = shrinkArray(tokens, index);
	}
	
	
	public void run() {
		// read file
		readFile();
		
		//for (int i = 0; i < tokens.length; i++)
		//	System.out.printf("[%3d]: %-20s %s", i, tokens[i], i % 4 == 3 ? "\n" : "");
		
		render();
	}
	
	
	public void render() {
		String cur = "";
		
		// for now skip html and body tokens
		for (int i = 2; i < tokens.length - 2; i++) {
			cur = tokens[i];
			
			if (cur.equalsIgnoreCase("<p>"))
				state = TextState.PARAGRAPH;
			else if (cur.equalsIgnoreCase("</p>"))
				state = TextState.NONE;
			
			
			switch(state) {
				case PARAGRAPH:
					browser.print(cur + ' ');
					break;
				case BOLD:
					browser.printBold(cur);
					break;
			}
		}
		
		/*
		// Sample renderings from HtmlPrinter class
		
		// Print plain text without line feed at end
		browser.print("First line");
		
		// Print line feed
		browser.println();
		
		// Print bold words and plain space without line feed at end
		browser.printBold("bold words");
		browser.print(" ");
		
		// Print italic words without line feed at end
		browser.printItalic("italic words");
		
		// Print horizontal rule across window (includes line feed before and after)
		browser.printHorizontalRule();
		
		// Print words, then line feed (printBreak)
		browser.print("A couple of words");
		browser.printBreak();
		browser.printBreak();
		
		// Print a double quote
		browser.print("\"");
		
		// Print Headings 1 through 6 (Largest to smallest)
		browser.printHeading1("Heading1");
		browser.printHeading2("Heading2");
		browser.printHeading3("Heading3");
		browser.printHeading4("Heading4");
		browser.printHeading5("Heading5");
		browser.printHeading6("Heading6");
		
		// Print pre-formatted text (optional)
		browser.printPreformattedText("Preformat Monospace\tfont");
		browser.printBreak();
		browser.print("The end");
		*/
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
	
}