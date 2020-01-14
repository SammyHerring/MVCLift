package Subjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import LiftComponents.LiftModel;
import States.*;

public class Person implements Subject {

	private final Integer ID = uniqueId.getAndIncrement();
	private static AtomicInteger uniqueId=new AtomicInteger();
	
	private Integer weight;
	private Integer startFloor;
	private Integer endFloor;
	
	private LiftModel m;
	
	private List<Observer> observers;
	private final Object MUTEX = new Object();
	private boolean changed;
	
	private State personState;
	public final State personStartFloorState;
	
	public Person(Integer startFloor, Integer endFloor, LiftModel m) {
		
    	this.weight = weightFloat();
    	
		this.startFloor = startFloor;
		this.endFloor = endFloor;
		
		this.m = m;
		
		this.personStartFloorState = new PersonStartFloor(this);
		
		this.observers = new ArrayList<>();
		
		this.postUpdate(personStartFloorState);
	}
	
	private Integer weightFloat() {
		
    	//Random Person Weight Generator 
    	//using Normal Distribution (Mean 1, Std. Deviation 1)
    	double mean = 1.0, std = 0.25;
    	Random rand = new Random();
    	double weightFloat = mean + std * rand.nextGaussian();
    	weightFloat *= 100;
    	
    	return (int)weightFloat;
	}
	
    /// START	|	ACCESSOR & MUTATOR METHODS
	
	public Integer getID() { return this.ID; }
	
    public Integer getWeight() { return this.weight; }
    
    public Integer getStartFloor() { return this.startFloor; }
    
    public Integer getEndFloor() { return this.endFloor; }
    
    public LiftModel getLift() { return this.m; }
    
    public Person getPerson() { return this; }
    
    public State getState() { return this.personState; }
	
    /// END		|	ACCESSOR & MUTATOR METHODS
	
    ///	START	|	SUBJECT DESIGN PATTERN
	
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
		return this.personState;
	}
	
	@Override
	public void postUpdate(State personState){
		this.personState=personState;
		this.changed=true;
		notifyObservers();
	}
	
    ///	END		| 	SUBJECT DESIGN PATTERN
}
