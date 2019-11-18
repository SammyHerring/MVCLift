public class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		
		System.out.print("---\tLift Simulator\t---");
		
		LiftView v = new LiftView();
		LiftModel m = LiftModel.getInstance();
		LiftController c = new LiftController(v, m);
		
	}
}
