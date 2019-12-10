package LiftComponents;
import Subjects.*;
import Views.TextView;

import java.util.List;

import States.*;

public class LiftView implements Observer {
	
	private Subject subject;
	private LiftModel m;
	private List<Button> b;
	
	public LiftView(LiftModel m, List<Button> b) {
		this.m = m;
		this.b = b;
		
		setSubject(m); //Set observer subject to Lift Model by default
	}
	
    ///	START	| OBSERVER DESIGN PATTERN

	@Override
	public void update() {
		
		Object update = subject.getUpdate(this);
		
		//getUpdate returns States - not the button instance
		
		if (update instanceof ButtonState) {
			
			if (update instanceof State) {
				
				((State) update).doAction(b);
				
			} else {
				
				TextView.printError("Object State", "Update object pushed, is of type Button but not State.");
				
			}
			
		} else if ( update instanceof PersonState ) {
			
			if (update instanceof State) {
				
				((State) update).doAction(m.passengers());
				
			} else {
				
				TextView.printError("Object State", "Update object pushed, is of type Button but not State.");
				
			}
			
		} else if (update instanceof State) {
			
			((State) update).doAction(m);
			
		} else {
			
			TextView.printError("Object State", "Update object pushed, not of State object type.");
		}
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	
    ///	END	| OBSERVER DESIGN PATTERN
}
