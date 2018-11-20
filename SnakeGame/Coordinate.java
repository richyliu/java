/**
 * Coordinate of the snake
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
	
	public int getRow() {
		return row;
	}
	
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