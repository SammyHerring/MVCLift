package Subjects;

import java.util.ArrayList;
import java.util.List;

import ButtonStates.*;

public class Button implements Subject, ButtonState {
	
	private int buttonFloor;
	private ButtonState buttonState;
	
	private List<Observer> observers;
	private final Object MUTEX = new Object();
	private String message;
	private boolean changed;
	
	public Button() {
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
	public Object getUpdate(Observer obj) {
		return this.message;
	}
	
	//method to post message to the topic
	public void postUpdate(String msg){
		this.message=msg;
		this.changed=true;
		notifyObservers();
	}
	
    ///	END		|	SUBJECT DESIGN PATTERN

	///	START	|	LIFT STATE MANAGER DESIGN PATTERN

	public void setState(ButtonState state) {
		this.buttonState = state;
	}

	public ButtonState getState() {
		return this.buttonState;
	}

	@Override
	public void doAction(Button b) {
		this.buttonState.doAction(b);
		
		//Does the button need access to the model???
	}
	
	///	END		| LIFT STATE MANAGER DESIGN PATTERN
	
	
	

}
