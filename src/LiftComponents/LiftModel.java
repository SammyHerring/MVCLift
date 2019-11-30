package LiftComponents;
import java.util.ArrayList;
import java.util.List;

import LiftStates.*;
import Subjects.*;

public final class LiftModel implements Subject, LiftState {
	
	private int currentFloor;
	private boolean doorOpen;
	private int maxWeight;
	private int minFloor;
	private int maxFloor;
	private ArrayList passengers;
	
    private static LiftModel INSTANCE;
   
	private List<Observer> observers;
	private final Object MUTEX= new Object();
	private String message;
	private boolean changed;
	
	private LiftState liftState;
     
    private LiftModel(int maxWeight, int minFloor, int maxFloor) {
    	LiftState liftInitState = new LiftInit();
    	LiftState liftStartState = new LiftStart();
    	LiftState liftMovingState = new LiftMoving();
    	LiftState liftEndState = new LiftEnd();
    	
    	this.maxWeight = maxWeight; //To be used for program extension
    	this.minFloor = minFloor;
    	this.maxFloor = maxFloor;
    	
    	this.setState(liftInitState);
    	this.doAction(this);
    	
    	this.passengers = new ArrayList<>();
    	
    	this.observers = new ArrayList<>();
    }
    
	public void changeFloor(int destinationFloor) {
		if (this.currentFloor == destinationFloor) {
			//do nothing else - change floor
		}
	}
    
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
        System.out.println("Lift Initialised.");
        
        return INSTANCE;
    }
    
    ///	END		| SINGLETON DESIGN PATTERN + THREAD MANAGEMENT
    
    /// START	|	ACCESSOR & MUTATOR METHODS
    
    public int getCurrentFloor() { return this.currentFloor; }
    public void setCurrentFloor(int floor) { this.currentFloor = floor; }
    
    public boolean getDoorOpen() { return this.doorOpen; }
    public void setDoorOpen (boolean doorOpen) { this.doorOpen = doorOpen; }
    
    public int maxWeight() { return this.maxWeight; }
    
    public int minFloor() { return this.minFloor; }
    
    public int maxFloor() { return this.maxFloor; }
    
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
	public Object getUpdate(Observer obj) {
		return this.message;
	}
	
	//method to post message to the topic
	public void postUpdate(String msg){
		this.message=msg;
		this.changed=true;
		notifyObservers();
	}
	
	///	END		| SUBJECT DESIGN PATTERN
	
	///	START	|	LIFT STATE MANAGER DESIGN PATTERN

	public void setState(LiftState state) {
		this.liftState = state;
	}

	public LiftState getState() {
		return this.liftState;
	}

	@Override
	public void doAction(LiftModel m) {
		this.liftState.doAction(m);
	}
	
	///	END		| LIFT STATE MANAGER DESIGN PATTERN

}
