package States;

import java.util.ArrayList;
import java.util.List;

import Subjects.Button;
import Views.TextView;

public class ButtonPressed implements State, ButtonState {
	
	private int buttonFloor;
	
	public ButtonPressed(int buttonFloor) {
		this.buttonFloor = buttonFloor;
	}

	public void doAction(Object obj) {
		
		if ( !(obj instanceof ArrayList) ) {
			
			throw new IllegalArgumentException("Object passed must be of type List<Button>.");
			
		} else {
			
			Class cls = null;
			
			for (Object aList : (List<?>) obj) {
				
				//Check list type before casting
			    cls = aList.getClass();
			}
			
			if ( cls == Button.class ) {
				
				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime
				List<Button> b = (List<Button>) obj;
				
				TextView.print("Button Not Pressed. Floor: " + b.get(buttonFloor).getButtonFloor());
				
			} else if (cls == null ) {
				
				throw new IllegalArgumentException("Object List<Button> must contain objects.");
				
			} else {
				
				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");
				
			}
			
		}
		
	}
	
}
