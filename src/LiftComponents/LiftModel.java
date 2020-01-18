package LiftComponents;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import States.*;
import Subjects.*;
import Views.ControllerView;
import Views.TextView;

public final class LiftModel implements Subject, State {
	
	private int currentFloor;
	private boolean doorOpen;
	private int maxWeight;
	private int minFloor;
	private int maxFloor;
	private CopyOnWriteArrayList<Person> passengers;
	private CopyOnWriteArrayList<Person> persons;
	
    private static LiftModel INSTANCE;
   
	private List<Observer> observers;
	private final Object MUTEX= new Object();
	private boolean changed;
	
	private ControllerView c;
	
	private State liftState;
	public final State liftInitState;
	public final State liftStartState;
	public final State liftMovingState;
	public final State liftEndState; //End of Lift Floor Movement
	public final State liftEndScenarioState; //End of entire Lift Scenario
     
    private LiftModel(int maxWeight, int minFloor, int maxFloor, ControllerView c) {
    	
    	this.maxWeight = maxWeight; //To be used for program extension
    	this.minFloor = minFloor;
    	this.maxFloor = maxFloor;
    	
    	this.c = c;
    	
    	this.liftInitState = new LiftInit(this, c);
    	this.liftStartState = new LiftStart(this, c);
    	this.liftMovingState = new LiftMoving(this, c);
    	this.liftEndState = new LiftEnd(this, c);
    	this.liftEndScenarioState = new LiftEndScenario(this, c);
    	
    	this.passengers = new CopyOnWriteArrayList<Person>(new ArrayList<Person>());
    	this.persons = new CopyOnWriteArrayList<Person>(new ArrayList<Person>());
    	
    	this.observers = new ArrayList<>();
    	
    	this.postUpdate(liftInitState);
    }
	
	///	START 	| PASSENGER MANAGEMENT
	public boolean checkPassenger(int passengerID) {
		synchronized(passengers) {
			for (Person passenger : passengers) {
				if (passenger.getID() == passengerID) {
					return true;
				}
			}
			return false;
		}
	}
	
	public boolean checkPerson(int personID) {
		synchronized(persons) {
			for (Person person : persons) {
				if (person.getID() == personID) {
					return true;
				}
			}
			return false;
		}
	}
	
	public Integer currentWeight() {
		Integer w = 0;
		synchronized (passengers) {
			for (Person person : passengers) {
				w = w + person.getWeight();
			}
			return w;
		}
	}
	
	public boolean passengerWeightNotExceeded(Integer passengerWeight) {
		
		if (!(Integer.sum(currentWeight(), passengerWeight) > this.maxWeight)) {
			return true;
		} else {
			return false;
		}
	}
	
	///	END		| PASSENGER MANAGEMENT
    
    /// START	| SINGLETON DESIGN PATTERN + THREAD MANAGEMENT
     
    public static LiftModel getInstance() {
    	if(INSTANCE == null) {
    		throw new AssertionError("Lift Model Instance must be initialised.");
    	}
        return INSTANCE;
    }
    

    public synchronized static LiftModel initialise(int maxWeight, int minFloor, int maxFloor, ControllerView c) {
        if (INSTANCE != null)
        {
            throw new AssertionError("Lift Model Instance already initialised.");
        }
        
        INSTANCE = new LiftModel(maxWeight, minFloor, maxFloor, c);
        TextView.print("Lift Initialised\t\t|\tFloors " + minFloor + " to " + maxFloor);
        
        return INSTANCE;
    }
    
    ///	END		| SINGLETON DESIGN PATTERN + THREAD MANAGEMENT
    
    /// START	|	ACCESSOR & MUTATOR METHODS
    
    public int getCurrentFloor() { return this.currentFloor; }
    public void setCurrentFloor(int floor)
    { 
    	//Ensure requested floor is within lift instance's initialised floor range
    	if ( floor < minFloor || floor > maxFloor ) {
    		throw new IllegalArgumentException("Requested floor not within lift's initialised floor range.");
    	} else 
    		this.currentFloor = floor; 
    }
    
    public boolean getDoorOpen() { return this.doorOpen; }
    public void setDoorOpen (boolean doorOpen) { this.doorOpen = doorOpen; }
    
    public int maxWeight() { return this.maxWeight; }
    
    public int minFloor() { return this.minFloor; }
    
    public int maxFloor() { return this.maxFloor; }
    
    public int floorCount() {
    	if (this.minFloor == 0) {
    		return this.maxFloor + 1;
    	} else {
    		return (this.maxFloor - this.minFloor);
    	}
    }
    
    public List<Person> passengers() { return this.passengers; }
    
    public List<Person> persons() { return this.persons; }
    
    /// END		|	ACCESSOR & MUTATOR METHODS
    
    ///	START	| SUBJECT DESIGN PATTERN
    
	@Override
	public void register(Observer obj) {
		
		if(obj == null) {
			throw new NullPointerException("Null Observer");
		}
		
		synchronized (MUTEX) {
			if(!observers.contains(obj)) observers.add(obj);
		}
	}

	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
			observers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observersLocal = null;
		
		//Synchronisation is used to make sure any observer registered after message is received is not notified
		synchronized (MUTEX) {
			if (!changed) return;
			
			observersLocal = new ArrayList<>(this.observers);
			this.changed=false;
		}
		
		for (Observer obj : observersLocal) {
			obj.update();
		}
	}

	@Override
	public State getUpdate(Observer obj) {
		return this.liftState;
	}
	
	@Override
	public void postUpdate(State liftState){
		this.liftState=liftState;
		this.changed=true;
		notifyObservers();
	}
	
	///	END		| SUBJECT DESIGN PATTERN
	
	///	START	|	LIFT STATE MANAGER DESIGN PATTERN

	public void setState(State state) {
		this.liftState = state;
	}

	public State getState() {
		return this.liftState;
	}

	@Override
	public void doAction(boolean running, Object obj) {
		this.liftState.doAction(running, obj);
	}
	
	///	END		| LIFT STATE MANAGER DESIGN PATTERN

}
