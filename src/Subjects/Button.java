package Subjects;

public class Button implements Observer{
	
	private Subject model;
	
	public Button() {
		
	}
	
	@Override
	public void update() {
		String msg = (String) model.getUpdate(this);
		
		if(msg == null)	{
			System.out.println("Button"+":: No new message");
		} else {
			
		System.out.println("Button"+":: Consuming message::"+msg);
		}
	}

	@Override
	public void setSubject(Subject sub) {
		this.model=sub;
	}
}
