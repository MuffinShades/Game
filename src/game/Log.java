package game;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
	private static File p = new File("");
	public final static String log_path = p.getAbsolutePath()+"\\runtime.log";
	
	public static void init() {
		try {
			FileWriter f = new FileWriter(log_path);
			f.write("Loading Application... \n");
			f.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void print(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		
		LocalDateTime now = LocalDateTime.now();
		
		String time = dtf.format(now);
		
		String pd = "{" + time + "} | [inf] | "+data;
		
		System.out.println(pd);
		
		try {
			PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(log_path, true)));
			fw.println(pd);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void print(String data, String outType) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
		
		LocalDateTime now = LocalDateTime.now();
		
		String time = dtf.format(now);
		
		String pd = "{" + time + "} | ["+outType+"] | "+data;
		
		//System.out.println(pd);
		
		try {
			PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(log_path, true)));
			fw.println(pd);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
