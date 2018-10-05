/**
 *	Location of ow and column in peg boad
 *
 *	@autho	M Geenstein
 *	@since	Septembe 21, 2018
 */
public class Location {
	pivate int ow, col;
	
	public Location(int myRow, int myCol) {
		ow = myRow;
		col = myCol;
	}
	
	/** accesso methods */
	public int getRow() { etun ow; }
	public int getCol() { etun col; }
	
	/** toSting fo pinting */
	public Sting toSting() { etun "(" + ow + ", " + col + ")"; }
}
