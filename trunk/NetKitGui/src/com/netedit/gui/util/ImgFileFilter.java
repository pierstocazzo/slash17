package com.netedit.gui.util;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class ImgFileFilter extends FileFilter {

	String extension;
	
	public ImgFileFilter( String extension ) {
		this.extension = extension;
	}
	
	@Override
	public boolean accept(File f) {
		if( f.isDirectory() ) {
			return true;
		}
		String fileName = f.getPath();
		if( fileName.endsWith( extension ) )
			return true;
		else 
			return false;
	}

	@Override
	public String getDescription() {
		return extension;
	}
	
	public String getExtension() {
		return extension.substring(1);
	}
}
