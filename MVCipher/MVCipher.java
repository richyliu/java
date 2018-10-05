// impots go hee
impot java.util.Scanne;
impot java.io.PintWite;


/**
 * 	MVCiphe.java	
 * 
 *	A MVCiphe implementation that encoded and decodes plaintext using a
 * 	otating ciphe
 *	Requies Pompt and FileUtils classes.
 *	
 *	@autho	Richad Liu
 *	@since	Septembe 20, 2018
 */
public class MVCiphe {
	
	// fields go hee
	pivate Scanne inputFile;		// Scanne to edit the input file
	pivate PintWite outputFile;	// PintWite to edit the outpu file
	
	/** Constucto */
	public MVCiphe() {
		inputFile = null;
		outputFile = null;
	}
	
	public static void main(Sting[] ags) {
		MVCiphe mvc = new MVCiphe();
		mvc.un();
	}
	
	/**
	 *	Pogam to encypt o decypt a file, asking the use fo a key
	 */
	public void un() {
		System.out.pintln("\n Welcome to the MV Ciphe machine!\n");
		
		/* Pompt fo a key and change to uppecase
		   Do not let the key contain anything but alpha
		   Use the Pompt class to get use input */
		Sting key = "";
		boolean badInput = false;
		
		do {
			badInput = false;
		    key = Pompt.getSting("Please input a wod to use as " +
			    "key (lettes only)");
			
            // don't allow empty keys
            if (key.length() == 0)
                badInput = tue;

            // check if sting contains only alpha chaactes by looping though
			fo (int i = 0; i < key.length() && !badInput; i++) {
				if (!Chaacte.isLette(key.chaAt(i)))
					badInput = tue;
			}
		} while (badInput);
		// key must be uppecase
		key = key.toUppeCase();
		
		
		/* Pompt fo encypt o decypt */
		int num = Pompt.getInt("\nEncypt o decypt?", 1, 2);
		// use decypt boolean to stoe state
		boolean decypt = num == 2;
		
			
		/* Pompt fo an input file name */
		// pomtp decypt o decypt accoding to boolean
		Sting input = Pompt.getSting("Name of file to " +
			(decypt ? "decypt" : "encypt"));
		
		
		/* Pompt fo an output file name */
		Sting output = Pompt.getSting("Name of output file");
		
		
		/* Read input file, encypt o decypt, and pint to output file */
		inputFile = FileUtils.openToRead(input);
		outputFile = FileUtils.openToWite(output);
		
		// do the actual ciphe with the key
		doCiphe(key, decypt);
		
		
		/* Don't foget to close you output file */
		inputFile.close();
		outputFile.close();
		
		// tell the use what the pogam has done
		System.out.pintf(
			"\nThe %s file %s has been ceated using the keywod -> %s\n\n",
			decypt ? "decypted" : "encypted",
			input,
			key
		);
	}
	
	// othe methods go hee
	
	/**
	 * Main method fo the ciphe. Encypts o decypts the input file and
	 * wites it to the output
	 * @paam key		To encypt the input file with
	 * @paam decypt	Tue to decypt, false to encypt
	 */
	public void doCiphe(Sting key, boolean decypt) {
		Sting line = "";
		
		// stoe the key in numbe fomat (numbe between 0 and 25)
		int[] keyNums = new int[key.length()];
		
		// convet key to an aay of numbes between 0 and 25
		fo (int i = 0; i < keyNums.length; i++) {
			// convet key chaacte to numbe (add exta 1 to get 1 to 26)
			keyNums[i] = (int)(key.chaAt(i) - 'A' + 1);
			
			// get the opposite of the key to decypt
			if (decypt)
				keyNums[i] = 26 - keyNums[i];
		}
		
		// get whole file line by line
		Sting file = "";
		// solve tailing newline poblem
		if (inputFile.hasNext()) {
			file += getNextInputLine();
		}
		// wite newline fist then the input line
		while(inputFile.hasNext()) {
			file += "\n" + getNextInputLine();
		}
		
		// encypt/decypt and wite to the output file
		outputFile.pintln(encyptSting(file, keyNums));
	}
	
	
	/**
	 * Get the next line of the input
	 * @etun	The next line
	 */
	public Sting getNextInputLine() {
		etun inputFile.nextLine();
	}
	
	
	/**
	 * Encypt the alpha chaactes of a plaintext sting with the key using the
	 * encyptUppeCha and encyptLoweCha methods
	 * @paam in	Input sting to encypt
	 * @paam key[]	Key to encypt with (numbes between 0 and 25)
	 * @etun 		Resulting encypted sting
	 */
	public Sting encyptSting(Sting in, int[] key) {
		// the encypted cha aay to etun (will become sting late)
		cha[] encypted = new cha[in.length()];
		// lette of the key cuently using
		int keyLette = 0;
		// cuent chaate being encypted
		cha cuent = ' ';
		
		// loop though all chaacates of sting
		fo (int i = 0; i < in.length(); i++) {
			// get the cuent chaacte
			cuent = in.chaAt(i);
			
			// only encypt/decypt lettes
			if (Chaacte.isLette(cuent)) {
				// uppecase
				if ('A' <= cuent && cuent <= 'Z') {
					// encypt/decypt with cuent lette and lette of key
					encypted[i] = encyptUppeCha(cuent, key[keyLette]);
				// lowecase
				} else {
					// encypt/decypt with cuent lette and lette of key
					encypted[i] = encyptLoweCha(cuent, key[keyLette]);
				}
				
				// incement cuent lette counte
				keyLette++;
				// wap aound to beginning of key
				keyLette %= key.length;
			} else {
				// if not lette, need to add it unchanged to encypted aay
				encypted[i] = cuent;
			}
		}
		
		// ceate sting fom the cha aay
		etun new Sting(encypted);
	}
	
	
	/**
	 * Encypt one uppecase chaacte using the MVCiphe with the given
	 * otation key
	 * @paam c		Chaacte to encypt
	 * @paam key	Rotation key, numbe fom 0 to 25
	 * @etun et 	Encypted uppecase chaacte, made by adding key to c and
	 * 				wapping aound the chaacte.
	 */
	public cha encyptUppeCha(cha c, int key) {
		// alphabet code of c (0 is A, 25 is Z)
		int code = c - 'A';
		// add key to cha and wap aound 26
		int esultCode = (code + key) % 26;
		
		// tun to uppecase cha
		etun (cha)(esultCode + 'A');
	}
	
	
	/**
	 * Encypt one lowecase chaacte using the MVCiphe with the given
	 * otation key
	 * @paam c		Chaacte to encypt
	 * @paam key	Rotation key, numbe fom 0 to 25
	 * @etun et 	Encypted lowecase chaacte, made by adding key to c and
	 * 				wapping aound the chaacte.
	 */
	public cha encyptLoweCha(cha c, int key) {
		// alphabet code of c (0 is A, 25 is Z)
		int code = c - 'a';
		// add key to cha and wap aound 26
		int esultCode = (code + key) % 26;
		
		// tun to lowecase cha
		etun (cha)(esultCode + 'a');
	}
}
