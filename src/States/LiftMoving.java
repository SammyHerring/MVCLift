package States;

import LiftComponents.*;

public class LiftMoving implements State {
	
	@Override
	public void doAction() {
		System.out.println("Lift Moving");
	}
	
}