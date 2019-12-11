package States;

import java.util.ArrayList;
import java.util.List;

import LiftComponents.LiftModel;
import Subjects.Button;
import Views.TextView;

public class LiftStart implements State {
	
	private LiftModel m;
	
	public LiftStart(LiftModel m) {
		this.m = m;
	}
	
	@Override
	public void doAction(boolean running, Object obj) {
		
		if ( !(obj instanceof ArrayList) ) {	

			throw new IllegalArgumentException("Object passed must be of type List<Button>.");	

		} else {	

			Class<? extends Object> cls = null;	


			for (Object aList : (List<?>) obj) {	

				//Check list type before casting	
			    cls = aList.getClass();	
			}	

			if ( cls == Button.class ) {	

				//	START | Successful Button State Activation Process	

				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime	
				List<Button> b = (List<Button>) obj;	
				
				if (!running) { TextView.print("Lift\t\tSTART\t\t|\tDoor Open: " + convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor()); }
				
				if (running) {
					
					m.setCurrentFloor(m.minFloor());
					m.setDoorOpen(false);
					
				} else {
					
				}

				//	END | Successful Button State Activation Process	

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}
	
	public static String convertToTitleCase(String text) {
		
	    if (text == null || text.isEmpty()) {
	        return text;
	    }
	 
	    StringBuilder converted = new StringBuilder();
	 
	    boolean convertNext = true;
	    for (char ch : text.toCharArray()) {
	        if (Character.isSpaceChar(ch)) {
	            convertNext = true;
	        } else if (convertNext) {
	            ch = Character.toTitleCase(ch);
	            convertNext = false;
	        } else {
	            ch = Character.toLowerCase(ch);
	        }
	        converted.append(ch);
	    }
	 
	    return converted.toString();
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
