package com.netedit;

import com.netedit.core.Factory;
import com.netedit.gui.GFactory;
import com.netedit.gui.GuiManager;

public class Main {
	public static void main(String[] args) {
		GFactory.init( new Factory() );
		GuiManager.getInstance().startGui();
	}
}