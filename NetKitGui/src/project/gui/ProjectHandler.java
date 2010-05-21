package project.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import project.core.nodes.AbstractHost;
import project.core.project.AbstractProject;

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
			GuiManager.getInstance().setProject(project);
			
			saved = false;
			return true;
		} 
		return false;
	}
	
	public void saveProject() {
		if( project != null ) {
			String projDir = project.getDirectory();
			
			createDirectory(projDir);
			
			String labConfcontent = project.getLabConfFile();
			
			for( AbstractHost host : project.getHosts() ) {
				String hostName = host.getName();
				createDirectory( projDir + "/" + hostName );
				
				String startupContent = host.getStartupFile();
				
				createFile( hostName + ".startup", projDir, startupContent );
			}
			
			createFile( "lab.conf", project.getDirectory(), labConfcontent );
			
			saved = true;
		
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Project Saved");
		} else {
			JOptionPane.showMessageDialog(GuiManager.getInstance().getFrame(), "Unable to save the project", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void createFile( String fileName, String projDir, String content ) {
		File f = new File( projDir + "/" + fileName );
		if( f.exists() ) {
			f.delete();
			try {
				f.createNewFile();
			} catch (IOException e) {
			}
		}
		
		try {
			PrintWriter out = new PrintWriter(f);
			
			out.println( content );
			out.flush();
			
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String createDirectory( String directory ) {
		File proj = new File( directory );
		if( !proj.exists() ) 
			proj.mkdirs();
		
		String dir = proj.getAbsolutePath();
		return dir;
	}

	public void setSaved(boolean saved) {
		ProjectHandler.saved = saved;
	}

	public boolean isSaved() {
		return saved;
	}

	public void openProject() {
		// TODO open project
		
	}
}

