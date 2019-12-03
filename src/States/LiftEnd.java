package States;

import LiftComponents.LiftModel;
import Views.TextView;

public class LiftEnd implements State {
	
	@Override
	public void doAction(LiftModel m) {
		TextView.print("Lift Journey Ended");
		TextView.print("Release passengers. Wait. Close doors. End Floor.");
	}
	
}
