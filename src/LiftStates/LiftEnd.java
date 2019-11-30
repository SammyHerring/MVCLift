package LiftStates;

import LiftComponents.*;

public class LiftEnd implements LiftState {
	
	@Override
	public void doAction(LiftModel l) {
		System.out.println("Lift Journey Ended");
		System.out.println("Release passengers. Wait. Close doors. End Floor.");
	}
	
}
