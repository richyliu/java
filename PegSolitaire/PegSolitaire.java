import java.util.ArrayList;

/**
 *	PegSolitaire game.
 *	A game of minimizing remaining pegs by removing them by jumping
 *
 *	@author	Richard Liu
 *	@since	October 1, 2018
 *
 *	<detailed description goes here>
 */
public class PegSolitaire {
	
	// fields
	PegBoard board;
		
	/** constructor */
	public PegSolitaire() { }
	
	
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
		ArrayList<Location> locations = new ArrayList<Location>();
		Location upPeg = new Location(peg.getRow() - 2, peg.getCol());
		Location leftPeg = new Location(peg.getRow(), peg.getCol() - 2);
		Location bottomPeg = new Location(peg.getRow() + 2, peg.getCol());
		Location rightPeg = new Location(peg.getRow(), peg.getCol() + 2);
		
		if (board.isValidLocation(upPeg) && !board.isPeg(upPeg))
			locations.add(upPeg);
		if (board.isValidLocation(leftPeg) && !board.isPeg(leftPeg))
			locations.add(leftPeg);
		if (board.isValidLocation(bottomPeg) && !board.isPeg(bottomPeg))
			locations.add(bottomPeg);
		if (board.isValidLocation(rightPeg) && !board.isPeg(rightPeg))
			locations.add(rightPeg);
		
		return locations;
	}
	
	
	/**
	 * Checks if the peg location has peg and has valid jumps
	 * @param	peg		Location of the peg
	 * @return			True if peg has valid jumps
	 */
	public boolean isValidJumperPeg(Location peg) {
		if (board.isValidLocation(peg) && board.isPeg(peg)) {
			
		}
		return false;
	}
	
	
	/**
	 * Prompt the user for the jumper peg
	 * @return			Valid jumper peg the user entered
	 */
	public Location getJumperPeg() {
		return null;
	}
}