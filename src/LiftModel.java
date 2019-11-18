
public final class LiftModel {
	
	private int currentFloor;
	private boolean doorState;
	//Attributes for program extension
	private int maxWeight;
	private int minFloor;
	private int maxFloor;
	
    private static LiftModel INSTANCE;
     
    private LiftModel() {        
    }
     
    public static LiftModel getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new LiftModel();
        }
         
        return INSTANCE;
    }

}
