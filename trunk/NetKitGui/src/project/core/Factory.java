package project.core;

import project.common.ItemType;

class Factory implements AbstractFactory {

	private Factory factory;
	
	private Factory() {
	}
	
	public AbstractFactory getInstance() {
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
