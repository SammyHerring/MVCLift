package States;

import LiftComponents.LiftModel;
import Views.TextView;

public class LiftInit implements State {
	
	@Override
	public void doAction(Object obj) {
		
		if ( !(obj instanceof LiftModel) ) {
			
			throw new IllegalArgumentException("Object passed must be of type LiftModel.");
			
		} else {
			
			LiftModel m = (LiftModel) obj;
			
			TextView.print("Lift Initial State. | Doors Closed. Init floor.");
			
			m.setCurrentFloor(m.minFloor());
			m.setDoorOpen(false);
			
		}
	}
}
