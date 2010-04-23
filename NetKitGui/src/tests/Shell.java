package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Shell {
	
	static Runtime rnt;
	static Process proc;
	static File currentDir;
	static BufferedReader in;
	static PrintWriter out;
	
	public static void main(String[] args) throws IOException, InterruptedException {
//		if( execute("cd /home/sal/lab") ) {
//			System.out.println("eseguito");
//		execute("cd /home/sal/lab");
			execute("");
//			execute("exit");
//		execute("ls");
//			String s = "";
//			while( (s = in.readLine()) != null ) {
//				System.out.println(s);
//			}
//			System.out.println("buffer finito");
//			execute("exit");
//		}
	}
	
	public static boolean execute( String command ) throws IOException {
		if( rnt == null ) {
			rnt = Runtime.getRuntime();
		}
		if( proc == null ) {
			System.out.println("creating proc");
			if( currentDir == null ) 
				currentDir = new File("/home/sal/lab");
			try {
				proc = rnt.exec(command, null, currentDir);
				out = new PrintWriter( proc.getOutputStream() );
				in = new BufferedReader( new InputStreamReader( proc.getInputStream() ) );
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		
//		out.println(command);
//		out.flush();
		
		if( command.equals("exit") ) {
			in.close();
			out.close();
			proc.destroy();
			proc = null;
		}
//		String s = "";
//		while( (s = in.readLine()) != null ) {
//			System.out.println(s);
//		}
		return true;
	}
}
