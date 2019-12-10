package LiftComponents;
import java.util.ArrayList;
import java.util.List;

import States.*;
import Subjects.*;
import Views.TextView;

public final class LiftModel implements Subject, State {
	
	private int currentFloor;
	private boolean doorOpen;
	private int maxWeight;
	private int minFloor;
	private int maxFloor;
	private List<Person> passengers;
	
    private static LiftModel INSTANCE;
   
	private List<Observer> observers;
	private final Object MUTEX= new Object();
	private boolean changed;
	
	private State liftState;
	public State liftInitState;
	public State liftStartState;
	public State liftMovingState;
	public State liftEndState;
     
    private LiftModel(int maxWeight, int minFloor, int maxFloor) {
    	
    	this.maxWeight = maxWeight; //To be used for program extension
    	this.minFloor = minFloor;
    	this.maxFloor = maxFloor;
    	
    	final State liftInitState = new LiftInit();
    	final State liftStartState = new LiftStart();
    	final State liftMovingState = new LiftMoving();
    	final State liftEndState = new LiftEnd();
    	
    	this.passengers = new ArrayList<>();
    	
    	this.observers = new ArrayList<>();
    	
    	this.postUpdate(liftInitState);
    }
    
	public void changeFloor(int destinationFloor) {
		if (this.currentFloor == destinationFloor) {
			//do nothing else - change floor
		}
	}
	
	///	START 	| PASSENGER MANAGEMENT
	
	public void addPassenger(Person p) {
		passengers.add(p);
	}
	
	public Person addPassengerRef(Person p) {
		passengers.add(p);
		return p;
	}
	
	public void removePassenger(int index) {
		passengers.remove(index); //Remove by index
	}
	
	///	END		| PASSENGER MANAGEMENT
    
    /// START	| SINGLETON DESIGN PATTERN + THREAD MANAGEMENT
     
    public static LiftModel getInstance() {
    	if(INSTANCE == null) {
    		throw new AssertionError("Lift Model Instance must be initialised.");
    	}
        return INSTANCE;
    }
    

    public synchronized static LiftModel initialise(int maxWeight, int minFloor, int maxFloor) {
        if (INSTANCE != null)
        {
            throw new AssertionError("Lift Model Instance already initialised.");
        }
        
        INSTANCE = new LiftModel(maxWeight, minFloor, maxFloor);
        TextView.print("Lift Initialised.");
        
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
	public void doAction(Object obj) {
		this.liftState.doAction(obj);
	}
	
	///	END		| LIFT STATE MANAGER DESIGN PATTERN

}
