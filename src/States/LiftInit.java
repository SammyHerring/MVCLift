package States;

import Views.TextView;

public class LiftInit implements State {
	
	@Override
	public void doAction() {
		TextView.print("Lift Initial State");
		TextView.print("Doors Closed. Init floor.");
		TextView.print("REVIEW BEFORE PRODUTION! NO ACTUAL INIT OCCURED.");
		
//		m.setCurrentFloor(m.minFloor());
//		m.setDoorOpen(false);
	}
}
