package Subjects;

import States.State;

public interface Subject {	
	//methods to register and unregister observers
	public void register(Observer obj);
	public void unregister(Observer obj);
		
	//method to notify observers of change
	public void notifyObservers();
		
	//method to get updates from subject
	public State getUpdate(Observer obj);
	
	//method to post state update to observer
	public void postUpdate(State state);
}
