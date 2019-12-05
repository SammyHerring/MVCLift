package States;

import Views.TextView;

public class PersonMovingFloor implements State, PersonState {
	
	private PersonAction personAction;
	
	public PersonMovingFloor() {
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
