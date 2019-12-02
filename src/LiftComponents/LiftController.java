package LiftComponents;

import CustomDataTypes.Quartet;
import Subjects.*;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	
	
	public LiftController(LiftView v, LiftModel m) {
		
		// Subjects --> Lift Model, Buttons, People
		// Observers --> Lift View
		
		this.v = v; //	LiftView	| Main Observer
		this.m = m; //	LiftModel	| Main Subject
		
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
		
		//generateScenario(scenario1());
		
	}


	public void generateScenario(Quartet<Integer, Integer, Integer, Integer> floorData) {
		
		 System.out.println(floorData);
		 
	}
	
	public Quartet<Integer, Integer, Integer, Integer> scenario1() {
		return new Quartet<Integer, Integer, Integer, Integer>(0, 1, 0, 0);
	}
}
 