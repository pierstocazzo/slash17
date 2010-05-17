package project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import project.common.ItemType;
import project.core.AbstractProject;
import project.gui.labview.ConfigurationPanel;

public class GuiManager {

	private static GuiManager gmanager;
	
	GFrame frame;
	ConfigurationPanel confPanel;
	JLabel emptyCanvas;
	GCanvas canvas;
	JSplitPane splitPane;
	AbstractProject project;
	
	/** Singleton implementation */
	private GuiManager() {}
	
	public static GuiManager getInstance() {
		if( gmanager == null ) 
			gmanager = new GuiManager();
		
		return gmanager;
	}
	/*****************************/
	
	public void startGui() {
		frame = new GFrame();
		
		confPanel = new ConfigurationPanel();
		
		emptyCanvas = new JLabel("Create a new project or open an existing one.");
		emptyCanvas.setHorizontalAlignment(SwingConstants.CENTER);
		emptyCanvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, emptyCanvas, confPanel);
		splitPane.setBorder( BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
	    splitPane.setResizeWeight(1D);
		frame.add(splitPane, BorderLayout.CENTER);
		
		frame.validate();
	}
	
	public void setProject( AbstractProject project ) {
		this.project = project;
		confPanel.setProject( project );
		
		canvas = new GCanvas(frame, project, confPanel);
		canvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		canvas.setPreferredSize(new Dimension((int) (frame.getSize().getWidth()*0.8), (int) (frame.getSize().getHeight()*0.8)));
		
		splitPane.setLeftComponent(canvas);
		frame.setCanvas(canvas);
		
		
		frame.validate();
	}
	
	public void update() {
		frame.validate();
		confPanel.validate();
		canvas.validate();
		
		if( canvas != null ) {
			confPanel.update();
		}
	}

	public ConfigurationPanel getConfPanel() {
		return confPanel;
	}
	
	public GCanvas getCanvas() {
		return canvas;
	}
	
	public GFrame getFrame() {
		return frame;
	}

	public AbstractProject getProject() {
		return project;
	}

	public void adding(ItemType type) {
		if( canvas != null ) 
			canvas.adding(type);
		else 
			JOptionPane.showMessageDialog( frame, "Create the project before", 
					"Error: no project", JOptionPane.ERROR_MESSAGE);
	}
	
	public void deleting() {
		if( canvas != null ) 
			canvas.deleting();
		else 
			JOptionPane.showMessageDialog( frame, "Create the project before", 
					"Error: no project", JOptionPane.ERROR_MESSAGE);
	}
}
