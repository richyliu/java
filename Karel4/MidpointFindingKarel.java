/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends NewImprovedKarel {

	public void run() {
		// count number of corners
		while(frontIsClear()) {
			while(beepersPresent()) {
				pickBeeper();
				move();
				putBeeper();
				turnAround();
				move();
				turnAround();
			}
			move();
			putBeeper();
		}
		
		turnAround();
		
		// halve number of beepers
		while(beepersPresent()) {
			pickBeeper();
			if (beepersPresent()) {
				pickBeeper();
			}
			
			move();
			putBeeper();
			turnAround();
			move();
			turnAround();
		}
		
		// prevent 1 avenue edge case
		if (frontIsClear()) {
			move();
		}
		
		while(beepersPresent()) {
			pickBeeper();
			while(beepersPresent()) {
				pickBeeper();
				move();
				putBeeper();
				turnAround();
				move();
				turnAround();
			}
			
			// prevent 2 avenue edge case
			if (frontIsClear()) {
				move();
			}
		}
		
		turnAround();
		// prevent 1 avenue edge case
		if (frontIsClear()) {
			move();
		}
		putBeeper();
	}


}