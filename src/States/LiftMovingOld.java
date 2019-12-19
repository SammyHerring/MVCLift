package States;

import java.util.ArrayList;
import java.util.List;
import CustomDataTypes.Generic;
import LiftComponents.LiftModel;
import Subjects.Button;
import Views.TextView;

public class LiftMovingOld implements State {

	private LiftModel m;

	public LiftMovingOld(LiftModel m) {
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
				
				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime	
				List<Button> b = (List<Button>) obj;	

				if (!running) {
					TextView.print("Lift\t\tMOVING\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\t\tFloor: " + m.getCurrentFloor());
				} else {

					for (Button button : b) {
						if (button.getState() == button.buttonPressedState) {
							if (m.passengers().get(0).getStartFloor() == button.getButtonFloor()) {
								m.setDoorOpen(false);
								button.postUpdate(button.buttonUnpressedState);
								TextView.print("Button\t\tFloor: " + button.getButtonFloor() + "\t|\tState: " + button.getState().toString());

								TextView.print("Lift\t\tMOVING\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift in Transit");


								try {
									
									int travelTime = 5; //Seconds
									
									for (int progress = 0; progress < travelTime; progress = progress + 1) {
										animateMovement(progress, travelTime, m);
										Thread.sleep(1000);
									}
									
								} catch (InterruptedException e) {

									TextView.printError("Passenger Entry Thread Interupted", e.getMessage());

								} finally {
									
									m.setCurrentFloor(m.passengers().get(0).getEndFloor());
									m.setDoorOpen(true);
									TextView.print("Lift\t\tEND\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\t\tFloor: " + m.getCurrentFloor() + "\tLift Arrived");
									m.postUpdate(m.liftEndState);
									
								}
							}

						}
					}
				}

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}

	private static void animateMovement(int progressPercentage, int travelTime, LiftModel m) {
		TextView.printnl("Lift\t\tMOVING\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\t[");
		int i = 0;
		for (; i <= progressPercentage; i++) {
			TextView.printnlnt(".");
		}
		for (; i < travelTime; i++) {
			TextView.printnlnt(" ");
		}
		TextView.printnlnt("]\n");
	}

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