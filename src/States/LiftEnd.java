package States;

import LiftComponents.LiftModel;
import Views.TextView;

public class LiftEnd implements State {
	
	@Override
	public void doAction(Object obj) {	
		
		if ( !(obj instanceof LiftModel) ) {
			
			throw new IllegalArgumentException("Object passed must be of type LiftModel.");
			
		} else {
			
			LiftModel m = (LiftModel) obj;
			
			TextView.print("Lift Journey Ended");
			TextView.print("Release passengers. Wait. Close doors. End Floor.");
			
		}
		
	}
	
}
