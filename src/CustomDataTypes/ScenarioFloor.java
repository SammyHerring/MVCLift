package CustomDataTypes;

public class ScenarioFloor {
	
	private int people;
	private int floorStart;
	private int floorEnd;
	private int instance;
	
	public ScenarioFloor(int people, int floorStart, int floorEnd, int instance) {
		this.people = people;
		this.floorStart = floorStart;
		this.floorEnd = floorEnd;
		this.instance = instance;
	}

	public int getPeople() { return people; }

	public int getFloorStart() { return floorStart; }

	public int getFloorEnd() { return floorEnd; }

	public int getInstance() { return instance; }

}
