/**
 * File Utilities for reading and writing.
 * 
 * @author Richard Liu
 * @since September 6, 2018
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class FileUtils {
	
	/**
	 * Opens a file to read using the Scanner class
	 * @param fileName	Name of the file to open
	 * @return 			The Scanner object to the file
	 */
	public static java.util.Scanner openToRead(String fileName) {
		java.util.Scanner input = null;
		
		try {
			input = new java.util.Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Cannot open " + fileName + " for reading");
			System.exit(1);
		}
		
		return input;
	}
	
	/**
	 * Opens a file to write using the PrintWriter class
	 * @param fileName	Name of the file to open
	 * @return			The PrintWriter object to the file
	 */
	 public static PrintWriter openToWrite(String fileName) {
		 PrintWriter output = null;
		 
		 try {
			 output = new PrintWriter(new File(fileName));
		 } catch (FileNotFoundException e) {
			System.err.println("ERROR: Cannot open " + fileName + " for writing");
			System.exit(2);
		 }
		 
		 return output;
	 }
}
