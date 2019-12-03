import java.util.ArrayList;
import java.util.List;

import Views.TextView;
import LiftComponents.*;
import Subjects.Button;

public class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		
		TextView.print("---\tLift Simulator Program\t---");
		TextView.print("---\tSimulator Initialising\t---\n", true);
		
		LiftModel.initialise(1000, 0, 1); //Initialisation Parameters --> maxWeight, minFloor, maxFloor
		LiftModel m = LiftModel.getInstance();
		LiftView v = new LiftView(m);
		
		//Generate list of Buttons for Model
		List<Button> b = new ArrayList<>();
		
		//Generate buttons for the number of existing floors
		for (int floor = 0; floor < m.floorCount(); floor=floor+1) {
			TextView.print("Button Initialised. Floor: " + floor);
			b.add(new Button(floor));
		}
		
		TextView.print("--\tSimulator Initialised\t---", true);
		
		//Start simulation from Lift Controller
		TextView.print("--\tSimulation Starting\t---", true);
		new LiftController(v, m, b);
		
	}
}
