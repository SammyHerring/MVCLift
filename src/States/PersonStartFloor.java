package States;

import java.util.ArrayList;
import java.util.List;
import Subjects.Button;
import Subjects.Person;
import Views.TextView;

public class PersonStartFloor implements State, PersonState{

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

				Button b_instance = b.get(p.getStartFloor());

				if (!running) {
					TextView.print("Passenger " + (p.getID()+1) + "\t"+ getPersonAction() + " \t|\tStart: " + p.getStartFloor() + "\t\tEnd: " + p.getEndFloor() + "\t\tWeight: " + p.getWeight());
				} else if (b_instance.getState() == b_instance.buttonUnpressedState) {
					if (b_instance.pushButtonSuccess()) {
						TextView.print("Passenger " + (p.getID()+1) + "\tFloor: " + p.getStartFloor() + "\t|\tPushing Button " + b_instance.getButtonFloor());
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

}
