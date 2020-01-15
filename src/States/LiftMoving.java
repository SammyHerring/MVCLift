package States;

import java.util.ArrayList;
import java.util.List;
import CustomDataTypes.Generic;
import LiftComponents.LiftModel;
import Subjects.Button;
import Views.TextView;

public class LiftMoving implements State {

	private LiftModel m;

	public LiftMoving(LiftModel m) {
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

				///	START | Lift MOVING State View Update

				if (!running) {
					TextView.print("Lift\tMOVING\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\t\tFloor: " + m.getCurrentFloor());
				} else {
					for (Button button : b) {
						if (button.getState() == button.buttonPressedState) {

							//CHECK IF LIFT IS EMPTY
							if (!m.passengers().isEmpty()) {

								//IF NOT EMPTY, MOVE LIFT TO FLOOR REQUESTED BY PASSENGERS
								if (m.passengers().get(0).getStartFloor() == button.getButtonFloor()) {

									m.setDoorOpen(false);
									TextView.print("Lift\tMOVING\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift in Transit");

									//ACTUAL LIFT MOVEMENT
									//WAIT FOR MOVEMENT TIME BEFORE UPDATING LIFT MODEL
									try {

										int travelTime = 5; //Seconds

										for (int progress = 0; progress < travelTime; progress = progress + 1) {
												animateMovement(progress, travelTime, m);
												Thread.sleep(1000);
										}

									} catch (InterruptedException e) {

										TextView.printError("Passenger Entry Thread Interupted", e.getMessage());

									} finally {

										button.postUpdate(button.buttonUnpressedState);
										m.setCurrentFloor(m.passengers().get(0).getEndFloor()); //Prioritise lift movement to first passenger to enter lift
										m.setDoorOpen(true);
										TextView.print("Lift\tEND\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift Arrived");
										m.postUpdate(m.liftEndState);

									}
								}

								//IF EMPTY, MOVE TO FLOOR REQUESTED BY BUTTON PRESS
							} else {

								m.setDoorOpen(false);
								TextView.print("Lift\tMOVING\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift in Transit");
								try {

									int travelTime = 5; //Seconds

									for (int progress = 0; progress < travelTime; progress = progress + 1) {
										if (running) {
											animateMovement(progress, travelTime, m);
											Thread.sleep(1000);
										}
									}

								} catch (InterruptedException e) {

									TextView.printError("Passenger Entry Thread Interupted", e.getMessage());

								} finally {

									m.setCurrentFloor(button.getButtonFloor());
									m.setDoorOpen(true);
									m.postUpdate(m.liftEndState);

								}
							}

						}
					}
				}

				///	END | Lift MOVING State View Update

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}

	private static void animateMovement(int progressPercentage, int travelTime, LiftModel m) {
		TextView.printnl("Lift\tMOVING\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\t[");
		int i = 0;
		for (; i <= progressPercentage; i++) {
			TextView.printnlnt(".");
		}
		for (; i < travelTime; i++) {
			TextView.printnlnt(" ");
		}
		TextView.printnlnt("]\n");
	}
}
