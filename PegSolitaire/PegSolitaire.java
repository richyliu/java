import java.util.ArrayList;

/**
 *	PegSolitaire game.
 *	A game of minimizing remaining pegs by removing them by jumping
 *
 *	@author	Richard Liu
 *	@since	October 1, 2018
 *
 *	The game starts out with 32 pegs on a plus-shaped board with a hole in the
 * 	middle. The user selects a peg to jump over another peg and into an empty
 * 	spot. The peg that was jumped over is removed. The game continues until
 * 	there are no more pegs to jump.
 */
public class PegSolitaire {
	
	// fields
	// board stores the pegs
	PegBoard board;
	// exit the game if true
	boolean exit;
		
	/** constructor */
	public PegSolitaire() {
		board = null;
		exit = false;
	}
	
	
	public static void main(String[] args) {
		PegSolitaire ps = new PegSolitaire();
		ps.run();
	}
	
	
	/** methods */
	
	/**
	 * Main run method, runs the bulk of the program
	 */
	public void run() {
		board = new PegBoard();
		
		printIntroduction();
		
		while(!exit) {
			board.printBoard();
			Location peg = getJumperPeg();
			
			// peg might be null if user exits
			if (peg != null)
				jumpChoosePeg(peg);

            // check if there are any remaining moves. if not, exit the game
            // start by assuming no valid pegs
            boolean noValid = true;

            for (int row = 0; row < 7 && noValid; row++)
                for (int col = 0; col < 7 && noValid; col++)
                    // once a valid peg is found, exit the loop
                    if (isValidJumperPeg(row, col))
                        noValid = false;

            // indeed no valid pegs, exit the game
            if (noValid)
                exit = true;
		}

        // print the board once more
        board.printBoard();
		
		// thank the user for playing
		System.out.println("\nYour score: " + board.pegCount());
		System.out.println("\n\nThanks for playing Peg Solitaire!\n");
	}
	
	
	/**
	 *	Print the introduction to the game
	 */
	public void printIntroduction() {
		System.out.println("  _____              _____       _ _ _        _ ");
		System.out.println(" |  __ \\            / ____|     | (_) |      (_)");
		System.out.println(" | |__) |__  __ _  | (___   ___ | |_| |_ __ _ _ _ __ ___ ");
		System.out.println(" |  ___/ _ \\/ _` |  \\___ \\ / _ \\| | | __/ _` | | '__/ _ \\");
		System.out.println(" | |  |  __/ (_| |  ____) | (_) | | | || (_| | | | |  __/");
		System.out.println(" |_|   \\___|\\__, | |_____/ \\___/|_|_|\\__\\__,_|_|_|  \\___|");
		System.out.println("             __/ |");
		System.out.println("            |___/");
		System.out.println("\nWelcome to Peg Solitaire!!!\n");
		System.out.println("Peg Solitaire is a game for one player. The " +
							"goal is to remove all\n" +
							"but one of the 32 pegs from a special board. " +
							"The board is a 7x7\n" +
							"grid with the corners cut out (shown below)." +
							" Pegs are placed in all");
		System.out.println("grid locations except the center which is " +
							"left empty. Pegs jump\n" +
							"over other pegs either horizontally or " +
							"vertically into empty\n" +
							"locations and the jumped peg is removed. Play " +
							"continues until\n" +
							"there are no more jumps possible, or there " +
							"is one peg remaining.");
		System.out.println("\nLet's play!!!\n");
	}
	
	
	/**
	 * Get the valid moves for the peg
	 * @param	peg		Location of the peg
	 * @return			Valid moves (locations) for the peg
	 */
	public ArrayList<Location> getPegMoves(Location peg) {
		int x = peg.getRow();
		int y = peg.getCol();
		
		ArrayList<Location> locations = new ArrayList<Location>();
		
		// check in each direction there is a valid peg and empty peg 2 spaces
		// away
		if (board.isValidLocation(x-2, y) && !board.isPeg(x-2, y) &&
			board.isPeg(x-1, y))
			locations.add(new Location(x-2, y));
		if (board.isValidLocation(x+2, y) && !board.isPeg(x+2, y) &&
			board.isPeg(x+1, y))
			locations.add(new Location(x+2, y));
		if (board.isValidLocation(x, y-2) && !board.isPeg(x, y-2) &&
			board.isPeg(x, y-1))
			locations.add(new Location(x, y-2));
		if (board.isValidLocation(x, y+2) && !board.isPeg(x, y+2) &&
			board.isPeg(x, y+1))
			locations.add(new Location(x, y+2));
				
		return locations;
	}
	
	
	/**
	 * Checks if the peg location has peg and has valid jumps
	 * @param	peg		Location of the peg
	 * @return			True if peg has valid jumps
	 */
	public boolean isValidJumperPeg(Location peg) {
		return board.isValidLocation(peg) &&
			isPeg(peg) &&
			getPegMoves(peg).size() > 0;
	}
	
	
	/**
	 * Checks if the peg location has peg and has valid jumps
	 * @param x		X location of the peg
	 * @param y		Y location of the peg
	 * @return 		True if peg has valid jumps
	 */
	public boolean isValidJumperPeg(int x, int y) {
		return isValidJumperPeg(new Location(x, y));
	}
	
	
	/**
	 * Prompt the user for the jumper peg
	 * @return			Valid jumper peg the user entered
	 */
	public Location getJumperPeg() {
		Location validPeg = null;
		String userStr = "";
		boolean badInput = true;
		
		do {
			// assume user input is bad
			badInput = true;
			
			// prompt the user for input string
			userStr = Prompt.getString("Jumper peg - row col (ex. 3 5, q=quit)");
			
			// exit if user types "q"
			if (userStr.equals("q")) {
				badInput = false;
				exit = true;
			} else {
				// split string by space
				String[] parts = userStr.split(" +");
				int num1 = 0;
				int num2 = 0;
				
				if (parts.length == 2) {
					try {
						// try to parse the numbers
						num1 = Integer.parseInt(parts[0]);
						num2 = Integer.parseInt(parts[1]);
						
						// create peg
						validPeg = new Location(num1, num2);
						
						// check if peg is valid
						if (!isValidJumperPeg(validPeg)) {
							// not valid, ask again
							System.out.println("Invalid jumper peg: " + validPeg);
							validPeg = null;
						} else {
							badInput = false;
						}
					// no need to prompt user unnecessarily if they didn't type
					// a number
					} catch (NumberFormatException e) {}
				}
			}
		} while(badInput);
		
		return validPeg;
	}
	
	
	/**
	 * Jumps the selected peg, asking the user to choose if there are more than
	 * one jump locations
	 * Precondition: peg must be a valid jumper peg
	 * @param	peg		Location of the peg to jump from
	 */
	public void jumpChoosePeg(Location peg) {
		ArrayList<Location> jumpLocs = getPegMoves(peg);
		
		if (jumpLocs.size() == 1) {
			// only 1 location, jump to it
			jumpPeg(peg, jumpLocs.get(0));
		} else {
			// multiple locations, allow user to choose
			System.out.println("Possible peg jump locations: ");
			for (int i = 0; i < jumpLocs.size(); i++) {
				System.out.println(" " + i + " " + jumpLocs.get(i));
			}
			// prompt user to choose
			int loc = Prompt.getInt("Enter location", 0, jumpLocs.size()-1);
			jumpPeg(peg, jumpLocs.get(loc));
		}
	}
	
	
	/**
	 * Jump the actual peg, removing the peg in between
	 * Precondition: from and to are valid peg locations
	 * Precondition: from has peg
	 * Precondition: to does not have peg
	 * @param from	Where to jump from
	 * @param to	Where to jump to
	 */
	public void jumpPeg(Location from, Location to) {
		// get middle peg by averaging from and to locations
		int dx = (from.getRow() + to.getRow())/2;
		int dy = (from.getCol() + to.getCol())/2;
		
		removePeg(from);
		putPeg(to);
		board.removePeg(dx, dy);
	}
	
	
	/**
	 *	Put a peg into the location on the board
	 *	Precondition: loc must be a valid location.
	 *	@param loc		location of peg
	 */
	public void putPeg(Location loc) {
		board.putPeg(loc.getRow(), loc.getCol());
	}
	
	
	/**
	 *	Remove a peg from the location.
	 *	Precondition: loc must be a valid location.
	 *	@param loc		location of peg
	 */
	public void removePeg(Location loc) {
		board.removePeg(loc.getRow(), loc.getCol());
	}
	
	
	/**
	 *	Determine if peg is in location
	 *	Precondition: loc must be a valid location.
	 *	@param loc		location of peg
	 *	@return			true if peg in location; false otherwise
	 */
	public boolean isPeg(Location loc) {
		return board.isPeg(loc.getRow(), loc.getCol());
	}
	
	
	/**
	 *	Returns true if row/column location is on the board
	 *	@param loc		the subject location
	 *	@return			true if location on the board; false otherwise
	 */
	public boolean isValidLocation(Location loc) {
		return board.isValidLocation(loc.getRow(), loc.getCol());
	}
}