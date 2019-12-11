package Views;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextView {
	
	private boolean preNL = false; //Before text new line carriage return
	
	//Overloading Methods for Print to support optional arguments
	public static void print(String text) {
		System.out.println(getCurrentTimeStamp()+"\t\t"+text);
	}
	
	public static void print(Boolean text) {
		System.out.println(getCurrentTimeStamp()+"\t\t"+text.toString());
	}
	
	public static void print(String text, boolean preNL) {
		if (preNL) {
			System.out.println("\n"+getCurrentTimeStamp()+"\t\t"+text);
		} else {
			System.out.println(getCurrentTimeStamp()+"\t\t"+text);
		}
	}
	
	//Standardised Error report print method
	public static void printError(String title, String text) {
		print("[" + title.toUpperCase() + " ERROR]\t" + text);
	}
	
	//Return string with current time stamp in required format
	private static String getCurrentTimeStamp() {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    Date now = new Date();
	    String strDate = dateFormat.format(now);
	    return strDate;
	}
	
}
