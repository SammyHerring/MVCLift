package States;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import LiftComponents.LiftModel;
import Subjects.Button;
import Subjects.Person;
import Views.TextView;

public class PersonStartFloor implements State, PersonState, Callable<Integer> {
	
	private final Person p;
	
	private PersonAction personAction;
	
	public PersonStartFloor(Person p) {
		
		this.p = p;
		
		this.personAction = PersonAction.WAITING;
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

				//	START | Successful Button State Activation Process	

				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime	
				List<Button> b = (List<Button>) obj;
				
				LiftModel m = p.getLift();
				
				Button b_instance = b.get(p.getStartFloor());
				
				if (!running) { TextView.print("Passenger " + (p.getID()+1) + "\t"+ getPersonAction() + " \t|\tStart: " + p.getStartFloor() + "\t\tEnd: " + p.getEndFloor()); }
				
				if (running && b_instance.getState() == b_instance.buttonUnpressedState) {
					
					TextView.print("Passenger " + p.getID() + "\tFloor: " + p.getStartFloor() + "\t|\tPushing Button " + b_instance.getButtonFloor());
					
					b_instance.postUpdate(b.get(p.getStartFloor()).buttonPressedState);
					
				} else if (running && b_instance.getState() == b_instance.buttonPressedState) {
						if (m.getState().equals(m.liftStartState) && m.getCurrentFloor() == p.getStartFloor() && m.getDoorOpen() && !m.checkPassenger(p.getID()) && m.checkPerson(p.getID())) {
							m.addPassenger(p);
							m.removePerson(p.getID());
						}
				}

				//	END | Successful Button State Activation Process	

			} else if (cls == null ) {	

				throw new IllegalArgumentException("Object List<Button> must contain objects.");	

			} else {	

				throw new IllegalArgumentException("Object List<Button> must contain objects of type Button.");	

			}	

		}
	}
	
	@Override
	public PersonAction getPersonAction() { return personAction; }

	@Override
	public void setPersonAction(PersonAction personAction) { this.personAction = personAction; }

	@Override
	public Integer call() throws Exception {
		return p.getStartFloor();
	}

}
