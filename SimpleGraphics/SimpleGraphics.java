/**
 *	SimpleGraphics.java
 *
 *	To compile:	javac -cp .:acm.jar SimpleGraphics.java
 *	To execute:	java -cp .:acm.jar SimpleGraphics
 *
 *	@author	Your name
 *	@since	Today's date
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

public class SimpleGraphics extends GraphicsProgram {
	
	/*	All fields and constants should be declared here.
	 *	Only constants (final) are initialized here. */
	private GOval circle;
	private final double RADIUS = 25;
	
	private GRect square;
	private final double SIDE = 40;
	
	/**	The init() method is executed before the run() method.
	 *	All initialization steps should be performed here.
	 */
	public void init() {

	}
	
	/**	The run() method is executed after init().
	 *	The bulk of the program should be performed here.
	 *	Exercise hint: Use one-dimensional arrays for the GOval's and GRect's.
	 */
	public void run() {
		circle = new GOval(100, 100, RADIUS * 2, RADIUS * 2);
		circle.setFilled(true);
		circle.setFillColor(Color.RED);
		
		square = new GRect(300, 100, SIDE, SIDE);
		square.setFilled(true);
		square.setFillColor(Color.BLUE);
		
		add(circle);
		add(square);
	}
}