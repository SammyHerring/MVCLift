package States;

import LiftComponents.LiftModel;

public class ButtonUnpressed implements State {

	public void doAction(LiftModel m) {
		System.out.println("Button Not Pressed");
	}
	
}
