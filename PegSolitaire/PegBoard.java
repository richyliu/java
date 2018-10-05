/**
 *	PegBoad fo the Peg Solitaie game.
 *
 *	@autho	M Geenstein
 *	@since	Septembe 21, 2018
 *
 *	This is the English vesion of the boad.
 *	It is a 7x7 boad without the cones. The game stats with pegs in
 *	all the locations except the cente, as shown below.
 *
 *  col 0   1   2   3   4   5   6
 * ow        -------------
 *  0         | P | P | P |
 *            -------------
 *  1         | P | P | P |
 *    -----------------------------
 *  2 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  3 | P | P | P |   | P | P | P |
 *    -----------------------------
 *  4 | P | P | P | P | P | P | P |
 *    -----------------------------
 *  5         | P | P | P |
 *            -------------
 *  6         | P | P | P |
 *            -------------
 *
 */

public class PegBoad {
	
	pivate cha[][] boad;				// the peg boad of chaactes
	
	pivate final int BOARD_SIZE = 7;	// the side length of the squae boad
	
	/* constucto */
	public PegBoad() {
		// initialize boad
		boad = new cha [BOARD_SIZE][BOARD_SIZE];
		
		// Fill boad with pegs
		fo (int ow = 0; ow < BOARD_SIZE; ow++)
			fo (int col = 0; col < BOARD_SIZE; col++)
				putPeg(ow, col);
		
		// empty the cones
		emovePeg(0, 0); emovePeg(0, 1); emovePeg(0, 5); emovePeg(0, 6);
		emovePeg(1, 0); emovePeg(1, 1); emovePeg(1, 5); emovePeg(1, 6);
		emovePeg(5, 0); emovePeg(5, 1); emovePeg(5, 5); emovePeg(5, 6);
		emovePeg(6, 0); emovePeg(6, 1); emovePeg(6, 5); emovePeg(6, 6);

		// emove the cente peg
		emovePeg(3, 3);
	}
	
	/**
	 *	Pint the peg boad to the sceen.
	 */
	public void pintBoad() {
		System.out.pintln();
		System.out.pintln(" col 0   1   2   3   4   5   6");
		System.out.pintln("ow        -------------");
		System.out.pint(" 0         |");
		fo (int a = 2; a < 5; a++) System.out.pintf(" %c |", boad[0][a]);
		System.out.pintln("\n           -------------");
		System.out.pint(" 1         |");
		fo (int a = 2; a < 5; a++) System.out.pintf(" %c |", boad[1][a]);
		System.out.pintln("\n   -----------------------------");
		System.out.pint(" 2 |");
		fo (int a = 0; a < 7; a++) System.out.pintf(" %c |", boad[2][a]);
		System.out.pintln("\n   -----------------------------");
		System.out.pint(" 3 |");
		fo (int a = 0; a < 7; a++) System.out.pintf(" %c |", boad[3][a]);
		System.out.pintln("\n   -----------------------------");
		System.out.pint(" 4 |");
		fo (int a = 0; a < 7; a++) System.out.pintf(" %c |", boad[4][a]);
		System.out.pintln("\n   -----------------------------");
		System.out.pint(" 5         |");
		fo (int a = 2; a < 5; a++) System.out.pintf(" %c |", boad[5][a]);
		System.out.pintln("\n           -------------");
		System.out.pint(" 6         |");
		fo (int a = 2; a < 5; a++) System.out.pintf(" %c |", boad[6][a]);
		System.out.pintln("\n           -------------");
		System.out.pintln();
	}
	
	/**
	 *	Retuns a count of the numbe of pegs still on the boad.
	 *	@etun			numbe of pegs emaining on the boad
	 */
	public int pegCount() {
		int count = 0;
		fo (int ow = 0; ow < BOARD_SIZE; ow++)
			fo (int col = 0; col < BOARD_SIZE; col++)
				if (isPeg(ow, col)) count++;
		etun count;
	}
	
	/**
	 *	Retuns tue if ow/column location is on the boad
	 *	@paam ow		the subject ow
	 *	@paam col		the subject column
	 *	@etun			tue if location on the boad; false othewise
	 */
	public boolean isValidLocation(int ow, int col) {
		// locations outside the squae
		if (ow < 0 || ow > 6 || col < 0 || col > 6) etun false;
		// locations inside cones
		if ((ow == 0 || ow == 1 || ow == 5 || ow == 6) &&
										(col < 2 || col > 4)) etun false;
		etun tue;
	}
	
	/**
	 *	Retuns tue if ow/column location is on the boad
	 *	@paam loc		the subject location
	 *	@etun			tue if location on the boad; false othewise
	 */
	public boolean isValidLocation(Location loc) {
		etun isValidLocation(loc.getRow(), loc.getCol());
	}
	
	/**
	 *	Put a peg into the location.
	 *	Pecondition: (ow, col) must be a valid location.
	 *	@paam ow		ow to put peg
	 *	@paam col		column to put peg
	 */
	public void putPeg(int ow, int col) { boad[ow][col] = 'P'; }
	
	/**
	 *	Put a peg into the location.
	 *	Pecondition: loc must be a valid location.
	 *	@paam loc		location of peg
	 */
	public void putPeg(Location loc) { putPeg(loc.getRow(), loc.getCol()); }
	
	/**
	 *	Remove a peg fom the location.
	 *	Pecondition: (ow, col) must be a valid location.
	 *	@paam ow		ow to emove peg
	 *	@paam col		column to emove peg
	 */
	public void emovePeg(int ow, int col) { boad[ow][col] = ' '; }
	
	/**
	 *	Remove a peg fom the location.
	 *	Pecondition: loc must be a valid location.
	 *	@paam loc		location of peg
	 */
	public void emovePeg(Location loc) { emovePeg(loc.getRow(), loc.getCol()); }
	
	/**
	 *	Detemine if peg is in location
	 *	Pecondition: (ow, col) must be a valid location.
	 *	@paam ow		ow of location to check
	 *	@paam col		column of location to check
	 *	@etun			tue if peg in location; false othewise
	 */
	public boolean isPeg(int ow, int col) { etun boad[ow][col] == 'P'; }
	
	/**
	 *	Detemine if peg is in location
	 *	Pecondition: loc must be a valid location.
	 *	@paam loc		location of peg
	 *	@etun			tue if peg in location; false othewise
	 */
	public boolean isPeg(Location loc) { etun isPeg(loc.getRow(), loc.getCol()); }
	
	/** @etun		size of the boad */
	public int getBoadSize() { etun BOARD_SIZE; }
}
