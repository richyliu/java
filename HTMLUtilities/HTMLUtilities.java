/**
 *	Utilities for handling HTML
 *
 *	@author	Richard Liu
 *	@since	16 Oct. 2018
 */
public class HTMLUtilities {

	// NONE = not nested in a block, COMMENT = inside a comment block 
	// PREFORMAT = inside a pre-format block 
	private enum TokenState { NONE, COMMENT, PREFORMAT }; 
	// the current tokenizer state
	private TokenState state;
	// punctuation characterss
	private final char[] PUNCTUATION = new char[]{'.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-'};
	
	
	/**
	 * Constructor, initialize fields with defaults.
	 */
	public HTMLUtilities() {
		state = TokenState.NONE;
	}
	

	/**
	 *	Break the HTML string into tokens. The array returned is
	 *	exactly the size of the number of tokens in the HTML string.
	 *	Example:	HTML string = "Goodnight moon goodnight stars"
	 *				returns { "Goodnight", "moon", "goodnight", "stars" }
	 *	@param str			the HTML string
	 *	@return				the String array of tokens
	 */
	public String[] tokenizeHTMLString(String inStr) {
		// ensure only 1 space surround string on either sides, so that the loop
		// can always look ahead 1 char and behind 1 char
		String str = " " + inStr.trim() + " ";
		
		// make the size of the array large to start
		String[] tokens = new String[10000];
		// current index of token
		int tokenIndex = 0;
		// current word
		String word = "";
		// currently on a word, add characters to word
		boolean inWord = false;
		// currently on a HTML tag (special type of token)
		boolean inTag = false;
		
		// return the current string if in a pre tag
		String ret = checkPre(inStr);
		// don't return if ret is null
		if (ret != null)
			return new String[]{ ret };
		
		
		// loop through input string, except for first and last space
		for (int i = 1; i < str.length()-1; i++) {
			// check if comment begins here
			if (state == TokenState.NONE &&
				str.length() >= i+4 &&
				str.substring(i, i+4).equals("<!--")) {
				state = TokenState.COMMENT;
			}
			// check if comment ends here
			if (state == TokenState.COMMENT &&
				i >= 3 &&
				str.substring(i-2, i+1).equals("-->")) {
				state = TokenState.NONE;
			}
			
			// previous character
			char p = str.charAt(i-1);
			// current character
			char c = str.charAt(i);
			// next character
			char n = str.charAt(i+1);
			
			// start of tag only if in normal mode
			if (state == TokenState.NONE && !inWord) {
				// html tag starts with '<'
				if (c == '<') {
					inWord = true;
					inTag = true;
				}
				// all other tokens (besides html tags)
				if (
						// previous character is a '>' or space
						(p == '>' || Character.isWhitespace(p)) &&
						// starting character can't be a space
						!Character.isWhitespace(c)) {
					inWord = true;
				}
			}
			// end of tag, add to tokens only if in normal mode
			if (state == TokenState.NONE && inWord && (
						// whitespace, end of non-html tag
						(!inTag && Character.isWhitespace(n)) ||
						// html tag ends
						c == '>' ||
						// next character is '<', word has to end 
						n == '<'
					)) {
				// add last '>' character
				word += c;
				
				// html token, directly add to array
				if (inTag) {
					tokens[tokenIndex] = word;
					tokenIndex++;
				// word, may contain more than 1 token
				} else {
					// tokenize word into individual tokens
					String[] wordTokens = tokenizeWord(word);
					// add to tokens array
					for (int j = 0; j < wordTokens.length; j++)
						tokens[tokenIndex + j] = wordTokens[j];
					// ensure index stays synced
					tokenIndex += wordTokens.length;
				}

				// reset tags and words
				inWord = false;
				inTag = false;
				word = "";
			}
			
			// if in a token right now, add character
			if (inWord)
				word += c;
		}
		// done with looping through string
		
		// create a smaller array of appropriate size
		String[] result = shrinkArray(tokens, tokenIndex);
		
		// return the correctly sized array
		return result;
	}


	/**
	 * Tokenize the word into individual tokens (a word is a string of text
	 * with spaces around it). The word cannot be an HTML tag.
	 * @param word  The input word to separate
	 * @return	  The tokens in the word
	 */
	public String[] tokenizeWord(String word) {
		// set tokens array to only word initially
		String[] tokens = new String[100];
		int tokenIndex = 0;
		
		// remove extraneous whitespace
		word = word.trim();
		boolean inNumber = false;
		String token = "";
		
		// whether a token has been added already in 1 run of the loop
		// prevent duplicate adding
		boolean added = false;
		
		// loop through string
		for (int i = 0; i < word.length(); i++) {
			added = false;
			
			// previous, current, and next characters
			char prev, cur, next;
			prev = cur = next = ' ';
			
			// ensure no out of bounds
			if (i > 0)
				prev = word.charAt(i-1);
			cur = word.charAt(i);
			if (i < word.length()-1)
				next = word.charAt(i+1);
			
			// check if number starts here
			if (!inNumber && !added && (
				// currently on a digit
				Character.isDigit(cur) ||
				// next is a digit and currently on negative sign
				(Character.isDigit(next) && cur == '-')
				// nubmer followed by negative is a new number
			) || inNumber && (cur == '-' && Character.isDigit(prev))) {
				// add to tokens array				
				if (token.length() > 0) {
					tokens[tokenIndex] = token;
					tokenIndex++;
					token = "";
				}
				added = true;
				inNumber = true;
			}
			
			// check if number ends here
			if (inNumber && !added && (
				// currently on a dot but next character not a digit
				(cur == '.' && !Character.isDigit(next)) ||
				// on a e and next character isn't negative or digit
				(cur == 'e' && next != '-' && !Character.isDigit(next)) ||
				// this is anything but digit, e, negative sign, or period
				(!Character.isDigit(cur) && cur != 'e' && cur != '-' && cur != '.')
			)) {
				// add to tokens array
				tokens[tokenIndex] = token;
				tokenIndex++;
				token = "";
				added = true;
				inNumber = false;
			}
			
			// check if on a punctuation
			if (!inNumber && !added && (
				// this is character or previous is character
				(isPunctuation(cur) || isPunctuation(prev))) &&
				// not a character-dash-character sequence (ignore as punctuation)
				!(cur == '-' && Character.isLetter(prev) &&
					Character.isLetter(next)) &&
				// not a character-dash-character sequence on the previous 3 chars
				!(prev == '-' && Character.isLetter(cur) && i > 1 &&
					Character.isLetter(word.charAt(i-2)))
			) {
				// add to tokens array
				if (token.length() > 0) {
					tokens[tokenIndex] = token;
					tokenIndex++;
					token = "";
				}
				added = true;
			}
			
			// add every character to a token
			token += cur;
		}
		// add last token to the array
		tokens[tokenIndex] = token;
		tokenIndex++;
		
		// shrink array to appropriate size
		tokens = shrinkArray(tokens, tokenIndex);

		return tokens;
	}
	
	
	/**
	 * Checks if the current line begins or ends a preformatted text. If
	 * currently in a preformatted tag, will return the line.
	 * @param line	Current line that is being parsed
	 * @return		Line if in preformatted text, otherwise null
	 */
	private String checkPre(String line) {
		// check if this line has pre (start of end of pre tag)
		if (state == TokenState.NONE && line.trim().equals("<pre>")) {
			state = TokenState.PREFORMAT;
			// only 1 tag, that is the start pre tag
			return "<pre>";
		}
		if (state == TokenState.PREFORMAT && line.trim().equals("</pre>")) {
			state = TokenState.NONE;
			// only 1 tag, that is the end pre tag
			return "</pre>";
		}
		
		// use entire line as token if in pre tag
		if (state == TokenState.PREFORMAT)
			return line;
		
		return null;
	}
	
	
	/**
	 * Checks if a character is a punctuation in the list:
	 * '.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-'
	 * @param c		Character to check
	 * @return		True if character is a punctuation
	 */
	public boolean isPunctuation(char c) {
		for (int i = 0; i < PUNCTUATION.length; i++)
			if (c == PUNCTUATION[i])
				return true;
		return false;
	}
	
	
	/**
	 * Shrink array of Strings to smaller size
	 * @param arr	String array to shrink
	 * @param len	Length to shrink to
	 * @return		Shrunken array of len size
	 */
	public String[] shrinkArray(String[] arr, int len) {
		String[] ret = new String[len];
		for (int i = 0; i < len; i++)
			ret[i] = arr[i];
		
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