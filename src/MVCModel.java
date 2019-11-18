public final class MVCModel {
	
	//MVC Model | System Entry point
	
	public static void main(String[] args) {
		System.out.print("---\tLift Simulator\t---");
	}
	
    private static MVCModel INSTANCE;
    private String info = "Initial info class";
     
    private MVCModel() {        
    }
     
    public static MVCModel getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new MVCModel();
        }
         
        return INSTANCE;
    }

}
