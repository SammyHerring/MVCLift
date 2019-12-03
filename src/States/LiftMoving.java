package States;

import LiftComponents.*;

public class LiftMoving implements LiftState {
	
	@Override
	public void doAction(LiftModel m) {
		System.out.println("Lift Moving");
	}
	
}