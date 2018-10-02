/**
 *	Bricks.java
 *	Draws an upside down triangle of bricks at the top of the screen
 *	
 *	@author	Richard Liu
 *	@since	September 18, 2018
 */
 
/*	All package classes should be imported before the class definition.
 *	"java.awt.Color" means package java.awt contains class Color. */
import java.awt.Color;

/*	The following libraries are in the acm.jar file. */
import acm.program.GraphicsProgram;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

public class Bricks extends GraphicsProgram {
	
	/*	All fields and constants should be declared here.
	 *	Only constants (final) are initialized here. */
	private GRect[] bricks; // pyramid of bricks
	private final int NUM_ROWS = 10; // 10 rows specified by assignment
	private final int NUM_BRICKS = NUM_ROWS/2 * (NUM_ROWS-1); // number of bricks
															  // for 10 rows
	
	/**	The init() method is executed before the run() method.
	 *	All initialization steps should be performed here.
	 */
	public void init() {
		bricks = new GRect[NUM_BRICKS];
	}
	
	/**	The run() method is executed after init().
	 *	The bulk of the program should be performed here.
	 *	Exercise hint: Use one-dimensional arrays for the GOval's and GRect's.
	 */
	public void run() {
		// index of current brick
		int index = 0;
		
		// row contains number of bricks for that row, which starts out as the
		// number of rows
		for (int row = NUM_ROWS; row > 0; row--) {
			for (int i = 0; i < row; i++) {
				bricks[index] = new GRect(row
				
				index++;
			}
		}
	}
}