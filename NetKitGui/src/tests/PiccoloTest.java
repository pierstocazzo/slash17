package tests;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PText;
import edu.umd.cs.piccolo.util.PDimension;
import edu.umd.cs.piccolox.PFrame;

public class PiccoloTest extends PFrame {
	private static final long serialVersionUID = 1L;
	
	PLayer mainLayer;
	PLayer secondLayer;
	
	public static void main(String[] args) {
		new PiccoloTest();
	}
	
	public void initialize() {	
		// getting the root node
		mainLayer = getCanvas().getLayer();
		secondLayer = new PLayer();
		mainLayer.addChild(secondLayer);

		// creating a text node
		PText text = new PText("First Piccolo Test");
		mainLayer.addChild(text);
		
		InputHandler handler = new InputHandler();
		
		Node node1 = new Node(100, 100);
		Node node2 = new Node(100, 200);
		Node node3 = new Node(200, 100);
		Node node4 = new Node(200, 200);
		node1.addInputEventListener(handler);
		node2.addInputEventListener(handler);
		node3.addInputEventListener(handler);
		node4.addInputEventListener(handler);
		
		Edge edge1 = new Edge(node1, node2);		
		Edge edge2 = new Edge(node2, node3);		
		Edge edge3 = new Edge(node3, node4);		
		
		mainLayer.addChild(node1);
		mainLayer.addChild(node2);
		mainLayer.addChild(node3);
		mainLayer.addChild(node4);
		secondLayer.addChild(edge1);
		secondLayer.addChild(edge2);
		secondLayer.addChild(edge3);
		
		PImage tux = new PImage("data/tux.png");
		tux.setScale(0.2);
		tux.setX(1300);
		tux.setY(500);
		tux.addInputEventListener(handler);
		mainLayer.addChild(tux);
	}


	class InputHandler extends PBasicInputEventHandler {
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
			
			try {
				for( Edge e : ((Node) node.getParent()).getEdges() ) {
					e.update();
				}
			} catch (Exception e) {
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
	}
}
