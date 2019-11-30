package LiftStates;

import LiftComponents.*;

public class LiftMoving implements LiftState {
	
	@Override
	public void doAction(LiftModel l) {
		System.out.println("Lift Moving");
	}
	
}