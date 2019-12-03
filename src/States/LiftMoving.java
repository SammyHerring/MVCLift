package States;

import LiftComponents.LiftModel;
import Views.TextView;

public class LiftMoving implements State {
	
	@Override
	public void doAction(LiftModel m) {
		TextView.print("Lift Moving");
	}
	
}