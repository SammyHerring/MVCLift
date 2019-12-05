package States;

import java.util.List;

import Subjects.Button;
import Views.TextView;

public class PersonStartFloor implements State, PersonState {
	
	private PersonAction personAction;
	
	public PersonStartFloor() {
		this.personAction = PersonAction.WAITING;
	}

	@Override
	public void doAction(Object obj) {
		TextView.print("Person is " + getPersonAction().toString());
	}
	
	@Override
	public PersonAction getPersonAction() { return personAction; }

	@Override
	public void setPersonAction(PersonAction personAction) { this.personAction = personAction; }

}
