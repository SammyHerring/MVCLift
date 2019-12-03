package Views;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextView {
	
	public static void print(String text) {
		System.out.println(getCurrentTimeStamp()+"\t\t"+text);
	}
	
	
	private static String getCurrentTimeStamp() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = dateFormat.format(now);
	    return strDate;
	}
	
}
