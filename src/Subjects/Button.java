package Subjects;

import java.util.ArrayList;
import java.util.List;

import States.*;

public class Button implements Subject, State {
	
	private int buttonFloor;
	
	private List<Observer> observers;
	private final Object MUTEX = new Object();
	private boolean changed;
	
	private State buttonState;
	
	public Button(int buttonFloor) {
		this.buttonFloor = buttonFloor;
		
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
		return this.buttonState;
	}
	
	@Override
	public void postUpdate(State buttonState){
		this.buttonState=buttonState;
		this.changed=true;
		notifyObservers();
	}
	
    ///	END		|	SUBJECT DESIGN PATTERN

	///	START	|	LIFT STATE MANAGER DESIGN PATTERN

	public void setState(State state) {
		this.buttonState = state;
	}

	public State getState() {
		return this.buttonState;
	}

	@Override
	public void doAction() {
		this.buttonState.doAction();
		
		//Does the button need access to the model???
	}
	
	///	END		| LIFT STATE MANAGER DESIGN PATTERN
	
	
	

}
