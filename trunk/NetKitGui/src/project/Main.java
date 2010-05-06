package project;

import project.core.Factory;
import project.gui.GFactory;
import project.gui.GuiManager;

public class Main {
	public static void main(String[] args) {
		GFactory.init( Factory.getInstance() );
		GuiManager.getInstance().startGui();
	}
}
