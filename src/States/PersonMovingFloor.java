package States;

import java.util.concurrent.Callable;

import Subjects.Person;
import Views.TextView;

public class PersonMovingFloor implements State, PersonState, Runnable, Callable {
	
	private final Person p;
	
	private PersonAction personAction;
	
	public PersonMovingFloor(Person p) {
		
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
		
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
