package LiftComponents;
import Subjects.*;

public class LiftView implements Observer {
	
	private Subject subject;
	private LiftModel m;
	
	public LiftView(LiftModel m) {
		this.m = m;
		
		setSubject(m); //Set observer subject to Lift Model by default
	}

	@Override
	public void update() {
		
		String msg = (String) subject.getUpdate(this);
		
		if(msg == null){
			//No Message with selected subject available
//			System.out.println("LiftView"+":: No new message");
		} else {
			System.out.println("LiftView"+"\t|\tConsuming message:\t"+msg);
		}
		
	}

	@Override
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

}
