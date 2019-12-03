import java.util.ArrayList;
import java.util.List;

import LiftComponents.*;
import Subjects.Button;

public class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		
		System.out.println("---\tLift Simulator Program\t---");
		System.out.println("---\tSimulator Initialising\t---\n");
		
		LiftModel.initialise(1000, 0, 1); //Initialisation Parameters --> maxWeight, minFloor, maxFloor
		LiftModel m = LiftModel.getInstance();
		LiftView v = new LiftView(m);
		
		//Generate list of Buttons for Model
		List<Button> b = new ArrayList<>();
		//Generate buttons for the number of existing floors
		for (int floor = 0; floor < m.floorCount(); floor=floor+1) {
			System.out.println("Button Initialised. Floor: " + floor);
			b.add(new Button(floor));
		}
		
		System.out.println("\n--\tSimulator Initialised\t---\n");
		
		//Start simulation from Lift Controller
		new LiftController(v, m, b);
		
	}
}
