package netkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Shell {
	
	static Runtime rnt;
	static Process proc;
	static File currentDir;
	static BufferedReader in;
	static PrintWriter out;
	
	public static boolean startLab( String labDirectory ) {
		try {
			if( rnt == null ) {
				rnt = Runtime.getRuntime();
			} 
			rnt.exec("sh startlab");
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean stopLab( String labDirectory, boolean crash ) {
		try {
			if( rnt == null ) {
				rnt = Runtime.getRuntime();
			} 
			rnt.exec("sh stoplab");
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
