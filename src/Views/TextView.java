package Views;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextView {
	
	//Overloading Methods for Print to support optional arguments
	public static void print(String text) {
		System.out.println(getCurrentTimeStamp()+"\t\t"+text);
		
	}
	
	public static void print(Integer text) {
		System.out.println(getCurrentTimeStamp()+"\t\t"+text.toString());
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
	
	//Print with no new line (nl)
	public static void printnl(String text) {
		System.out.print(getCurrentTimeStamp()+"\t\t"+text);
	}
	
	//Print with no new line (nl) and no timestamp (nt)
	public static void printnlnt(String text) {
		System.out.print(text);
	}
	
	//Overloading Methods for Error Printing
	public static void printError(String text) {
		print("[ERROR]\t" + text);
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
