package LiftComponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

import CustomDataTypes.Scenario;
import CustomDataTypes.ScenarioFloor;
import CustomDataTypes.ScenarioFloorComparator;
import Subjects.*;
import Views.ControllerView;
import Views.TextView;

public class LiftController {
	
	private  LiftView v;
	private LiftModel m;
	
	public LiftController(LiftView v, LiftModel m, List<Button> b, ControllerView c) {
		
		//Scenario Setups		
		//Scenario 1
		ScenarioFloor s1f0 = new ScenarioFloor(1, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s1floors = Arrays.asList(new ScenarioFloor[]{s1f0});
		Scenario s1 = new Scenario(s1floors, m.liftInitState);
		
		//Scenario 2
		ScenarioFloor s2f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s2floors = Arrays.asList(new ScenarioFloor[]{s2f1});
		Scenario s2 = new Scenario(s2floors, m.liftInitState);
		
		//Scenario 3a
		ScenarioFloor s3f0 = new ScenarioFloor(3, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		ScenarioFloor s3f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s3floors = Arrays.asList(new ScenarioFloor[]{s3f0, s3f1});
		Scenario s3 = new Scenario(s3floors, m.liftInitState);
		
		//Scenario 3
		ScenarioFloor s4f0 = new ScenarioFloor(15, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		ScenarioFloor s4f1 = new ScenarioFloor(15, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s4floors = Arrays.asList(new ScenarioFloor[]{s4f0, s4f1});
		Scenario s4 = new Scenario(s4floors, m.liftInitState);
		
		//Scenario in use
		Scenario s = s1;
		
		// Subjects --> Lift Model, Buttons, People
		// Observers --> Lift View
		
		//Generate required objects
		this.v = v; //	LiftView	| Main Observer
		this.m = m; //	LiftModel	| Main Subject
		//Register observers to the subject
		//Register model to view and initialise model
		v.update();
		
		generateScenario(s);
		
		//Register all instances of buttons to view
		for (Button button : b) {
			button.register(v);
			v.setSubject(button);
			v.update();
			c.setSubject(button);
			c.update();
		}
		
		for (Person person : m.persons()) {
			v.setSubject(person);
			v.update();
		}
		
		//Run actual scenario simulation
		try {
			
			TextView.print("--\tScenario " + (s.getID()+1) + " Starting\t--");
			
			while(!m.getState().equals(m.liftEndScenarioState)) {
				runScenario(m, b, s, c);
			}
			
		} catch (Exception ex) {
			
			TextView.printError("Scenario Exception", ex.getMessage());
			
		} finally {
			ControllerView.startStopButton(false);
			TextView.print("--\tScenario " + (s.getID()+1) + " Finished\t--");
			
		}
		
		TextView.print("--\tSimulation Finished\t--");
		
	}


	public void generateScenario(Scenario s) {
		
		List<ScenarioFloor> floors = s.getFloors();
		floors.sort(new ScenarioFloorComparator());
		
		//Iterate through floors
		for (int index = 0; index < floors.size(); index = index + 1) {
			
			TextView.print(floors.get(index).getInfo());
			
			//Iterate through number of passengers
			for (int personIndex = 0; personIndex < floors.get(index).getPeople(); personIndex = personIndex + 1) {
				m.persons().add(new Person(floors.get(index).getFloorStart(), floors.get(index).getFloorEnd(), m));
				m.persons().get(personIndex).register(v);
			}
			
		}
		 
	}
	
	public void resetScenario() {
		m.persons().clear();
		m.passengers().clear();
	}
	
	public synchronized void runScenario(LiftModel m, List<Button> b, Scenario s, ControllerView c) throws InterruptedException, ExecutionException, TimeoutException, Exception {
		
		ExecutorService executor = null;
		
		try {
			v.begin();
			
			executor = newWorkStealingPool();
			
			Future<?> controllerViewGUI = executor.submit(() -> { 
				//c.doAction(running, obj);
			});
			
			List<Future<?>> subjectTasks = new ArrayList<>();
			
			for (Person person : m.persons() ) {
				
	            Future<?> passengerFuture = executor.submit(() -> {
	                v.setSubject(person);
	                v.update();
	            });
	            
				for (Button button : b ) {
					
		            Future<?> buttonFuture = executor.submit(() -> {
		                v.setSubject(button);
		                v.update();
		            });
		            
		            subjectTasks.add(buttonFuture);
				}
	            
	            subjectTasks.add(passengerFuture);
			}
			
			
            Future<?> modelFuture = executor.submit(() -> {
                v.setSubject(m);
                v.update();
            });
            subjectTasks.add(modelFuture);
            
            
            for (Future<?> future : subjectTasks) {
                while (!future.isDone()) {
                	Thread.sleep(500);
                }
            }
			
		} finally {
			
			v.end();
			executor.shutdown();
			
		}
		
	}
	
	public static ExecutorService newWorkStealingPool() {
		return new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
	                            ForkJoinPool.defaultForkJoinWorkerThreadFactory,
	                            null, true);
	}
}
 