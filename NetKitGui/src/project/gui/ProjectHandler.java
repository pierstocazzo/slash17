package project.gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import project.core.AbstractHost;
import project.core.AbstractInterface;
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
			GuiManager.getInstance().setProject(project);
			
			saved = false;
			
			return true;
		} else {
			return false;
		}
	}
	
	public boolean saveProject() {
		if( project == null ) {
			return false;
		}
		
		String projDir = project.getDirectory();
		String projName = project.getName();
		Collection<AbstractHost> hosts = project.getHosts();
		
		createDirectory(projName, projDir);
		
		String labConfcontent = "# 'lab.conf' created by NetKit GUI\n\n";
		
		for( AbstractHost host : hosts ) {
			String hostName = host.getName();
			createDirectory( hostName, projDir );
			
			String startupContent = "# '" + hostName + ".startup' created by NetKit GUI\n\n";
			
			for( AbstractInterface iface : host.getInterfaces() ) {
				
				String ifaceName =  iface.getName();
				String ip = iface.getIp();
				String mask = iface.getMask();
				String bcast = iface.getBCast();
				String cdName = iface.getCollisionDomain().getName();
				
				labConfcontent += hostName + "[" + ifaceName + "]=\"" + cdName + "\"\n";
				
				if( ip != null && mask != null && bcast != null ) {
					startupContent += "ifconfig " + ifaceName + " " + ip + " netmask " + 
									mask + " broadcast " + bcast + " up\n";
				} else {
					startupContent += "ifconfig " + ifaceName + " up # not configured \n";
				}
			}
			labConfcontent += "\n";
			
			
			createFile( hostName + ".startup", projDir, startupContent );
		}
		
		createFile("lab.conf", project.getDirectory(), labConfcontent);
		
		saved = true;
		
		return true;
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

	private String createDirectory( String name, String topDirectory ) {
		File proj = new File( topDirectory + "/" + name );
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
}

