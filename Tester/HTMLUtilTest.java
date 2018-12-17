import java.util.ArrayList;

public class HTMLUtilTest {
    public static void main(String[] args) {
        //System.out.println(removeTags("no<p> tags </p><i>"));
        String[] out = tokenizeText("no   tags in this text? <b> foo </b> hi");
        
        for (String word : out)
			System.out.println(word);
    }
    
    public static String[] tokenizeText(String str) {
		String text = removeTags(str).trim() + " a";
		String[] tokens = new String[10000];
		int tokenIndex = 0;
		String token = "";
		
		for (int i = 0; i < text.length()-1; i++) {
			char c = text.charAt(i);
			char n = text.charAt(i+1);
			
			if (Character.isWhitespace(c) && !Character.isWhitespace(n)) {
				tokens[tokenIndex] = token.trim();
				tokenIndex++;
				token = "";
			} else {
				token += c;
			}
		}
		
		return resizeArray(tokens, tokenIndex);
	}
	
	public static String[] resizeArray(String[] arr, int arrSize) {
		String[] ret = new String[arrSize];
		
		for (int i = 0; i < arrSize; i++)
			ret[i] = arr[i];
		
		return ret;
	}
	
	public static String removeTags(String str) {
		String ret = "";
		boolean inTag = false;
		
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			
			if (inTag) {
				if (c == '>') inTag = false;
			} else {
				if (c == '<') {
					inTag = true;
					ret += ' ';
				} else
					ret += c;
			}
		}
		
		return ret;
	}
}