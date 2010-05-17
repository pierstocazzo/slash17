package project.gui.labview;

import javax.swing.JDialog;

import project.core.AbstractRule;

public class RuleDialog extends JDialog {
	private static final long serialVersionUID = 7520700147946284690L;

	AbstractRule rule;
	
	public RuleDialog( AbstractRule rule ) {
		this.rule = rule;
	}
}
