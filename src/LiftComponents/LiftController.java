package LiftComponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

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
		
		//Scenario in use
		Scenario s = s3;
		
		// Subjects --> Lift Model, Buttons, People
		// Observers --> Lift View
		
		//Generate required objects
		this.v = v; //	LiftView	| Main Observer
		this.m = m; //	LiftModel	| Main Subject
		this.b = b; //	Buttons		| Secondary Subject
		
		generateScenario(s);

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
		
		for (Person passenger : m.passengers()) {
			v.setSubject(passenger);
			v.update();
		}
		
		try {
			
			runScenario(m, b, s);
			
		} catch (Exception ex) {
			
			TextView.printError("Scenario Exception", ex.getMessage());
			
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
	
	public void runScenario(LiftModel m, List<Button> b, Scenario s) throws InterruptedException, ExecutionException, TimeoutException, Exception {
		
		TextView.print("--\tScenario " + (s.getID()+1) + " Starting\t--");
		
		ExecutorService executor = null;
		
		try {
			executor = newWorkStealingPool();
			
			Collection<Callable<Integer>> callables = new ArrayList<>();
			
			for (Person passenger : m.passengers()) {
				callables.add((Callable<Integer>) passenger.getState());
			}
			
			List<Future<Integer>>personTasks = executor.invokeAll(callables);
			
			for (Future<Integer> status : personTasks) {
				Integer floor = status.get(1, TimeUnit.SECONDS);
				TextView.print("Passenger Calling Lift to Floor " + floor);
			}
			
		} finally {
			
			TextView.print("--\tScenario " + (s.getID()+1) + " Finished\t--");
			executor.shutdown();
			
		}

		TextView.print("--\tSimulation Finished\t--");
		
	}
	
	public static ExecutorService newWorkStealingPool() {
		return new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
	                            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
	                            null, true);
	}
}
 