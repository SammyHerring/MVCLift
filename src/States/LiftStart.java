package States;
import LiftComponents.LiftModel;
import Views.TextView;

public class LiftStart implements State {
	
	@Override
	public void doAction(Object obj) {
		
		if ( !(obj instanceof LiftModel) ) {
			
			throw new IllegalArgumentException("Object passed must be of type LiftModel.");
			
		} else {
			
			LiftModel m = (LiftModel) obj;
			
			TextView.print("Lift Journey Start");
			
		}
		
//		m.setCurrentFloor(m.requestedFloor());
//		m.setDoorOpen(true);
		
		//Start Phase
		// If required, change to Floor of Button Press --> Moving Phase
		// Open Doors
		// Allow Passenger Entry (1 Second p/p)
		
		//Moving Phase
		// Close Doors
		// Change Floor to required Floor
		// Wait 5 Seconds
		
		//End Phase
		// Open Doors
		// Allow Passenger Exit (1 Second p/p)
		// Close Doors
		
	}
	
}