package Subjects;

public interface Subject {
	public void register (Observer o);
	public void unregister(Oberser o);
	public void notifyObserver();
}
