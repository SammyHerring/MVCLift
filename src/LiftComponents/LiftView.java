package LiftComponents;
import Subjects.*;
import States.*;
import GUIViews.TextView;

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
				
			} else {
				TextView.print("Button State Error.");
			}
			
		} else {
			TextView.print("State Error.");
		}
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
