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
		AbstractInterface iface = host.addInterface(collisionDomain);
		
		if( iface != null ) 	
			return new Link(iface, collisionDomain);
		else return null;
	}

}
