package project.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import project.core.AbstractHost;
import project.core.AbstractProject;

public class ProjectHandler {

	private AbstractProject project;
	
	private static ProjectHandler projectHandler;
	
	private static boolean saved = true;
	
	/** Singleton implementation for the ProjectHandler */
	private ProjectHandler() {
	}

	public static ProjectHandler getInstance() {
		if( projectHandler == null )  
			projectHandler = new ProjectHandler();
		
		return projectHandler;
	}
	/****************************************************/
	
	public boolean newProject() {
		
		String projectName = JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), "Insert the project's name:", "New Project", JOptionPane.QUESTION_MESSAGE);
		
		if( projectName == null ) {
			return false;
		}
		
		while( projectName.equals("") ) {
			projectName = JOptionPane.showInputDialog(GuiManager.getInstance().getFrame(), "Insert a valid name:", "New Project", JOptionPane.QUESTION_MESSAGE);
			if( projectName == null ) 
				return false;
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the project's directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showDialog( GuiManager.getInstance().getFrame(), "Select" );
		
		if( choose == JFileChooser.APPROVE_OPTION ) {
			String dir = fc.getSelectedFile().getAbsolutePath();

			project = GFactory.getInstance().createProject(projectName, dir + "/" + projectName);
			
			createDirectory(projectName, dir);
			
			String content = "# 'lab.conf' created by NetKit GUI\n\n";
			
			createFile("lab.conf", project.getDirectory(), content);
			
			GuiManager.getInstance().setProject(project);
			
			saved = false;
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean saveProject() {
		if( project == null ) {
			JOptionPane.showMessageDialog(null, "No project to save", "No Project", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		String projDir = project.getDirectory();
		Collection<AbstractHost> hosts = project.getHosts();
		
		for( AbstractHost host : hosts ) {
			String name = host.getName();
			createDirectory( name, projDir );
			String content = "# '" + name + ".startup' created by NetKit GUI\n\n";
			createFile( name + ".startup", projDir, content );
		}
		
		saved = true;
		
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

	public void setSaved(boolean saved) {
		ProjectHandler.saved = saved;
	}

	public boolean isSaved() {
		return saved;
	}
}

