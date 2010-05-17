package project.core;

public interface AbstractRule {
	
	public String getRule();

	public String getTarget();
	
	public String getProtocol();
	
	public String getSource();
	
	public String getDestination();
	
	public String getInputIface();
	
	public String getOutputIface();
	
	public int getDestPort();
	
	public int getSourcePort();
	
	public AbstractChain getChain();
	
	public void setRule( String rule );
	
	public void setTarget( String target );
	
	public void setProtocol( String protocol );
	
	public void setSource( String source );
	
	public void setDestination( String destination );
	
	public void setInputIface( String iface );
	
	public void setOutputIface( String iface );
	
	public void setDestPort( int port );
	
	public void setSourcePort( int port );

	public void delete();

	public String getName();
	
	public void setName( String name );
}
