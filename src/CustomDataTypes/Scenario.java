package CustomDataTypes;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import States.*;

public class Scenario {
	
	private final Integer ID;
	private static AtomicInteger uniqueId=new AtomicInteger();
	
	private List<ScenarioFloor> floors; //Floor scenarios per instance
	private int startFloor;
	
	public Scenario(List<ScenarioFloor> floors, int startFloor) {
		
		this.ID = uniqueId.getAndIncrement();
		this.floors = floors;
		this.startFloor = startFloor;
	}
	
    /// START	|	ACCESSOR & MUTATOR METHODS
	
	public Integer getID() { return this.ID; }

	public List<ScenarioFloor> getFloors() { return floors; }
	
	public int getStartFloor() { return startFloor; }
	
    /// END	|	ACCESSOR & MUTATOR METHODS
	
}
