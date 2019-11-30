package ButtonStates;

import Subjects.*;

public class ButtonUnpressed implements ButtonState {

	public void doAction(Button b) {
		System.out.println("Button Not Pressed");
	}
	
}
