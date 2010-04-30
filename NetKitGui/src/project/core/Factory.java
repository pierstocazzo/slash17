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
	public AbstractLink createLink(AbstractInterface hostInterface,
			AbstractCollisionDomain collisionDomain) {
		return new Link(hostInterface, collisionDomain);
	}

}
