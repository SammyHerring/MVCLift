package States;

import java.util.ArrayList;
import java.util.List;

import CustomDataTypes.Generic;
import LiftComponents.LiftModel;
import Subjects.Button;
import Views.ControllerView;
import Views.TextView;

public class LiftInit implements State {

	private LiftModel m;

	public LiftInit(LiftModel m) {
		this.m = m;
	}

	@Override
	public synchronized void doAction(boolean running, Object obj) {

		if ( !(obj instanceof ArrayList) ) {	

			throw new IllegalArgumentException("Object passed must be of type List<Button>.");	

		} else {	

			Class<? extends Object> cls = null;	


			for (Object aList : (List<?>) obj) {	

				//Check list type before casting	
				cls = aList.getClass();	
			}	

			if ( cls == Button.class ) {	

				///	START | Lift INIT State View Update

				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime	
				List<Button> b = (List<Button>) obj;	

				if (!running) {
					ControllerView.startStopButton(false);
					TextView.print("Lift\tINIT\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor()); 
					m.setCurrentFloor(m.minFloor());
					m.setDoorOpen(false);
				} else {
					ControllerView.startStopButton(true);
					
					for (Button button : b) {
						if (button.getState() == button.buttonPressedState) {
							m.postUpdate(m.liftStartState);
						}
					}
				}

				///	END | Lift INIT State View Update	

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}
}
