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
		
		//Attach observer to subjects and post message and update
		v.setSubject(m);
		m.postMessage("Model Message");
		v.update();
		
		v.setSubject(b0);
		b0.postMessage("Button 0 Message");
		v.update();
		
		v.setSubject(b1);
		b1.postMessage("Button 1 Message");
		v.update();
		
	}
}
 