package project;

import project.core.Factory;
import project.gui.GFrame;
import project.gui.GFactory;

public class Main {
	public static void main(String[] args) {
		GFactory.init( Factory.getInstance() );
		new GFrame();
	}
}
