package States;

import LiftComponents.*;

public class LiftEnd implements State {
	
	@Override
	public void doAction() {
		System.out.println("Lift Journey Ended");
		System.out.println("Release passengers. Wait. Close doors. End Floor.");
	}
	
}
