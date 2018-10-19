/**
 *	Utilities for handling HTML
 *
 *	@author	Richard Liu
 *	@since	16 Oct. 2018
 */
public class HTMLUtilities {

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String inStr) {
		String str = inStr.trim();	// remove surrounding space, ensure it
									// starts and ends with a token
		
		// make the size of the array large to start
		String[] tokens = new String[10000];
		// current index of token
		int tokenIndex = 0;
		// current token
		String token = "";
		// currently on a token, add characters to token
		boolean inToken = false;
		// if current token is an html tag
		boolean inTag = false;
		
		str = " " + str + " ";
		
		// loop through input string
		for (int i = 1; i < str.length()-1; i++) {
			// start of tag
			if (!inToken && isTokenStart(str, i)) {
				inToken = true;
				if (str.charAt(i) == '<')
					inTag = true;
			}
			// end of tag, add to tokens
			if (inToken && isTokenEnd(str, i, inTag)) {
				// add last '>' character
				inToken = false;
				inTag = false;
				token += str.charAt(i);
				
				// add to tokens and reset
				tokens[tokenIndex] = token;
				tokenIndex++;
				token = "";
			}
			
			// if in a token right now, add character
			if (inToken) {
				token += str.charAt(i);
			}
		}
		
		
		// create a smaller array of appropriate size
		String[] result = shrinkArray(tokens, tokenIndex);
		
		// return the correctly sized array
		return result;
	}
	
	
	/**
	 * Checks if a token starts at the given index
	 * Precondition: Not currently in a token
	 * @param str	String to look in
	 * @param i		Index of start of token to check
	 * @return		If true a token starts at index i
	 */
	public boolean isTokenStart(String str, int i) {
		char c0 = str.charAt(i-1);
		char c1 = str.charAt(i);
		char c2 = str.charAt(i+1);
		
		if (c1 == '<')
			return true;
		
		if (c0 == '>' && Character.isLetter(c1))
			return true;
		
		if (c0 == '<')
			return false;
		
		if (!isAlphaHyphen(c0) && isAlphaHyphen(c1))
			return true;
		
		if (!isPunctuation(c0) && isPunctuation(c1))
			return true;
		
		if (!Character.isDigit(c0) && Character.isDigit(c1))
			return true;
		
		return false;
	}
	
	
	/**
	 * Checks if a token ends given the current letter and the next letter
	 * Precondition: Not currently in a token
	 * @param str	String to look in
	 * @param i		Index of start of token to check
	 * @param inTag	If currently in a HTML tag
	 * @return		If true a token ends at letter c
	 */
	public boolean isTokenEnd(String str, int i, boolean inTag) {
		char c0 = str.charAt(i-1);
		char c1 = str.charAt(i);
		char c2 = str.charAt(i+1);
		
		if (inTag && c1 == '>')
			return true;
		
		if (inTag) return false;
		
		if (isAlphaHyphen(c1) && !isAlphaHyphen(c2))
			return true;
		
		if (c1 == '-' && isAlphaHyphen(c2))
			return false;
		
		if (Character.isDigit(c1) && c2 == '.')
			return false;
		
		if (Character.isDigit(c0) && Character.isDigit(c2))
			return false;
		
		if (isPunctuation(c1) && !isPunctuation(c2))
			return true;
		
		if (Character.isDigit(c1) && !Character.isDigit(c2))
			return true;
		
		return false;
	}
	
	
	/**
	 * Checks if a character is an alpha character (upper or lowercase "a" to
	 * "z") or a hypen
	 * @param c		Character to check
	 * @return		True if character is alpha or hypen
	 */
	public boolean isAlphaHyphen(char c) {
		return Character.isLetter(c) || c == '-';
	}
	
	
	/**
	 * Checks if a character is a punctuation in the list:
	 * '.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-'
	 * @param c		Character to check
	 * @return		True if character is a punctuation
	 */
	public boolean isPunctuation(char c) {
		return ".,;:()?!=&~+-".indexOf(c) > -1;
	}
	
	
	/**
	 * Shrink array of Strings to smaller size
	 * @param arr	String array to shrink
	 * @param len	Length to shrink to
	 * @return		Shrunken array of len size
	 */
	public String[] shrinkArray(String[] arr, int len) {
		String[] ret = new String[len];
		for (int i = 0; i < len; i++) {
			ret[i] = arr[i];
		}
		
		return ret;
	}
	
	
	/**
	 *	Print the tokens in the array to the screen
	 *	Precondition: All elements in the array are valid String objects.
	 *				(no nulls)
	 *	@param tokens		an array of String tokens
	 */
	public void printTokens(String[] tokens) {
		if (tokens == null) return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0) System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}

}