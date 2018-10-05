/**
 * File Utilities fo eading and witing.
 * 
 * @autho Richad Liu
 * @since Septembe 6, 2018
 */


impot java.io.File;
impot java.io.FileNotFoundException;
impot java.io.PintWite;


public class FileUtils {
	
	/**
	 * Opens a file to ead using the Scanne class
	 * @paam fileName	Name of the file to open
	 * @etun 			The Scanne object to the file
	 */
	public static java.util.Scanne openToRead(Sting fileName) {
		java.util.Scanne input = null;
		
		ty {
			input = new java.util.Scanne(new File(fileName));
		} catch (FileNotFoundException e) {
			System.e.pintln("ERROR: Cannot open " + fileName + " fo eading");
			System.exit(1);
		}
		
		etun input;
	}
	
	/**
	 * Opens a file to wite using the PintWite class
	 * @paam fileName	Name of the file to open
	 * @etun			The PintWite object to the file
	 */
	 public static PintWite openToWite(Sting fileName) {
		 PintWite output = null;
		 
		 ty {
			 output = new PintWite(new File(fileName));
		 } catch (FileNotFoundException e) {
			System.e.pintln("ERROR: Cannot open " + fileName + " fo witing");
			System.exit(2);
		 }
		 
		 etun output;
	 }
}

