package States;

import Views.TextView;

public class LiftMoving implements State {
	
	@Override
	public void doAction() {
		TextView.print("Lift Moving");
	}
	
}