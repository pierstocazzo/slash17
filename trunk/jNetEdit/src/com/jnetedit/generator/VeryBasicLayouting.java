package com.jnetedit.generator;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import com.jnetedit.core.nodes.AbstractCollisionDomain;
import com.jnetedit.core.nodes.AbstractHost;

public class VeryBasicLayouting implements AbstractLayout {

	boolean[][] matrix;
	
	public VeryBasicLayouting() {
		super();
		initMatrix();
	}
	
	@Override
	public Rectangle2D getBounds(AbstractHost node) {
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				if( matrix[i][j] == false ) {
					matrix[i][j] = true;
					return new Rectangle(j*120, i*180, 64, 64);
				}
			}
		}
		return new Rectangle(0, 0, 64, 64);
	}
	
	@Override
	public Rectangle2D getBounds(AbstractCollisionDomain domain) {
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				if( matrix[i][j] == false ) {
					matrix[i][j] = true;
					return new Rectangle(j*120, i*180, 64, 64);
				}
			}
		}
		return new Rectangle(0, 0, 64, 64);
	}

	private void initMatrix() {
		matrix = new boolean[8][8];
		for( int i = 0; i < 8; i++ ) {
			for( int j = 0; j < 8; j++ ) {
				matrix[i][j] = false;
			}
		}
	}
}
