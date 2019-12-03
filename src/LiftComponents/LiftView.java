package LiftComponents;
import Subjects.*;
import Views.TextView;
import States.*;

public class LiftView implements Observer {
	
	private Subject subject;
	private LiftModel m;
	
	public LiftView(LiftModel m) {
		this.m = m;
		
		setSubject(m); //Set observer subject to Lift Model by default
	}

	@Override
	public void update() {
		
		Object update = subject.getUpdate(this);
		
		if (update instanceof State) {
			
			if (update instanceof ButtonPressed) {
				
			} else if (update instanceof ButtonUnpressed) {
				
			} else if (update instanceof LiftInit) {
				
			} else if (update instanceof LiftStart) {
				
			} else if (update instanceof LiftMoving) {
				
			} else if (update instanceof LiftEnd) {
				
			} else {
				TextView.print("State Error. State not found");
			}
			
		} else {
			TextView.print("State Error. Update pushed not a State object.");
		}
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
