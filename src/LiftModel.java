import java.util.ArrayList;
import java.util.List;

import Subjects.*;

public final class LiftModel implements Subject {
	
	private int currentFloor;
	private boolean doorOpen;
	//Attributes for program extension
	private int maxWeight;
	private int minFloor;
	private int maxFloor;
	
    private static LiftModel INSTANCE;
   
	private List<Observer> observers;
	private final Object MUTEX= new Object();
	private String message;
	private boolean changed;
     
    private LiftModel(int maxWeight, int minFloor, int maxFloor) {
    	this.currentFloor = 0;
    	this.doorOpen = false;
    	this.maxWeight = maxWeight; //To be used for program extension
    	this.minFloor = minFloor;
    	this.maxFloor = maxFloor;
    	
    	this.observers = new ArrayList<>();
    }
     
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
    
    /// START	|	ACCESSOR & MUTATOR METHODS
    public int getCurrentFloor() { return this.currentFloor; }
    public void setCurrentFloor(int floor) { this.currentFloor = floor; }
    
    public boolean getDoorOpen() { return this.doorOpen; }
    public void setDoorOpen (boolean doorOpen) { this.doorOpen = doorOpen; }
    
    public int maxWeight() { return this.maxWeight; }
    
    public int maxFloor() { return this.maxFloor; }
    
    public int minFloor() { return this.minFloor(); }
    /// END		|	ACCESSOR & MUTATOR METHODS
    
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
	public void postMessage(String msg){
		System.out.println("Message Posted to Topic:"+msg);
		this.message=msg;
		this.changed=true;
		notifyObservers();
	}

}
