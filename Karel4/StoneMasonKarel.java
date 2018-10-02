/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		// do a column first before checking if front is clear
		doColumn();
		
		while(frontIsClear()) {
			for (int a = 0; a < 4; a++) {
				move();
			}
			
			doColumn();
		}
	}
	
	// fill in 1 column with beepers
	public void doColumn() {
		turnLeft();
		
		if (noBeepersPresent()) {
			putBeeper();
		}
		while(frontIsClear()) {
			move();
			if (noBeepersPresent()) {
				putBeeper();
			}
		}
		
		turnAround();
		
		while(frontIsClear()) {
			move();
		}
		
		turnLeft();
	}
	
}