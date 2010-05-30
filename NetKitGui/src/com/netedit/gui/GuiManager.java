package com.netedit.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.netedit.core.project.AbstractProject;
import com.netedit.gui.gcomponents.ConfigurationPanel;
import com.netedit.gui.gcomponents.GCanvas;
import com.netedit.gui.gcomponents.GFrame;
import com.netedit.gui.input.HandlerManager;
import com.netedit.gui.nodes.GLink;

public class GuiManager {
	private static final long serialVersionUID = 7422535360161172027L;

	private static GuiManager gmanager;
	
	GFrame frame;
	ConfigurationPanel confPanel;
	JLabel emptyCanvas;
	GCanvas canvas;
	JSplitPane splitPane;
	AbstractProject project;
	HandlerManager handler;
	
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
		Lab.getInstance().setProject(project);
		confPanel.setProject(project);
		
		handler = new HandlerManager();
		canvas = new GCanvas();
		handler.start();
		canvas.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		canvas.setPreferredSize(new Dimension((int) (frame.getSize().getWidth()*0.8), (int) (frame.getSize().getHeight()*0.8)));
		
		splitPane.setLeftComponent(canvas);
		frame.setCanvas(canvas);
		confPanel.update();
		frame.validate();
	}
	
	public void update() {
		frame.validate();
		confPanel.validate();
		canvas.validate();
		
		if( canvas != null ) {
			for( Object o : canvas.getLinkLayer().getChildrenReference() ) {
				if( o instanceof GLink ) {
					((GLink) o).update();
				}
			}
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
	
	public HandlerManager getHandler() {
		return handler;
	}
}
