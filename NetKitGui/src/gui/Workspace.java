package gui;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Workspace {

	protected static String projectDirectory;
	
	public static void newProject( Component c ) {
		
		String projectName = JOptionPane.showInputDialog(c, "Insert the project's name:", "New Project", JOptionPane.QUESTION_MESSAGE);
		
		if( projectName == null ) {
			return;
		}
		
		while( projectName.equals("") ) {
			projectName = JOptionPane.showInputDialog(c, "You must insert the project's name:", "New Project", JOptionPane.QUESTION_MESSAGE);
		}
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Select the project's directory");
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setAcceptAllFileFilterUsed(false);
		int choose = fc.showDialog( c, "Select" );
		
		if( choose == JFileChooser.APPROVE_OPTION ) {
			String dir = fc.getSelectedFile().getAbsolutePath();
			
			File proj = new File(dir + "/" + projectName);
			proj.mkdir();
			projectDirectory = proj.getAbsolutePath();
			System.out.println("Project's main directory: " + projectDirectory);
			
			File labconf = new File(projectDirectory + "/lab.conf");
			
			try {
				PrintWriter out = new PrintWriter(labconf);
				
				out.println("# \"lab.conf\" created by NetKit GUI\n" +
						"# website: http://slash17.googlecode.com");
				out.flush();
				
				out.close();
				
			} catch (FileNotFoundException e) {
			}
		}
	}
}

