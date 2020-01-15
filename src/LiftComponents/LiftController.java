package LiftComponents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import CustomDataTypes.Scenario;
import CustomDataTypes.ScenarioFloor;
import CustomDataTypes.ScenarioFloorComparator;
import Subjects.*;
import Views.ControllerView;
import Views.TextView;
import Views.ControllerView.scenario;

public class LiftController {

	private  LiftView v;
	private LiftModel m;

	public LiftController(LiftView v, LiftModel m, List<Button> b, ControllerView c) throws InterruptedException {

		Map<scenario, Scenario> scenarios = new HashMap<scenario, Scenario>();

		//Scenario Setups	
		//Scenarios constructed with list of Scenario Floor Type Objects and Initial State Designation
		//Scenario 1
		ScenarioFloor s1f0 = new ScenarioFloor(1, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s1floors = Arrays.asList(new ScenarioFloor[]{s1f0});
		scenarios.put(scenario.S1, new Scenario(s1floors, m.liftInitState));

		//Scenario 2
		ScenarioFloor s2f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s2floors = Arrays.asList(new ScenarioFloor[]{s2f1});
		scenarios.put(scenario.S2, new Scenario(s2floors, m.liftInitState));

		//Scenario 3
		ScenarioFloor s3f0 = new ScenarioFloor(3, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
		ScenarioFloor s3f1 = new ScenarioFloor(1, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
		List<ScenarioFloor> s3floors = Arrays.asList(new ScenarioFloor[]{s3f0, s3f1});
		scenarios.put(scenario.S3, new Scenario(s3floors, m.liftInitState));

		//Scenario 3b || UNUSED SCENARIO FOR TESTING SYSTEM LIMITS
		//TO USE, COMMENT SCENARIO 3, SEE ABOVE, AND UN COMMENT SCENARIO 3b -- BELOW THIS LINE
//		ScenarioFloor s3f0 = new ScenarioFloor(15, 0, 1, 0); //Scenario Floor 0 --> Number of People, Source, Destination, Instance
//		ScenarioFloor s3f1 = new ScenarioFloor(15, 1, 0, 0); //Scenario Floor 1 --> Number of People, Source, Destination, Instance
//		List<ScenarioFloor> s3floors = Arrays.asList(new ScenarioFloor[]{s3f0, s3f1});
//		scenarios.put(scenario.S3, new Scenario(s3floors, m.liftInitState));

		//Default Scenario Setting 
		Scenario s = scenarios.get(scenario.S1); //Scenario Designation to prevent null pointer error if controller pointer fails
		try {
			s = scenarios.get(c.getScenario());
		} catch (Exception ex) {
			TextView.printError("Scenario Selection", "Default Scenario Not Found on Controller");
		}

		// Subjects --> Lift Model, Buttons, People
		// Observers --> Lift View

		//Generate required objects
		this.v = v; //	LiftView	| Main Observer
		this.m = m; //	LiftModel	| Main Subject
		//Register observers to the subject
		//Register model to view and initialise model
		v.update();

		while (true) {
			c.update(); //Await command from controller view
			while (c.getRunState()) {

				c.update();
				s = scenarios.get(c.getScenario());

				generateScenario(s);

				//Register all instances of buttons to view
				for (Button button : b) {
					button.register(v);
					v.setSubject(button);
					v.update();
				}

				for (Person person : m.persons()) {
					v.setSubject(person);
					v.update();
				}

				//Run actual scenario simulation
				try {

					TextView.print("--\tScenario " + (s.getID()+1) + " Starting\t--");

					do {

						runScenario(m, b, s, c);
						c.update();

					} while (!m.getState().equals(m.liftEndScenarioState) && c.getRunState());

				} catch (Exception ex) {

					TextView.printError("Scenario Exception", ex.getMessage());

				} finally {
					c.scenarioButtonManager(scenario.DISABLE);
					c.setRunState(false);
					TextView.printManager("Requesting Executor Service Shutdown");
					Thread.sleep(5000); //Wait for any executor services to stop
					TextView.print("--\tScenario " + (s.getID()+1) + " Finished\t--");
					resetScenario();
					c.update();
				}

				TextView.print("--\tSimulation Finished\t--");
				c.scenarioButtonManager(scenario.ENABLE);
			}
		}
	}


	public void generateScenario(Scenario s) {

		resetScenario();

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

	public synchronized void runScenario(LiftModel m, List<Button> b, Scenario s, ControllerView c) throws InterruptedException, ExecutionException, TimeoutException, Exception {

		ExecutorService executor = null;

		try {
			v.begin();
			c.setRunState(true);

			executor = newWorkStealingPool();

			List<Future<?>> subjectTasks = new ArrayList<>();

			Future<?> controllerViewGUI = executor.submit(() -> { 
				c.update();
			});
			subjectTasks.add(controllerViewGUI);

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
				while (!future.isDone() && c.getRunState()) {
					if (!c.getRunState()) {
						executor.shutdownNow();
						future.cancel(true);
					}
					Thread.sleep(500);
				}
			}

		} finally {
			v.end();
			executor.shutdown();

		}

	}

	public void resetScenario() {
		if (m.getState() != m.liftInitState) { m.setState(m.liftInitState); }
		m.persons().clear();
		m.passengers().clear();
	}

	public static ExecutorService newWorkStealingPool() {
		return new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
				ForkJoinPool.defaultForkJoinWorkerThreadFactory,
				null, true);
	}
}
