// imports go here
import java.util.Scanner;
import java.io.PrintWriter;


/**
 * 	MVCipher.java	
 * 
 *	A MVCipher implementation that encoded and decodes plaintext using a
 * 	rotating cipher
 *	Requires Prompt and FileUtils classes.
 *	
 *	@author	Richard Liu
 *	@since	September 20, 2018
 */
public class MVCipher {
	
	// fields go here
	private Scanner inputFile;		// Scanner to edit the input file
	private PrintWriter outputFile;	// PrintWriter to edit the outpu file
	
	/** Constructor */
	public MVCipher() {
		inputFile = null;
		outputFile = null;
	}
	
	public static void main(String[] args) {
		MVCipher mvc = new MVCipher();
		mvc.run();
	}
	
	/**
	 *	Program to encrypt or decrypt a file, asking the user for a key
	 */
	public void run() {
		System.out.println("\n Welcome to the MV Cipher machine!\n");
		
		/* Prompt for a key and change to uppercase
		   Do not let the key contain anything but alpha
		   Use the Prompt class to get user input */
		String key = Prompt.getLetterString("Please input a word to use as " +
			"key (letters only)");
		// key must be uppercase
		key = key.toUpperCase();
		
		
		/* Prompt for encrypt or decrypt */
		int num = Prompt.getInt("\nEncrypt or decrypt?", 1, 2);
		// use decrypt boolean to store state
		boolean decrypt = num == 2;
		
			
		/* Prompt for an input file name */
		// promtp decrypt or decrypt according to boolean
		String input = Prompt.getString("Name of file to " +
			(decrypt ? "decrypt" : "encrypt"));
		
		
		/* Prompt for an output file name */
		String output = Prompt.getString("Name of output file");
		
		
		/* Read input file, encrypt or decrypt, and print to output file */
		inputFile = FileUtils.openToRead(input);
		outputFile = FileUtils.openToWrite(output);
		
		// do the actual cipher with the key
		doCipher(key, decrypt);
		
		
		/* Don't forget to close your output file */
		inputFile.close();
		outputFile.close();
		
		// tell the user what the program has done
		System.out.printf(
			"\nThe %s file %s has been created using the keyword -> %s\n\n",
			decrypt ? "decrypted" : "encrypted",
			input,
			key
		);
	}
	
	// other methods go here
	
	/**
	 * Main method for the cipher. Encrypts or decrypts the input file and
	 * writes it to the output
	 * @param key		To encrypt the input file with
	 * @param decrypt	True to decrypt, false to encrypt
	 */
	public void doCipher(String key, boolean decrypt) {
		String line = "";
		
		// store the key in number format (number between 0 and 25)
		int[] keyNums = new int[key.length()];
		
		// convert key to an array of numbers between 0 and 25
		for (int i = 0; i < keyNums.length; i++) {
			// convert key character to number (subtract extra 1 to get 1 to 26)
			keyNums[i] = (int)(key.charAt(i) - 'A' - 1);
			
			// get the opposite of the key to decrypt
			if (decrypt)
				keyNums[i] = 26 - keyNums[i];
		}
		
		// get whole file line by line
		String file = "";
		// solve trailing newline problem
		if (inputFile.hasNext()) {
			file += getNextInputLine();
		}
		// write newline first then the input line
		while(inputFile.hasNext()) {
			file += "\n" + getNextInputLine();
		}
		
		// encrypt/decrypt and write to the output file
		outputFile.println(encryptString(file, keyNums));
	}
	
	
	/**
	 * Get the next line of the input
	 * @return	The next line
	 */
	public String getNextInputLine() {
		return inputFile.nextLine();
	}
	
	
	/**
	 * Encrypt the alpha characters of a plaintext string with the key using the
	 * encryptUpperChar and encryptLowerChar methods
	 * @param in	Input string to encrypt
	 * @param key[]	Key to encrypt with (numbers between 0 and 25)
	 * @return 		Resulting encrypted string
	 */
	public String encryptString(String in, int[] key) {
		// the encrypted char array to return (will become string later)
		char[] encrypted = new char[in.length()];
		// letter of the key currently using
		int keyLetter = 0;
		// current charater being encrypted
		char current = ' ';
		
		// loop through all characaters of string
		for (int i = 0; i < in.length(); i++) {
			// get the current character
			current = in.charAt(i);
			
			// only encrypt/decrypt letters
			if (Character.isLetter(current)) {
				// uppercase
				if ('A' <= current && current <= 'Z') {
					// encrypt/decrypt with current letter and letter of key
					encrypted[i] = encryptUpperChar(current, key[keyLetter]);
				// lowercase
				} else {
					// encrypt/decrypt with current letter and letter of key
					encrypted[i] = encryptLowerChar(current, key[keyLetter]);
				}
				
				// increment current letter counter
				keyLetter++;
				// wrap around to beginning of key
				keyLetter %= key.length;
			} else {
				// if not letter, need to add it unchanged to encrypted array
				encrypted[i] = current;
			}
		}
		
		// create string from the char array
		return new String(encrypted);
	}
	
	
	/**
	 * Encrypt one uppercase character using the MVCipher with the given
	 * rotation key
	 * @param c		Character to encrypt
	 * @param key	Rotation key, number from 0 to 25
	 * @return ret 	Encrypted uppercase character, made by adding key to c and
	 * 				wrapping around the character.
	 */
	public char encryptUpperChar(char c, int key) {
		// alphabet code of c (0 is A, 25 is Z)
		int code = c - 'A';
		// add key to char and wrap around 26
		int resultCode = (code + key) % 26;
		
		// turn to uppercase char
		return (char)(resultCode + 'A');
	}
	
	
	/**
	 * Encrypt one lowercase character using the MVCipher with the given
	 * rotation key
	 * @param c		Character to encrypt
	 * @param key	Rotation key, number from 0 to 25
	 * @return ret 	Encrypted lowercase character, made by adding key to c and
	 * 				wrapping around the character.
	 */
	public char encryptLowerChar(char c, int key) {
		// alphabet code of c (0 is A, 25 is Z)
		int code = c - 'a';
		// add key to char and wrap around 26
		int resultCode = (code + key) % 26;
		
		// turn to lowercase char
		return (char)(resultCode + 'a');
	}
}