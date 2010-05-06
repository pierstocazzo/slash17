package project.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JSplitPane;

import project.gui.netconf.LabConfPanel;

public class GuiManager {

	private static GuiManager gmanager;
	
	GFrame frame;
	LabConfPanel confPanel;
	GCanvas canvas;
	
	private GuiManager() {}
	
	public static GuiManager getInstance() {
		if( gmanager == null ) 
			gmanager = new GuiManager();
		
		return gmanager;
	}
	
	public void startGui() {
		frame = new GFrame();
		
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		
		confPanel = new LabConfPanel(null);
		canvas = new GCanvas(frame, confPanel);
		// set the canvas preferred size to the 80% of the window's size 
		canvas.setPreferredSize(new Dimension((int) (size.getWidth()*0.8), (int) (size.getHeight()*0.8)));
		
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, canvas, confPanel);
		jSplitPane.setDividerSize(2);
		
		frame.add(jSplitPane, BorderLayout.CENTER);
		frame.setCanvas(canvas);
	}
	
	public void update() {
		confPanel.update();
	}
}
