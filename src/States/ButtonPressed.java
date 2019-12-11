package States;

import Subjects.Button;
import Views.TextView;

public class ButtonPressed implements State, ButtonState {
	
	private Button b;
	
	public ButtonPressed(Button b) {
		
		this.b = b;
		
	}

	public void doAction(boolean running, Object obj) {
		
		if (!running) { TextView.print("Button\t\tFloor: " + b.getButtonFloor() + "\t|\tState: Pressed"); }
			
	}
}
