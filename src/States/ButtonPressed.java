package States;

import Subjects.Button;
import Views.TextView;

public class ButtonPressed implements State, ButtonState {
	
	private Button b;
	
	public ButtonPressed(Button b) {
		this.b = b;
	}

	public void doAction(boolean running, Object obj) {
		TextView.print("Button Not Pressed. Floor: " + ((Button) b).getButtonFloor());
			
	}
}
