package States;

import LiftComponents.LiftModel;
import Views.ControllerView;

public class LiftEndScenario implements State {

	private LiftModel m;
	private ControllerView c;

	public LiftEndScenario(LiftModel m, ControllerView c) {
		this.m = m;
		this.c = c;
	}

	@Override
	public synchronized void doAction(boolean running, Object obj) {
		//Do nothing. Class used to defined end state.
	}
}
