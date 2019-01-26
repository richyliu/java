import java.awt.Color;

/**
 *	StdDraw example
 *
 *	@author	Mr Greenstein
 *	@since	January 24, 2019
 */
public class StdDrawExample {

	private ExampleBoard board;
	
	public StdDrawExample() {
		board = new ExampleBoard();
		// set the size of the window
		StdDraw.setCanvasSize(500, 500);
		//StdDraw.setXscale(0, 1.2);		// (0, 1) default
		//StdDraw.setYscale(0, 1);		// (0, 1) default
		StdDraw.enableDoubleBuffering();
	}
	
	public static void main(String[] args) {
		StdDrawExample sde = new StdDrawExample();
		sde.run();
	}
	
	public void run() {
		while (true) {
			board.drawBoard();
			board.getMouse();
			StdDraw.show();
			StdDraw.pause(5);
		}
	}
}

/**
 *	A green board with gray background and red circle moving around.
 */
class ExampleBoard {
	
	private double x, y;		// x,y location of circle
	private boolean toRight, toTop;	// direction of circle
	
	private int red, green, blue;	// current color
	
	public ExampleBoard() {
		x = 0.05;
		y = 0.5;
		toRight = toTop = true;
		red = 255;
		green = blue = 0;
	}
	
	/**	Draws the board in its current state to the GUI.
	 */
	public void drawBoard() {
		// draw gray background
		StdDraw.setPenColor(new Color(150, 150, 150));
		StdDraw.filledRectangle(0.65, 0.5, 0.65, 0.5);
		// draw green board
		StdDraw.setPenColor(new Color(0, 120, 0));
		StdDraw.filledSquare(0.5, 0.5, 0.48);
		
		// move circle
		if (toRight) x += 0.01;
		else x -= 0.01;
		
		if (toTop) y += 0.01;
		else y -= 0.01;
		
		// if too far right, change direction
		if (x > 0.94) {
			toRight = false;
			x -= 0.02;
		}
		// if too far left, change direction
		if (x < 0.05) {
			toRight = true;
			x += 0.02;
		}
		
		// if too far up, change direction
		if (y > 0.94) {
			toTop = false;
			y -= 0.02;
		}
		// if too far down, change direction
		if (y < 0.05) {
			toTop = true;
			y += 0.02;
		}
		// draw red circle
		StdDraw.setPenColor(new Color(red, green, blue));
		StdDraw.filledCircle(x, y, 0.09);
	}
	
	/**	Change color if mouse is clicked 
	 *	alternates red/blue,
	 *	if mouse is right on top of circle, then purple
	 */
	public void getMouse() {
		if (StdDraw.isMousePressed()) {
			if (red == 255) {
				red = 0;
				blue = 255;
			}
			else {
				red = 255;
				blue = 0;
			}
			double xLoc = StdDraw.mouseX();
			double yLoc = StdDraw.mouseY();
			if (Math.abs(x - xLoc) < 0.05 && Math.abs(y - yLoc) < 0.05) {
				red = 200;
				blue = 200;
			}
		}
	}

}