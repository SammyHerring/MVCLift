import java.util.ArrayList;
import java.util.List;

import Views.ControllerView;
import Views.TextView;
import LiftComponents.*;
import Subjects.Button;

public class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		
		ControllerView c = new ControllerView(); //GUI to Show Program Console
		
		TextView.print("---\tLift Simulator Program\t---");
		TextView.print("---\tSimulator Initialising\t---\n", true);
		
		LiftModel.initialise(1000, 0, 1, c); //Lift Initialisation Parameters --> maxWeight, minFloor, maxFloor
		LiftModel m = LiftModel.getInstance();
		
		//Generate list of Buttons for Model
		List<Button> b = new ArrayList<>();
		
		//Generate buttons for the number of existing floors
		for (int floor = 0; floor < m.floorCount(); floor=floor+1) {
			TextView.print("Button Initialised \t|\tFloor: " + floor);
			b.add(new Button(floor));
		}
		
		LiftView v = new LiftView(m, b); //Give view access to model and button subjects
		
		TextView.print("--\tSimulator Initialised\t---", true);
		
		//Start simulation from Lift Controller
		TextView.print("--\tSimulation Starting\t---", true);
		
		try {
			new LiftController(v, m, b, c);
		} catch (Exception ex) {
			TextView.printError("Lift Controller", "Interupt Related Error");
		}
		
		
	}
}
