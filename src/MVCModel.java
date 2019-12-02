import LiftComponents.*;

public class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		
		System.out.println("---\tLift Simulator Program\t---");
		System.out.println("---\tSimulator Initialising\t---\n");
		LiftModel.initialise(1000, 0, 1); //Initialisation Parameters --> maxWeight, minFloor, maxFloor
		System.out.println("\n--\tSimulator Initialised\t---\n");
		
		LiftModel m = LiftModel.getInstance();
		LiftView v = new LiftView(m);
		new LiftController(v, m);
		
	}
}
