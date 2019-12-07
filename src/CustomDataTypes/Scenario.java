package CustomDataTypes;
import java.util.List;

import States.*;

public class Scenario {
	
	private List<ScenarioFloor> floors; //Floor scenarios per instance
	
	public Scenario(List<ScenarioFloor> floors, State state) {
		this.floors = floors;
	}

	public List<ScenarioFloor> getFloors() {
		return floors;
	}
}
