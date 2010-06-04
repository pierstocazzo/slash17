package com.netedit.core;

import java.io.Serializable;

import com.netedit.common.ItemType;
import com.netedit.core.nodes.AbstractCollisionDomain;
import com.netedit.core.nodes.AbstractHost;
import com.netedit.core.nodes.AbstractLink;
import com.netedit.core.nodes.CollisionDomain;
import com.netedit.core.nodes.Host;
import com.netedit.core.nodes.Link;
import com.netedit.core.nodes.components.AbstractInterface;
import com.netedit.core.project.AbstractProject;
import com.netedit.core.project.Project;
import com.netedit.core.util.NameGenerator;


public class Factory implements AbstractFactory, Serializable {
	private static final long serialVersionUID = -4123193508601850436L;
	
	public Factory() {
	}
	
	@Override
	public AbstractCollisionDomain createCollisionDomain( boolean isTap ) {
		if( isTap ) {
			String name = NameGenerator.getNextName( ItemType.TAP );
			if( name != null ) {
				AbstractCollisionDomain cd = new CollisionDomain(name);
				cd.setIsTap(true);
				return cd;
			}
			return null;
		} else {
			return new CollisionDomain( NameGenerator.getNextName( ItemType.COLLISIONDOMAIN ) );
		}
	}

	@Override
	public AbstractHost createHost(ItemType type) {
		return new Host( NameGenerator.getNextName(type), type );
	}

	@Override
	public AbstractLink createLink(AbstractHost host, AbstractCollisionDomain collisionDomain) {
		if( host.isConnectedTo( collisionDomain ) ) 
			return null;
		
		AbstractInterface iface = host.addInterface(collisionDomain);
		collisionDomain.addConnection(iface);
		
		if( iface != null ) 	
			return new Link(iface, collisionDomain);
		else return null;
	}

	@Override
	public AbstractProject createProject(String name, String directory) {
		return new Project(directory, name);
	}
}
