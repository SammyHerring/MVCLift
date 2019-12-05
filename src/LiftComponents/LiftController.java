package LiftComponents;

import java.util.List;

import Subjects.*;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	private List<Button> b;
	
	
	public LiftController(LiftView v, LiftModel m, List<Button> b) {
		
		// Subjects --> Lift Model, Buttons, People
		// Observers --> Lift View
		
		//Generate required objects
		this.v = v; //	LiftView	| Main Observer
		this.m = m; //	LiftModel	| Main Subject
		this.b = b; //	Buttons		| Secondary Subject

		//Register observers to the subject
		//Register model to view and initialise model
//		m.register(v);
		v.update();
		
		//Register all instances of buttons to view
		for (Button button : b) {
			button.register(v);
			v.setSubject(button);
			v.update();
		}
		
		//b.get(0).postUpdate(b.get(0).);
		
		v.setSubject(m);

		//generateScenario(scenario1());
		
	}


//	public void generateScenario(Quartet<Integer, Integer, Integer, Integer> floorData) {
//		
//		 System.out.println(floorData);
//		 
//	}
//	
//	public Quartet<Integer, Integer, Integer, Integer> scenario1() {
//		return new Quartet<Integer, Integer, Integer, Integer>(0, 1, 0, 0);
//	}
}
 