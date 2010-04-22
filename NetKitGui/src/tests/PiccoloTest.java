package tests;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolox.PFrame;

public class PiccoloTest extends PFrame {
	private static final long serialVersionUID = 1L;
	
	PLayer mainLayer;
	PLayer secondLayer;
	
	public void initialize() {	
		// getting the root node
		mainLayer = getCanvas().getLayer();
		secondLayer = new PLayer();
		mainLayer.addChild(secondLayer);

		// creating a text node
		PText text = new PText("First Piccolo Test");
		mainLayer.addChild(text);
		
		PBasicInputEventHandler listener = new PBasicInputEventHandler() {
			@Override
			public void mousePressed(PInputEvent event) {
				super.mousePressed(event);
				
				event.getPickedNode().setTransparency(0.5f);
				event.setHandled(true);
			}
			
			@Override
			public void mouseEntered(PInputEvent event) {
				super.mouseEntered(event);
				
				event.getPickedNode().setTransparency(0.5f);
				event.setHandled(true);
			}
			
			@Override
			public void mouseDragged(PInputEvent event) {
				super.mouseDragged(event);
				
				PNode node = event.getPickedNode();
				
				node.setTransparency(0.5f);
				PDimension delta = event.getDeltaRelativeTo( node );
				node.translate(delta.width, delta.height);
				event.setHandled(true);
				
				for( Link e : ((Host) node.getParent()).getEdges() ) {
					e.update();
				}
			}
			
			@Override
			public void mouseExited(PInputEvent event) {
				super.mouseExited(event);
				
				event.getPickedNode().setTransparency(1);
				event.setHandled(true);
			}
			
			@Override
			public void mouseReleased(PInputEvent event) {
				super.mouseReleased(event);
				
				event.getPickedNode().setTransparency(1);
				event.setHandled(true);
			}
		};
		
		Host node1 = new Host(100, 100);
		Host node2 = new Host(200, 200);
		node1.addInputEventListener(listener);
		node2.addInputEventListener(listener);
		Link edge = new Link(node1, node2);		
		
		mainLayer.addChild(node1);
		mainLayer.addChild(node2);
		secondLayer.addChild(edge);
	}

	public static void main(String[] args) {
		new PiccoloTest();
	}
}
