package project.gui;

import java.awt.BorderLayout;

import project.gui.netconf.ConfPanel;

public class GuiManager {

	private static GuiManager gmanager;
	
	GFrame frame;
	ConfPanel confPanel;
	GCanvas canvas;
	
	private GuiManager() {}
	
	public static GuiManager getInstance() {
		if( gmanager == null ) 
			gmanager = new GuiManager();
		
		return gmanager;
	}
	
	public void startGui() {
		frame = new GFrame();
		
		confPanel = new ConfPanel(null);
		frame.add(confPanel, BorderLayout.EAST);	
		canvas = new GCanvas(frame, confPanel);
		frame.add(canvas, BorderLayout.CENTER);
		
		frame.setCanvas(canvas);
	}
	
	public void update() {
		confPanel.getInterfaceTree().update();
	}
}
