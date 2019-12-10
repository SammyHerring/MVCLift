package CustomDataTypes;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import States.*;

public class Scenario {
	
	private final Integer ID;
	private static AtomicInteger uniqueId=new AtomicInteger();
	
	private List<ScenarioFloor> floors; //Floor scenarios per instance
	
	public Scenario(List<ScenarioFloor> floors, State state) {
		
		this.ID = uniqueId.getAndIncrement();
		
		this.floors = floors;
	}
	
    /// START	|	ACCESSOR & MUTATOR METHODS
	
	public Integer getID() { return this.ID; }

	public List<ScenarioFloor> getFloors() { return floors; }
	
    /// END	|	ACCESSOR & MUTATOR METHODS
	
}
