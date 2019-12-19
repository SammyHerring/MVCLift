package CustomDataTypes;

public class Generic {
	public static String convertToTitleCase(String text) {

		if (text == null || text.isEmpty()) {
			return text;
		}

		StringBuilder titleString = new StringBuilder();

		boolean convertNext = true;
		
		for (char c : text.toCharArray()) {
			
			if (Character.isSpaceChar(c)) {
				
				convertNext = true;
				
			} else if (convertNext) {
				
				c = Character.toTitleCase(c);
				convertNext = false;
						
			} else {
				
				c = Character.toLowerCase(c);
				
			}
			
			titleString.append(c);
		}
		return titleString.toString();
	}
}
