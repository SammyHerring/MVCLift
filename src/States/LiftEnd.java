package States;

import Views.TextView;

public class LiftEnd implements State {
	
	@Override
	public void doAction() {
		TextView.print("Lift Journey Ended");
		TextView.print("Release passengers. Wait. Close doors. End Floor.");
	}
	
}
