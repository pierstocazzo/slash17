package project.gui.labview;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import project.core.AbstractHost;

public class GTreePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final int INTERFACES = 0;
	public static final int FIREWALLING = 1;
	public static final int ROUTING = 2;
	public static final int LABSTRUCTURE = 3;
	
	protected GTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
    
    protected int type;

    public GTreePanel( String stringLabel, String projectName, int type ) {
        super(new BorderLayout());
        
        JLabel label = new JLabel( stringLabel );
		label.setBorder( new EmptyBorder(5, 5, 5, 5) );
		add( label, BorderLayout.NORTH );
        
        rootNode = new GTreeNode(projectName, GTreeNode.FOLDER);
        treeModel = new DefaultTreeModel(rootNode);
        
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        tree.addMouseListener( new MyListener() );
        
        MyRenderer renderer = new MyRenderer();
        tree.setCellRenderer(renderer);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
    
    public void selectHost( String hostName ) {
    	collapseAll();
    	
    	for( int i = 0; i < rootNode.getChildCount(); i++ ) {
    		GTreeNode host = ((GTreeNode) rootNode.getChildAt(i));
    		if( host.getUserObject().equals( hostName ) ) {
    			try {
    				tree.scrollPathToVisible( new TreePath( ((GTreeNode) host.getLastChild()).getPath()) );
    			} catch (Exception e) {
				}
    			return;
    		}
    	}
    }
    
    public void collapseAll() {
    	int row = tree.getRowCount() - 1;
    	while (row >= 0) {
    		tree.collapseRow(row);
    		row--;
    	}
    }

    public void expandAll(JTree tree) {
    	int row = 0;
    	while (row < tree.getRowCount()) {
    		tree.expandRow(row);
    		row++;
    	}
    }

    
    /** Remove all nodes except the root node. */
    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }

    /** Remove the currently selected node. */
    public void removeCurrentNode() {
        TreePath currentSelection = tree.getSelectionPath();
        if (currentSelection != null) {
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)
                         (currentSelection.getLastPathComponent());
            MutableTreeNode parent = (MutableTreeNode)(currentNode.getParent());
            if (parent != null) {
                treeModel.removeNodeFromParent(currentNode);
                return;
            }
        } 
    }

    /** Add child to the currently selected node. */
    public GTreeNode addObject( Object child, int type ) {
    	GTreeNode parentNode = null;
        TreePath parentPath = tree.getSelectionPath();

        if (parentPath == null) {
            parentNode = rootNode;
        } else {
            parentNode = (GTreeNode) (parentPath.getLastPathComponent());
        }

        return addObject( parentNode, child, true, type );
    }

    public GTreeNode addObject( GTreeNode parent, Object child, int type) {
        return addObject( parent, child, false, type );
    }

    public GTreeNode addObject( GTreeNode parent,  Object child, boolean visible, int type ) {
    	
    	GTreeNode childNode = new GTreeNode(child, type);

        if (parent == null) {
            parent = rootNode;
        }
	
        //It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
        treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

        //Make sure the user can see the lovely new node.
        if (visible) {
        	collapseAll();
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        
        return childNode;
    }
    
    public void setName( String name ) {
		rootNode.setUserObject( name );
	}

	public GTreeNode addObject(String name, int type, AbstractHost host) {
		GTreeNode node = addObject(name, type);
		node.setHost(host);
		return node;
	}
	
	public GTreeNode addObject(GTreeNode parent, String name, int type, AbstractHost host) {
		GTreeNode node = addObject(parent, name, true, type);
		node.setHost(host);
		return node;
	}

    /** the mouse listeners for the tree nodes */
    class MyListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
	         TreePath selPath = tree.getSelectionPath();
	         if( selPath != null ) {
	        	 GTreeNode node = (GTreeNode) selPath.getLastPathComponent();
	         
		         if( e.getClickCount() >= 2 && node.getType() == GTreeNode.IFACE ) {
		        	 new IfaceConfFrame( node.getHost().getInterface( (String) node.getUserObject() ) );
	             }
	         }
		}
    }

    /** Renderer for the tree */
    class MyRenderer extends DefaultTreeCellRenderer {
    	private static final long serialVersionUID = 1L;

    	public MyRenderer() {
    		super();
    	}

    	public Component getTreeCellRendererComponent(
    			JTree tree,
    			Object value,
    			boolean sel,
    			boolean expanded,
    			boolean leaf,
    			int row,
    			boolean hasFocus) {

    		super.getTreeCellRendererComponent(
    				tree, value, sel,
    				expanded, leaf, row,
    				hasFocus);

    		GTreeNode node = (GTreeNode) value;

    		setIcon( node.getIcon() );

    		
    		return this;
    	}
    }
}

