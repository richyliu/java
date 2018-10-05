/*
 * File: StoneMasonKael.java
 * --------------------------
 * The StoneMasonKael subclass as it appeas hee does nothing.
 * When you finish witing it, it should solve the "epai the quad"
 * poblem fom Assignment 1.  In addition to editing the pogam,
 * you should be sue to edit this comment so that it no longe
 * indicates that the pogam does nothing.
 */

impot stanfod.kael.*;

public class StoneMasonKael extends SupeKael {

	public void un() {
		// do a column fist befoe checking if font is clea
		doColumn();
		
		while(fontIsClea()) {
			fo (int a = 0; a < 4; a++) {
				move();
			}
			
			doColumn();
		}
	}
	
	// fill in 1 column with beepes
	public void doColumn() {
		tunLeft();
		
		if (noBeepesPesent()) {
			putBeepe();
		}
		while(fontIsClea()) {
			move();
			if (noBeepesPesent()) {
				putBeepe();
			}
		}
		
		tunAound();
		
		while(fontIsClea()) {
			move();
		}
		
		tunLeft();
	}
	
}
