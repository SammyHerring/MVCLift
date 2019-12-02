package LiftStates;

import LiftComponents.*;

public class LiftStart implements LiftState {
	
	@Override
	public void doAction(LiftModel m) {
		System.out.println("Lift Journey Start");
		
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
