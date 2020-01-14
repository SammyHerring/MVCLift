package States;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import CustomDataTypes.Generic;
import LiftComponents.LiftModel;
import Subjects.Button;
import Subjects.Person;
import Views.ControllerView;
import Views.TextView;

public class LiftStart implements State {

	private LiftModel m;

	public LiftStart(LiftModel m) {
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

				///	START | Lift START State View Update

				if (!running) {
					ControllerView.startStopButton(false);
					
					TextView.print("Lift\tSTART\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor());
				} else {
					
					ControllerView.startStopButton(true);
					
					for (Button button : b) {

						if ((button.getState() == button.buttonPressedState) &&
								(m.getCurrentFloor() == button.getButtonFloor())) {

							//Open Doors
							m.setDoorOpen(true);

							//Notification of Arrival
							//IF NO PASSENGERS HAVE ENTERED AND ARE WAITING
							if (m.passengers().isEmpty() && !(m.persons().isEmpty())) {
								TextView.print("Lift\tSTART\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift Arrived");
							}
							
							//Passengers Entry
							for (Person p : m.persons()) {
								if (m.getCurrentFloor() == p.getStartFloor() && 
										m.getDoorOpen() && 
										!m.checkPassenger(p.getID()) && 
										m.checkPerson(p.getID()) &&
										m.passengerWeightNotExceeded(p.getWeight()) ) {

									m.passengers().add(p);
									m.persons().remove(p);
								}
							}

							//Once Passengers Enter - 1 second p/p
							for (Person passenger: m.passengers()) {

								try {
									if (!m.checkPerson(passenger.getID())) {
										TextView.print("Passenger " + (passenger.getID()+1) + "\tENTERING\t|\tFloor: " + passenger.getStartFloor() + "\t\tEntering Lift");
									}
									Thread.sleep(1000);

								} catch (InterruptedException e) {

									TextView.printError("Passenger Entry Thread Interupted", e.getMessage());

								}

							}

							synchronized(m.persons()) {
								//Lambda Stream to detect people awaiting to board
								List<Person> peopleOnFloor = m.persons().stream().filter(p -> p.getStartFloor() == m.getCurrentFloor()).collect(Collectors.toList());

								for (Person person : peopleOnFloor) {

									if (!m.passengerWeightNotExceeded(person.getWeight())) {
										TextView.print("Lift\tSTART\t|\tDoor Open: " + Generic.convertToTitleCase(String.valueOf(m.getDoorOpen())) + "\tFloor: " + m.getCurrentFloor() + "\tLift Full at " + m.currentWeight() + " KG");
										m.postUpdate(m.liftMovingState);
										break;
									}

								}

								if (peopleOnFloor.isEmpty()) {

									m.postUpdate(m.liftMovingState);

								}
							}

						} else if (button.getState() == button.buttonPressedState && m.getCurrentFloor() != button.getButtonFloor()) {
							m.postUpdate(m.liftMovingState);
						}
					}
				}

				///	END | Lift START State View Update

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}
}
