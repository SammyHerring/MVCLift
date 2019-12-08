package CustomDataTypes;

import java.util.Comparator;

public class ScenarioFloorComparator implements Comparator<ScenarioFloor> {
	
	//Custom Scenario Floor Comparator for Custom Sort Method
	//Scenario instances sorted by, instance (time of event), source floor and destination floor
	//If all values comparator values are equal, the order used is the one in which floors are received
	//This allows for simultaneous thread creation when necessary with more complex scenarios
	
	@Override
	public int compare(ScenarioFloor f1, ScenarioFloor f2) {
		
		int comparator1 = f1.getInstance().compareTo(f2.getInstance());
		
		if (comparator1 == 0) {
			
			int comparator2 = f1.getFloorStart().compareTo(f2.getFloorStart());
			
			if (comparator2 == 0) {
				
				return f1.getFloorEnd().compareTo(f2.getFloorEnd());
				
			} else {
				
				return comparator2;
				
			}
		}
		
		return comparator1;
	}
}
