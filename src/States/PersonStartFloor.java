package States;

import java.util.ArrayList;
import java.util.List;

import LiftComponents.LiftModel;
import Subjects.Person;
import Views.TextView;

public class PersonStartFloor implements State, PersonState, Runnable {
	
	private final Integer personID;
	
	private PersonAction personAction;
	
	public PersonStartFloor(Integer personID) {
		
		this.personID = personID;
		
		this.personAction = PersonAction.WAITING;
	}

	@Override
	public void doAction(Object obj) {
		
		if ( !(obj instanceof ArrayList) ) {
			
			throw new IllegalArgumentException("Object passed must be of type List<Person>.");
			
		} else {
			
			Class<? extends Object> cls = null;
			
			for (Object aList : (List<?>) obj) {
				
				//Check list type before casting
			    cls = aList.getClass();
			}
			
			if ( cls == Person.class ) {
				
				//	START | Successful Button State Activation Process
				
				@SuppressWarnings("unchecked") //Check performed using reflection, evaluation occurs at runtime
				List<Person> p = (List<Person>) obj;
				
				TextView.print("Passenger " + (personID+1) + "\t"+ getPersonAction() + " \t|\tStart: " + p.get(personID).getStartFloor() + "\t End: " + p.get(personID).getEndFloor());
				
				//	END | Successful Button State Activation Process
				
			} else if (cls == null ) {
				
				throw new IllegalArgumentException("Object List<Person> must contain objects.");
				
			} else {
				
				throw new IllegalArgumentException("Object List<Person> must contain objects of type Person.");
				
			}
			
		}
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
