package project.core;

import java.util.ArrayList;

public class Chain implements AbstractChain {

	AbstractHost host;
	
	String name;
	
	String policy;
	
	ArrayList<AbstractRule> rules;
	
	public Chain( AbstractHost host ) {
		this.host = host;
		name = "";
		policy = "ACCEPT";
		rules = new ArrayList<AbstractRule>();
	}
	
	public Chain( AbstractHost host, String name ) {
		this.host = host;
		this.name = name;
		policy = "ACCEPT";
		rules = new ArrayList<AbstractRule>();
	}
	
	public AbstractHost getHost() {
		return host;
	}
	
	@Override
	public AbstractRule addRule() {
		AbstractRule rule = new Rule(this, "rule" + rules.size());
		rules.add(rule);
		return rule;
	}

	@Override
	public void deleteRule(AbstractRule rule) {
		rules.remove(rule);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public AbstractRule getRule( String rule ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<AbstractRule> getRules() {
		return rules;
	}

	@Override
	public void setName( String name ) {
		this.name = name;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy( String policy ) {
		this.policy = policy;
	}

}
