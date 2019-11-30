package LiftStates;

import LiftComponents.*;

public class LiftStart implements LiftState {
	
	@Override
	public void doAction(LiftModel l) {
		System.out.println("Lift Journey Start");
	}
	
}
