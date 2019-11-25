import Subjects.*;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	
	public LiftController(LiftView v, LiftModel m) {
		this.v = v;
		this.m = m;
		
		Observer b0 = new Button();
		Observer b1 = new Button();
		
		m.register(b0);
		m.register(b1);
		
		b0.setSubject(m);
		b1.setSubject(m);
		
		b0.update();
		
		m.postMessage("Update");
	}	
}
 