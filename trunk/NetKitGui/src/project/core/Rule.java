package project.core;


public class Rule implements AbstractRule {

	String name;
	
	String inputIface;
	String outputIface;
	String source;
	String destination;
	int sourcePort;
	int destPort;
	String protocol;
	String target;
	
	AbstractChain chain;
	
	String rule;
	
	String userRule;
	
	public Rule( AbstractChain chain, String name ) {
		this.chain = chain;
		this.name = name;
		
		sourcePort = 0;
		destPort = 0;
		inputIface = "";
		outputIface = "";
		protocol = "";
		target = "";
		source = "";
		destination = "";
		
		userRule = null;
	}

	public String getInputIface() {
		return inputIface;
	}
	
	public AbstractChain getChain() {
		return chain;
	}

	public void setInputIface(String inputIface) {
		this.inputIface = inputIface;
	}

	public String getOutputIface() {
		return outputIface;
	}

	public void setOutputIface(String outputIface) {
		this.outputIface = outputIface;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getRule() {
		if( userRule != null )
			return userRule;
		
		rule = "iptables -A " + chain.getName();
		if( !source.equals("") )
			rule += " -s " + source;
		if( !destination.equals("") )
			rule += " -d " + destination;
		if( !inputIface.equals("") ) 
			rule += " -i " + inputIface;
		if( !outputIface.equals("") )
			rule += " -o " + outputIface;
		if( !protocol.equals("") )
			rule += " -p " + protocol;
		if( sourcePort != 0 )
			rule += " --sport " + sourcePort;
		if( destPort != 0 )
			rule += " --dport " + destPort;
		if( !target.equals("") ) 
			rule += " -j " + target;
		return rule;
	}

	public void setRule(String rule) {
		this.userRule = rule;
	}

	@Override
	public void delete() {
		chain.deleteRule(this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
