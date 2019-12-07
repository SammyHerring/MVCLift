package LiftComponents;

import java.util.Arrays;
import java.util.List;

import CustomDataTypes.Scenario;
import CustomDataTypes.ScenarioFloor;
import Subjects.*;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	private List<Button> b;
	
	
	public LiftController(LiftView v, LiftModel m, List<Button> b) {
		
		//Scenario Setups
		//Scenario 1
		ScenarioFloor s1f0 = new ScenarioFloor(1, 0, 1, 0); //Scenario Floor --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s1floors = Arrays.asList(new ScenarioFloor[]{s1f0});
		Scenario s1 = new Scenario(s1floors, m.liftInitState);
		
		//Scenario 2
		ScenarioFloor s2f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s2floors = Arrays.asList(new ScenarioFloor[]{s2f1});
		Scenario s2 = new Scenario(s1floors, m.liftInitState);
		
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
		
		v.setSubject(m);

		generateScenario(s1);
		
	}


	public void generateScenario(Scenario s1) {
		
		//Iterate through floors
		for (int index = 0; index <= s1.getFloors().size(); index = index + 1) {
			
		}
		 
	}
}
 