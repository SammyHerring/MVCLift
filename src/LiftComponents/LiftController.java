package LiftComponents;

import java.util.Arrays;
import java.util.List;

import CustomDataTypes.Scenario;
import CustomDataTypes.ScenarioFloor;
import CustomDataTypes.ScenarioFloorComparator;
import Subjects.*;
import Views.TextView;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	private List<Button> b;
	
	
	public LiftController(LiftView v, LiftModel m, List<Button> b) {
		
		//Scenario Setups
		//Scenario 1
		ScenarioFloor s1f0 = new ScenarioFloor(1, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s1floors = Arrays.asList(new ScenarioFloor[]{s1f0});
		Scenario s1 = new Scenario(s1floors, m.liftInitState);
		
		//Scenario 2
		ScenarioFloor s2f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s2floors = Arrays.asList(new ScenarioFloor[]{s2f1});
		Scenario s2 = new Scenario(s2floors, m.liftInitState);
		
		//Scenario 3
		ScenarioFloor s3f0 = new ScenarioFloor(3, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		ScenarioFloor s3f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s3floors = Arrays.asList(new ScenarioFloor[]{s3f0, s3f1});
		Scenario s3 = new Scenario(s3floors, m.liftInitState);
		
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
		
		for (Person passenger : m.passengers()) {
			TextView.print("Passenger " + passenger.getID() + "\t|\tStart: " + passenger.getStartFloor() + "\t End: " + passenger.getEndFloor());
		}
		
	}


	public void generateScenario(Scenario s) {
		
		List<ScenarioFloor> floors = s.getFloors();
		floors.sort(new ScenarioFloorComparator());
		
		//Iterate through floors
		for (int index = 0; index < floors.size(); index = index + 1) {
			
			TextView.print(floors.get(index).getInfo());
			
			//Iterate through number of passengers
			for (int passengers = 0; passengers < floors.get(index).getPeople(); passengers = passengers + 1) {
				
				Person p = m.addPassengerRef(new Person(floors.get(index).getFloorStart(), floors.get(index).getFloorEnd()));
				p.register(v);
			}
			
		}
		 
	}
}
 