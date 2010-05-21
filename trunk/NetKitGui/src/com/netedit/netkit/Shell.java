package com.netedit.netkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import com.netedit.core.project.AbstractProject;
import com.netedit.gui.GuiManager;


public class Shell {
	
	static Runtime rnt;
	static Process proc;
	static File currentDir;
	static BufferedReader in;
	static PrintWriter out;
	
	public static void startLab( AbstractProject project ) {
		try {
			if( rnt == null ) {
				rnt = Runtime.getRuntime();
			} 
			if( project != null && isDirectory( project.getDirectory() ) ) 
				rnt.exec("sh startlab " + project.getDirectory() );
			else 
				JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), 
						"Save the project before start", "Error", JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void stopLab( AbstractProject project, boolean crash ) {
		try {
			if( rnt == null ) {
				rnt = Runtime.getRuntime();
			} 
			if( project != null && isDirectory( project.getDirectory() ) )
				rnt.exec("sh stoplab " + project.getDirectory() + " " + crash);
			else 
				JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), 
						"Save the project before stop", "Error", JOptionPane.ERROR_MESSAGE);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean isDirectory(String directory) {
		File f = new File(directory);
		return f.isDirectory();
	}
}
