package project.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

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
		
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
		confPanel = new LabConfPanel(null);
		emptyCanvas = new JLabel("Create a new project or open an existing one.");
		emptyCanvas.setHorizontalAlignment(SwingConstants.CENTER);
		// set the canvas preferred size to the 80% of the window's size 
		emptyCanvas.setPreferredSize(new Dimension((int) (size.getWidth()*0.8), (int) (size.getHeight()*0.8)));
		
		jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, emptyCanvas, confPanel);
		jSplitPane.setDividerSize(2);
		frame.add(jSplitPane, BorderLayout.CENTER);
	}
	
	public void setProject( AbstractProject project ) {
		this.project = project;
		confPanel.setProject( project );
		
		canvas = new GCanvas(frame, project, confPanel);
		
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		canvas.setPreferredSize(new Dimension((int) (size.getWidth()*0.8), (int) (size.getHeight()*0.8)));
		
		jSplitPane.setLeftComponent(canvas);
		frame.setCanvas(canvas);
	}
	
	public void update() {
		if( canvas != null )
			confPanel.update();
	}
}
