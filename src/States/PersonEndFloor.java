package States;

import Subjects.Person;
import Views.TextView;

public class PersonEndFloor implements State, PersonState, Runnable {
	
	private final Person p;
	
	private PersonAction personAction;
	
	public PersonEndFloor(Person p) {
		
		this.p = p;
		
		this.personAction = PersonAction.WAITING;
		
	}

	@Override
	public void doAction(boolean running, Object obj) {
		
		TextView.print("Passenger " + (p.getID()+1) + "\t"+ getPersonAction() + " \t|\tStart: " + p.getStartFloor() + "\t End: " + p.getEndFloor());
	}
	
	@Override
	public PersonAction getPersonAction() { return personAction; }

	@Override
	public void setPersonAction(PersonAction personAction) { this.personAction = personAction; }

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
