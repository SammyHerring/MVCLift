package States;

import Subjects.Button;
import Views.TextView;

public class ButtonUnpressed implements State, ButtonState {
	
	private Button b;
	
	public ButtonUnpressed(Button b) {
		
		this.b = b;
		
	}

	public void doAction(boolean running, Object obj) {
		
		TextView.print("Button\t\tFloor: " + b.getButtonFloor() + "\t|\tState: Not Pressed");
		
	}
}
