package LiftComponents;
import Subjects.*;

public class LiftView implements Observer {
	
	private Subject topic;
	
	public LiftView() {
		
	}

	@Override
	public void update() {
		String msg = (String) topic.getUpdate(this);
		if(msg == null){
			System.out.println("LiftView"+":: No new message");
		}else
		System.out.println("LiftView"+":: Consuming message::"+msg);
	}

	@Override
	public void setSubject(Subject sub) {
		this.topic=sub;
	}

}
