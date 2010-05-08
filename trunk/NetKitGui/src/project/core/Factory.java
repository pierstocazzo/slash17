package project.core;

import project.common.ItemType;

public class Factory implements AbstractFactory {

	private static Factory factory;
	
	private Factory() {
	}
	
	public static AbstractFactory getInstance() {
		if( factory == null ) {
			factory = new Factory();
		}
		
		return factory;
	}
	
	@Override
	public AbstractCollisionDomain createCollisionDomain() {
		return new CollisionDomain( NameGenerator.getNextName( ItemType.COLLISIONDOMAIN ) );
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
