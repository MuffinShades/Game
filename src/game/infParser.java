package game;

public class infParser {
	public static String cnt = "";
	
	public static String GetValue(int id, String value, String infFile) {
		String res = "";
		cnt = infReader.ReadFile(infFile);
		String[] spl = cnt.split("\n");
		
		boolean readHere = false;
		for (int i = 0; i < spl.length; i++) {
			//detect start
			
			if (spl[i].startsWith("["+id+"]")) {
				readHere = true;
			}
			
			if (readHere) {
				String v = spl[i].replaceAll("\\t", "");
				
				if (v.indexOf("=")>=0&&v.toLowerCase().split("=")[0].equalsIgnoreCase(value.toLowerCase())) {
					System.out.println("!");
					res = v.toLowerCase().split("=")[1];
					break;
				}
			}
		}
		return res;
	}
}
