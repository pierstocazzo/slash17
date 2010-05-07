package project.gui.labview;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

import project.core.AbstractHost;

public class GTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = -2967012607835738947L;

	public static final int IFACE = 0;
	public static final int FIREWALL = 1;
	public static final int ROUTER = 2;
	public static final int FOLDER = 3;
	public static final int FILE = 4;
	
	private static final Icon ifaceIcon = new ImageIcon("data/images/24x24/nic_icon.png");
	private static final Icon folderIcon = new ImageIcon("data/images/24x24/folder_icon.png");
	private static final Icon routerIcon = new ImageIcon("data/images/24x24/router_icon.png");
	private static final Icon firewallIcon = new ImageIcon("data/images/24x24/firewall_icon.png");
	private static final Icon fileIcon = new ImageIcon("data/images/24x24/file_icon.png");
	
	private int type;
	
	private AbstractHost host;
	
	public GTreeNode( int type ) {
		super();
		this.type = type;
	}
	
	public GTreeNode( Object obj, int type ) {
		super(obj);
		this.type = type;
	}
	
	public GTreeNode( Object obj, boolean allowsChildren, int type ) {
		super(obj, allowsChildren);
		this.type = type;
	}
	
	public void setHost( AbstractHost host ) {
		this.host = host;
	}
	
	public Icon getIcon() {
		Icon icon = null;
		
		switch (type) {
		case 0:
			icon = ifaceIcon;
			break;
		case 1:
			icon = firewallIcon;
			break;
		case 2:
			icon = routerIcon;
			break;
		case 3: 
			icon = folderIcon;
			break;
		case 4: 
			icon = fileIcon;
			break;
		default:
			break;
		}
		
		return icon;
	}
	
	public AbstractHost getHost() {
		return host;
	}
	
	public int getType() {
		return type;
	}
}
