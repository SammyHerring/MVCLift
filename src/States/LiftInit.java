package States;

import LiftComponents.*;

public class LiftInit implements State {
	
	@Override
	public void doAction() {
		System.out.println("Lift Initial State");
		System.out.println("Doors Closed. Init floor.");
		
//		m.setCurrentFloor(m.minFloor());
//		m.setDoorOpen(false);
	}
}
