package States;

import LiftComponents.LiftModel;
import Views.TextView;

public class LiftInit implements State {
	
	@Override
	public void doAction(LiftModel m) {
		TextView.print("Lift Initial State. | Doors Closed. Init floor.");
		
		m.setCurrentFloor(m.minFloor());
		m.setDoorOpen(false);
	}
}
