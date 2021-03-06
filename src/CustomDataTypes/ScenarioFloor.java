package CustomDataTypes;

public class ScenarioFloor {
	
	private Integer people;
	private Integer floorStart;
	private Integer floorEnd;
	private Integer instance;
	
	public ScenarioFloor(Integer people, Integer floorStart, Integer floorEnd, Integer instance) {
		this.people = people;
		this.floorStart = floorStart;
		this.floorEnd = floorEnd;
		this.instance = instance;
	}

	public Integer getPeople() { return people; }

	public Integer getFloorStart() { return floorStart; }

	public Integer getFloorEnd() { return floorEnd; }

	public Integer getInstance() { return instance; }
	
	public String getInfo() {

		return "Scenario\tFloor: "+ this.floorStart + "\t|\tPeople: " + this.people + "\t\tFloor Start: "  +this.floorStart + "\tFloor End: " + this.floorEnd + "\tInstance: " + this.instance;
	}

}
