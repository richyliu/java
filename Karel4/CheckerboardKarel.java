/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment Karel4.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		while(noBeepersPresent()) {
			putBeeper();
			
			if (facingEast()) {
				if (frontIsBlocked()) {
					if (leftIsBlocked()) {
						return;
					}
				}
			} else {
				if (frontIsBlocked()) {
					if (rightIsBlocked()) {
						return;
					}
				}
			}
			
			moveCheck();
			
			if (facingEast()) {
				if (frontIsBlocked()) {
					if (leftIsBlocked()) {
						return;
					}
				}
			} else {
				if (frontIsBlocked()) {
					if (rightIsBlocked()) {
						return;
					}
				}
			}
			
			moveCheck();
		}
	}
	
	// move & check if clear, turn based on east clear or west clear
	public void moveCheck() {
		if (frontIsClear()) {
			move();
		} else {
			if (facingEast()) {
				turnLeft();
				move();
				turnLeft();	
			} else {
				turnRight();
				move();
				turnRight();
			}
		}
	}

}