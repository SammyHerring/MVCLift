package States;

import LiftComponents.LiftModel;

public class ButtonPressed implements State {

	public void doAction(LiftModel m) {
		System.out.println("Button  Pressed");
	}
	
}
