public class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		
		System.out.println("---\tLift Simulator Program\t---");
		System.out.println("---\tSimulator Initialising\t---\n");
		LiftModel.initialise(1000, 0, 1); //Init Parameters --> maxWeight, minFloor, maxFloor
		System.out.println("\n--\tSimulator Initialised\t---\n");
		
		LiftView v = new LiftView();
		LiftModel m = LiftModel.getInstance();
		LiftController c = new LiftController(v, m);
		
	}
}
