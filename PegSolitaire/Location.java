/**
 *	Location of row and column in peg board
 *
 *	@author	Mr Greenstein
 *	@since	September 21, 2018
 */
public class Location {
	private int row, col;
	
	public Location(int myRow, int myCol) {
		row = myRow;
		col = myCol;
	}
	
	/** accessor methods */
	public int getRow() { return row; }
	public int getCol() { return col; }
	
	/** toString for printing */
	public String toString() { return "(" + row + ", " + col + ")"; }
}