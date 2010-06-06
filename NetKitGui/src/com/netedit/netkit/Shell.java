/**
 * jNetEdit - Copyright (c) 2010 Salvatore Loria
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */

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
	
	public static void startLab( AbstractProject project, boolean parallel ) {
		try {
			if( rnt == null ) {
				rnt = Runtime.getRuntime();
			} 
			if( project != null && isDirectory( project.getDirectory() ) ) 
				rnt.exec("sh startlab " + project.getDirectory() + " " + parallel);
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
