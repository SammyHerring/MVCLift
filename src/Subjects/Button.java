package Subjects;

import java.util.ArrayList;
import java.util.List;

import States.*;
import Views.ControllerView;

public class Button implements Subject, State {
	
	private ControllerView c;
	
	private int buttonFloor;
	
	private List<Observer> observers;
	private final Object MUTEX = new Object();
	private boolean changed;
	
	private State buttonState;
	public final State buttonUnpressedState;
	public final State buttonPressedState;
	
	public Button(Integer buttonFloor) {
		
		this.c = c;
		
		this.buttonFloor = buttonFloor;
		
		this.buttonUnpressedState = new ButtonUnpressed(this);
		this.buttonPressedState = new ButtonPressed(this);
		
		this.observers = new ArrayList<>();
		
		this.postUpdate(buttonUnpressedState);
	}
	
    /// START	|	ACCESSOR & MUTATOR METHODS
    
    public Integer getButtonFloor() { return this.buttonFloor; }
    
    public boolean pushButtonSuccess() {
    	if (buttonState != buttonPressedState) {
        	this.postUpdate(this.buttonPressedState);
        	return true;
    	}
    	return false;
    }
    
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
		synchronized (MUTEX) {
			return this.buttonState;
		}
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
	public void doAction(boolean running, Object obj) {
		this.buttonState.doAction(running, obj);
	}
	
	///	END		| LIFT STATE MANAGER DESIGN PATTERN
	
	
	

}
