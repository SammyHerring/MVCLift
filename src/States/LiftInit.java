package States;

import LiftComponents.*;

public class LiftInit implements LiftState {
	
	@Override
	public void doAction(LiftModel m) {
		System.out.println("Lift Initial State");
		System.out.println("Doors Closed. Init floor.");
		
		m.setCurrentFloor(m.minFloor());
		m.setDoorOpen(false);
	}
}
