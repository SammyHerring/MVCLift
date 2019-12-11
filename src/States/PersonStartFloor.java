package States;

import java.util.concurrent.Callable;

import Subjects.Person;
import Views.TextView;

public class PersonStartFloor implements State, PersonState, Runnable, Callable<Integer> {
	
	private final Person p;
	
	private PersonAction personAction;
	
	public PersonStartFloor(Person p) {
		
		this.p = p;
		
		this.personAction = PersonAction.WAITING;
	}

	@Override
	public void doAction(Object obj) {
		
		TextView.print("Passenger " + (p.getID()+1) + "\t"+ getPersonAction() + " \t|\tStart: " + p.getStartFloor() + "\t End: " + p.getEndFloor());
		
	}
	
	@Override
	public PersonAction getPersonAction() { return personAction; }

	@Override
	public void setPersonAction(PersonAction personAction) { this.personAction = personAction; }

	@Override
	public void run() {
		TextView.print("Person Running " + p.getID());
		
	}

	@Override
	public Integer call() throws Exception {
		return p.getStartFloor();
	}

}
