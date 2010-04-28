package gui;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import core.Host;
import core.Project;

public class Workspace {

	Project project;
	
	public Workspace( Project project ) {
		this.project = project;
	}

	public boolean newProject( Component c ) {
		
		String projectName = JOptionPane.showInputDialog(c, "Insert the project's name:", "New Project", JOptionPane.QUESTION_MESSAGE);
		
		if( projectName == null ) {
			return false;
		}
		
		while( projectName.equals("") ) {
			projectName = JOptionPane.showInputDialog(c, "Insert a valid name:", "New Project", JOptionPane.QUESTION_MESSAGE);
			if( projectName == null ) 
				return false;
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the project's directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showDialog( c, "Select" );
		
		if( choose == JFileChooser.APPROVE_OPTION ) {
			String dir = fc.getSelectedFile().getAbsolutePath();

			project.setName(projectName);
			project.setDirectory(dir + "/" + projectName);
			
			createDirectory(projectName, dir);
			
			String content = "# 'lab.conf' created by NetKit GUI\n\n";
			
			createFile("lab.conf", project.getDirectory(), content);
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean saveProject( Component c ) {
		Collection<Host> hosts = project.getHosts();
		String projDir = project.getDirectory();
		
		if( projDir.equals("notsetted") ) {
			if( !newProject(c) ) {
				JOptionPane.showMessageDialog(c, "Error: you must create the project before save it");
				return false;
			}
		}
		
		projDir = project.getDirectory();
		
		for( Host host : hosts ) {
			String name = host.getName();
			createDirectory( name, projDir );
			String content = "# '" + name + ".startup' created by NetKit GUI\n\n";
			createFile( name + ".startup", projDir, content );
			
		}
		
		return true;
	}

	private void createFile( String fileName, String projDir, String content ) {
		File f = new File( projDir + "/" + fileName );
		
		try {
			PrintWriter out = new PrintWriter(f);
			
			out.println( content );
			out.flush();
			
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String createDirectory( String name, String topDirectory ) {
		File proj = new File( topDirectory + "/" + name );
		proj.mkdir();
		return proj.getAbsolutePath();
	}
}

