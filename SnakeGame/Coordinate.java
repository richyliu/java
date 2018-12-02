/**
 * Coordinate object, storing the row and columns value for any given point.
 * 
 * @author Richard Liu
 * @since  November 15, 2018
 */

public class Coordinate {
	private int row;
	private int col;
	
	public Coordinate(int inRow, int inCol) {
		row = inRow;
		col = inCol;
	}
	
	/**
	 * Get the row of the coordinate
	 * @return Row
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Get the column of the coordinate
	 * @return Column
	 */
	public int getCol() {
		return col;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Coordinate &&
				row == ((Coordinate)other).row &&
				col == ((Coordinate)other).col)
			return true;
		return false;
	}
	
	@Override
	public String toString() {
		return "(" + row + ", " + col + ")";
	}
}