package Subjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import States.State;

public class Person implements Subject {
	//Attributes for program extension
	private int weight;
	private int startFloor;
	private int endFloor;
	
	private List<Observer> observers;
	private final Object MUTEX = new Object();
	private boolean changed;
	
	private State personState;
	
	public Person(int startFloor, int endFloor) {
		
    	//Random Person Weight Generator 
    	//using Normal Distribution (Mean 1, Std. Deviation 1)
    	double mean = 1.0, std = 0.25;
    	Random rand = new Random();
    	double weightFloat = mean + std * rand.nextGaussian();
    	weightFloat *= 100;
    	this.weight = (int)weightFloat;
    	
		this.startFloor = startFloor;
		this.endFloor = endFloor;
		
		this.observers = new ArrayList<>();
	}
	
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
