package LiftComponents;
import Subjects.*;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	
	
	public LiftController(LiftView v, LiftModel m) {
		this.v = v; //	LiftView	| Main Observer
		this.m = m; //	LiftModel	| Main Subject
		
		// Subjects --> Lift Model, Buttons, People
		// Observers --> Lift View
		
		Button b0 = new Button();
		Button b1 = new Button();
		
		//Register observers to the subject
		m.register(v);
		b0.register(v);
		b1.register(v);
		
		
		
		b0.postUpdate("Lift Call");
		
		//Attach observer to subjects and update view
//		v.setSubject(m); //Model set as subject in view by default
//		m.postUpdate("Message");
		
		//Button call checker	
		v.setSubject(b0);
		v.update();
		v.setSubject(b1);
		v.update();
		
	}
}
 