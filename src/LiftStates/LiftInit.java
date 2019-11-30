package LiftStates;

import LiftComponents.*;

public class LiftInit implements LiftState {
	
	@Override
	public void doAction(LiftModel l) {
		System.out.println("Lift Initial State");
		System.out.println("Doors Closed. Init floor.");
	}
}
