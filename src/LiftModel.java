
public final class LiftModel {
	
	private int currentFloor;
	private boolean doorOpen;
	//Attributes for program extension
	private int maxWeight;
	private int minFloor;
	private int maxFloor;
	
    private static LiftModel INSTANCE;
     
    private LiftModel() {
    	this.currentFloor = 0;
    	this.doorOpen = false;
    	this.maxWeight = 1000; //To be used for program extension
    	this.minFloor = 0;
    	this.maxFloor = 1;
    }
     
    public static LiftModel getInstance() {
    	if(INSTANCE == null) {
    		throw new AssertionError("Lift Model Instance must be initialised.");
    	}
        return INSTANCE;
    }
    

    public synchronized static LiftModel initialise() {
        if (INSTANCE != null)
        {
            throw new AssertionError("Lift Model Instance already initialised.");
        }
        
        INSTANCE = new LiftModel();
        System.out.println("Lift Initialised.");
        
        return INSTANCE;
    }

}
