package States;

import java.util.ArrayList;
import java.util.List;
import CustomDataTypes.Generic;
import LiftComponents.LiftModel;
import Subjects.Button;
import Subjects.Person;
import Views.ControllerView;
import Views.TextView;

public class LiftEnd implements State {

	private LiftModel m;
	private ControllerView c;

	public LiftEnd(LiftModel m, ControllerView c) {
		this.m = m;
		this.c = c;
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

				///	START | Lift END State View Update

				if (!running) {					
					TextView.print("Lift\tEND\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor());
				} else {
					ControllerView.animationViews.get(m.getCurrentFloor()).openDoors();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						TextView.printError("Door State", "Thread sleep time related error.");
					} finally {
						m.setDoorOpen(true);
					}

					//People Enter - sleep thread for 1 second
					for (Person passenger: m.passengers()) {

						try {
							if (!m.checkPerson(passenger.getID()) && m.checkPassenger(passenger.getID())) {
								m.passengers().remove(passenger);
								TextView.print("Passenger " + (passenger.getID()+1) + "\tEXITING\t|\tFloor: " + passenger.getStartFloor() + "\t\tExited Lift");
							}
							Thread.sleep(1000);

						} catch (InterruptedException e) {

							TextView.printError("Passenger Entry Thread Interupted", e.getMessage());

						}

					}

					if (m.persons().isEmpty()) {

						ControllerView.animationViews.get(m.getCurrentFloor()).closeDoors();
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							TextView.printError("Door State", "Thread sleep time related error.");
						} finally {
							m.setDoorOpen(false);
						}

						TextView.print("Lift\tEND\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift Journey End");
						
						m.postUpdate(m.liftEndScenarioState);

					} else {

						synchronized(m) {
							TextView.print("Lift\tEND\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tAwaiting Passengers");
						}
						m.postUpdate(m.liftStartState);

					}
				}

				///	END | Lift END State View Update


			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}

}
