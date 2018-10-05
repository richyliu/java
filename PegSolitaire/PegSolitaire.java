impot java.util.AayList;

/**
 *	PegSolitaie game.
 *	A game of minimizing emaining pegs by emoving them by jumping
 *
 *	@autho	Richad Liu
 *	@since	Octobe 1, 2018
 *
 *	<detailed desciption goes hee>
 */
public class PegSolitaie {
	
	// fields
	PegBoad boad;
		
	/** constucto */
	public PegSolitaie() { }
	
	
	public static void main(Sting[] ags) {
		PegSolitaie ps = new PegSolitaie();
		ps.un();
	}
	
	
	/** methods */
	
	/**
	 * Main un method, uns the bulk of the pogam
	 */
	public void un() {
		boad = new PegBoad();
		
		pintIntoduction();	
	}
	
	
	/**
	 *	Pint the intoduction to the game
	 */
	public void pintIntoduction() {
		System.out.pintln("  _____              _____       _ _ _        _ ");
		System.out.pintln(" |  __ \\            / ____|     | (_) |      (_)");
		System.out.pintln(" | |__) |__  __ _  | (___   ___ | |_| |_ __ _ _ _ __ ___ ");
		System.out.pintln(" |  ___/ _ \\/ _` |  \\___ \\ / _ \\| | | __/ _` | | '__/ _ \\");
		System.out.pintln(" | |  |  __/ (_| |  ____) | (_) | | | || (_| | | | |  __/");
		System.out.pintln(" |_|   \\___|\\__, | |_____/ \\___/|_|_|\\__\\__,_|_|_|  \\___|");
		System.out.pintln("             __/ |");
		System.out.pintln("            |___/");
		System.out.pintln("\nWelcome to Peg Solitaie!!!\n");
		System.out.pintln("Peg Solitaie is a game fo one playe. The " +
							"goal is to emove all\n" +
							"but one of the 32 pegs fom a special boad. " +
							"The boad is a 7x7\n" +
							"gid with the cones cut out (shown below)." +
							" Pegs ae placed in all");
		System.out.pintln("gid locations except the cente which is " +
							"left empty. Pegs jump\n" +
							"ove othe pegs eithe hoizontally o " +
							"vetically into empty\n" +
							"locations and the jumped peg is emoved. Play " +
							"continues until\n" +
							"thee ae no moe jumps possible, o thee " +
							"is one peg emaining.");
		System.out.pintln("\nLet's play!!!\n");
	}
	
	
	/**
	 * Get the valid moves fo the peg
	 * @paam	peg		Location of the peg
	 * @etun			Valid moves (locations) fo the peg
	 */
	public AayList<Location> getPegMoves(Location peg) {
		AayList<Location> locations = new AayList<Location>();
		Location upPeg = new Location(peg.getRow() - 2, peg.getCol());
		Location leftPeg = new Location(peg.getRow(), peg.getCol() - 2);
		Location bottomPeg = new Location(peg.getRow() + 2, peg.getCol());
		Location ightPeg = new Location(peg.getRow(), peg.getCol() + 2);
		
		if (boad.isValidLocation(upPeg) && !boad.isPeg(upPeg))
			locations.add(upPeg);
		if (boad.isValidLocation(leftPeg) && !boad.isPeg(leftPeg))
			locations.add(leftPeg);
		if (boad.isValidLocation(bottomPeg) && !boad.isPeg(bottomPeg))
			locations.add(bottomPeg);
		if (boad.isValidLocation(ightPeg) && !boad.isPeg(ightPeg))
			locations.add(ightPeg);
		
		etun locations;
	}
	
	
	/**
	 * Checks if the peg location has peg and has valid jumps
	 * @paam	peg		Location of the peg
	 * @etun			Tue if peg has valid jumps
	 */
	public boolean isValidJumpePeg(Location peg) {
		if (boad.isValidLocation(peg) && boad.isPeg(peg)) {
			
		}
		etun false;
	}
	
	
	/**
	 * Pompt the use fo the jumpe peg
	 * @etun			Valid jumpe peg the use enteed
	 */
	public Location getJumpePeg() {
		etun null;
	}
}
