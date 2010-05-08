package project.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import project.core.AbstractProject;
import project.gui.labview.LabConfPanel;

public class GuiManager {

	private static GuiManager gmanager;
	
	GFrame frame;
	LabConfPanel confPanel;
	JLabel emptyCanvas;
	GCanvas canvas;
	JSplitPane jSplitPane;
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
		
		confPanel = new LabConfPanel();
		
		emptyCanvas = new JLabel("Create a new project or open an existing one.");
		emptyCanvas.setHorizontalAlignment(SwingConstants.CENTER);
		emptyCanvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		// set the canvas preferred size to the 80% of the window's size 
		emptyCanvas.setPreferredSize(new Dimension((int) (frame.getSize().getWidth()*0.8), (int) (frame.getSize().getHeight()*0.8)));
		
		jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, emptyCanvas, confPanel);
		jSplitPane.setBorder( BorderFactory.createLineBorder(Color.LIGHT_GRAY) );
		frame.add(jSplitPane, BorderLayout.CENTER);
		
		frame.validate();
	}
	
	public void setProject( AbstractProject project ) {
		this.project = project;
		confPanel.setProject( project );
		
		canvas = new GCanvas(frame, project, confPanel);
		canvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		canvas.setPreferredSize(new Dimension((int) (frame.getSize().getWidth()*0.8), (int) (frame.getSize().getHeight()*0.8)));
		
		jSplitPane.setLeftComponent(canvas);
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

	public LabConfPanel getConfPanel() {
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
}
