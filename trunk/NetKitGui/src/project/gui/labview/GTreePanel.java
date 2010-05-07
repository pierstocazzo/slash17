package project.gui.labview;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class GTreePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	protected GTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;

    public GTreePanel( String rootNodeName ) {
        super(new BorderLayout());
        
        JLabel label = new JLabel( rootNodeName );
		label.setBorder( new EmptyBorder(5, 5, 5, 5) );
		add( label, BorderLayout.NORTH );
        
        rootNode = new GTreeNode(rootNodeName, GTreeNode.FOLDER);
        treeModel = new DefaultTreeModel(rootNode);
        treeModel.addTreeModelListener(new MyTreeModelListener());
        tree = new JTree(treeModel);
        tree.setEditable(false);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setShowsRootHandles(true);
        
        MyRenderer renderer = new MyRenderer();
        tree.setCellRenderer(renderer);

        JScrollPane scrollPane = new JScrollPane(tree);
        add(scrollPane);
    }
    
    public void update() {
    	clear();
    	// update the tree
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
            tree.scrollPathToVisible(new TreePath(childNode.getPath()));
        }
        
        return childNode;
    }

    class MyTreeModelListener implements TreeModelListener {
    	public void treeNodesChanged(TreeModelEvent e) {
    		DefaultMutableTreeNode node;
    		node = (DefaultMutableTreeNode)(e.getTreePath().getLastPathComponent());

    		/*
    		 * If the event lists children, then the changed
    		 * node is the child of the node we've already
    		 * gotten.  Otherwise, the changed node and the
    		 * specified node are the same.
    		 */

    		int index = e.getChildIndices()[0];
    		node = (DefaultMutableTreeNode)(node.getChildAt(index));

    		System.out.println("The user has finished editing the node.");
    		System.out.println("New value: " + node.getUserObject());
    	}
    	public void treeNodesInserted(TreeModelEvent e) {
    	}
    	public void treeNodesRemoved(TreeModelEvent e) {
    	}
    	public void treeStructureChanged(TreeModelEvent e) {
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

