package LiftComponents;
import Subjects.*;
import Views.TextView;

import java.util.List;

import States.*;

public class LiftView implements Observer {
	
	private Subject subject;
	private LiftModel m;
	private List<Button> b;
	
	private boolean running;
	
	public LiftView(LiftModel m, List<Button> b) {
		this.m = m;
		this.b = b;
		
		this.running = false;
		
		setSubject(m); //Set observer subject to Lift Model by default
	}
	
    /// START	|	ACCESSOR & MUTATOR METHODS
	
	public void begin() { this.running = true; }
	
	public void end() { this.running = false; }
	
    /// END		|	ACCESSOR & MUTATOR METHODS
	
    ///	START	| OBSERVER DESIGN PATTERN

	@Override
	public void update() {
		
		Object update = subject.getUpdate(this);
		
		if (update instanceof State ) {
			
			((State) update).doAction(this.running, b);
			
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
