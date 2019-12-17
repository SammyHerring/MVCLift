package States;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import CustomDataTypes.Generic;
import LiftComponents.LiftModel;
import Subjects.Button;
import Subjects.Person;
import Views.TextView;

public class LiftEnd implements State {

	private LiftModel m;

	public LiftEnd(LiftModel m) {
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

				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime	
				List<Button> b = (List<Button>) obj;	

				if (!running) {
					TextView.print("Lift\t\tEND\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\t\tFloor: " + m.getCurrentFloor());
				} else {
					m.setDoorOpen(true);

					if (m.passengers().isEmpty()) {
						TextView.print("Lift\t\tEND\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\t\tFloor: " + m.getCurrentFloor() + "\tLift Arrived");
					}

					//People Enter - sleep thread for 1 second
					for (Person passenger: m.passengers()) {

						try {
							if (!m.checkPerson(passenger.getID()) && m.checkPassenger(passenger.getID())) {
								m.passengers().remove(passenger);
								TextView.print("Passenger " + (passenger.getID()+1) + "\tFloor: " + passenger.getStartFloor() + "\t|\tExited Lift");
							}
							Thread.sleep(1000);

						} catch (InterruptedException e) {

							TextView.printError("Passenger Entry Thread Interupted", e.getMessage());

						}

					}

					if (m.persons().isEmpty()) {
						m.setDoorOpen(false);
						TextView.print("Lift\t\tEND\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift Journey End");
						m.postUpdate(m.liftEndScenarioState);
					} else {
						TextView.print("Lift\t\tEND\t\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\t\tFloor: " + m.getCurrentFloor() + "\tAwaiting Passengers");
						m.postUpdate(m.liftStartState);
					}



				}

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
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
