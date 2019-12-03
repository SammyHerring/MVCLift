package States;

import LiftComponents.*;

public class LiftEnd implements LiftState {
	
	@Override
	public void doAction(LiftModel m) {
		System.out.println("Lift Journey Ended");
		System.out.println("Release passengers. Wait. Close doors. End Floor.");
	}
	
}
