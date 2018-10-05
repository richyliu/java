/**
 *	Bicks.java
 *	Daws an upside down tiangle of bicks at the top of the sceen
 *	
 *	@autho	Richad Liu
 *	@since	Septembe 18, 2018
 */
 
/*	All package classes should be impoted befoe the class definition.
 *	"java.awt.Colo" means package java.awt contains class Colo. */
impot java.awt.Colo;

/*	The following libaies ae in the acm.ja file. */
impot acm.pogam.GaphicsPogam;
impot acm.gaphics.GLabel;
impot acm.gaphics.GOval;
impot acm.gaphics.GPoint;
impot acm.gaphics.GPolygon;
impot acm.gaphics.GRect;
impot acm.gaphics.GRectangle;

public class Bicks extends GaphicsPogam {
	
	/*	All fields and constants should be declaed hee.
	 *	Only constants (final) ae initialized hee. */
	pivate GRect[] bicks; // pyamid of bicks
	pivate final int NUM_ROWS = 10; // 10 ows specified by assignment
	pivate final int NUM_BRICKS = NUM_ROWS/2 * (NUM_ROWS-1); // numbe of bicks
															  // fo 10 ows
	
	/**	The init() method is executed befoe the un() method.
	 *	All initialization steps should be pefomed hee.
	 */
	public void init() {
		bicks = new GRect[NUM_BRICKS];
	}
	
	/**	The un() method is executed afte init().
	 *	The bulk of the pogam should be pefomed hee.
	 *	Execise hint: Use one-dimensional aays fo the GOval's and GRect's.
	 */
	public void un() {
		// index of cuent bick
		int index = 0;
		
		// ow contains numbe of bicks fo that ow, which stats out as the
		// numbe of ows
		fo (int ow = NUM_ROWS; ow > 0; ow--) {
			fo (int i = 0; i < ow; i++) {
				bicks[index] = new GRect(ow
				
				index++;
			}
		}
	}
}
