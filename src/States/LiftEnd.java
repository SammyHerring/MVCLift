package States;

import LiftComponents.LiftModel;
import Views.TextView;

public class LiftEnd implements State {
	
	@Override
	public void doAction(boolean running, Object obj) {
		
		if ( !(obj instanceof LiftModel) ) {
			
			throw new IllegalArgumentException("Object passed must be of type LiftModel.");
			
		} else {
			
			LiftModel m = (LiftModel) obj;
			
			TextView.print("Lift\t\tEND\t\t|\tDoor Open: " + convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor());
			
			m.setCurrentFloor(m.minFloor());
			m.setDoorOpen(false);
			
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
	
}
