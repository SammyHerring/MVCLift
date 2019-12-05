package States;

public interface PersonState {
	
	public enum PersonAction {
	    WAITING, CALLING
	}
	
	public PersonAction getPersonAction();
	
	public void setPersonAction(PersonAction personAction);
	
}
