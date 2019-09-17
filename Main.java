
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import java.util.Vector;

// this assignment use the sample code in course material
public class Main {
	public static Vector<Sprite> children = new Vector<Sprite>(); 
	public static void main(String[] args) {		

		// add scene graph to the canvas
		Canvas canvas = new Canvas();
		canvas.addSprite(Main.makeSprite());
		MenuView MenuView = new MenuView(children, canvas);
		canvas.children = children;
		// create a frame to hold it
		JFrame f = new JFrame();

		BorderLayout layout = new BorderLayout();
		f.getContentPane().setLayout(layout);
		f.getContentPane().add(MenuView, BorderLayout.NORTH);
		f.getContentPane().add(canvas, BorderLayout.CENTER);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1024, 768);
		f.setResizable(false);
		f.setVisible(true);
	}
	
	/* Make sample scene graph for testing purposes. */
	private static Sprite makeSprite() {
		// create four different parts
		Sprite Torso = new RectangleSprite(80, 180);

		Sprite Head = new HeadSprite(50, 70);

		Sprite RUpperArm = new UpperArm(25, 95);
		Sprite RLowerArm = new LowerArm(20, 68);
		Sprite RHand = new Hand(20, 20);

		Sprite LUpperArm = new UpperArm(25, 95);
		Sprite LLowerArm = new LowerArm(20, 68);
		Sprite LHand = new Hand(20, 20);

		Sprite RUpperLeg = new UpperLeg(25, 100);
		Sprite RLowerLeg = new LowerLeg(25, 80);
		Sprite RFoot = new Feet(35, 15, false);

		Sprite LUpperLeg = new UpperLeg(25, 100);
		Sprite LLowerLeg = new LowerLeg(25, 80);
		Sprite LFoot = new Feet(35, 15, true);

		children.add(Torso);
		children.add(Head);
		children.add(RUpperArm);
		children.add(RLowerArm);
		children.add(RHand);
		children.add(LUpperArm);
		children.add(LLowerArm);
		children.add(LHand);
		children.add(RUpperLeg);
		children.add(RLowerLeg);
		children.add(RFoot);
		children.add(LUpperLeg);
		children.add(LLowerLeg);
		children.add(LFoot);

		// define them based on relative, successive transformations
		Torso.transform(AffineTransform.getTranslateInstance(500, 200));

		Head.transform(AffineTransform.getTranslateInstance(15, -75));
		
		RUpperArm.transform(AffineTransform.getTranslateInstance(65, 5));
		RLowerArm.transform(AffineTransform.getTranslateInstance(2, 95));
		RHand.transform(AffineTransform.getTranslateInstance(0, 68));

		LUpperArm.transform(AffineTransform.getTranslateInstance(-10, 5));
		LLowerArm.transform(AffineTransform.getTranslateInstance(2, 95));
		LHand.transform(AffineTransform.getTranslateInstance(0, 68));

		RUpperLeg.transform(AffineTransform.getTranslateInstance(47, 180));
		RLowerLeg.transform(AffineTransform.getTranslateInstance(0, 100));
		RFoot.transform(AffineTransform.getTranslateInstance(4, 80));

		LUpperLeg.transform(AffineTransform.getTranslateInstance(11, 180));
		LLowerLeg.transform(AffineTransform.getTranslateInstance(0, 100));
		LFoot.transform(AffineTransform.getTranslateInstance(-12, 80));

		// build scene graph
		Torso.addChild(Head);
		Torso.addChild(RUpperArm);
		Torso.addChild(LUpperArm);
		Torso.addChild(RUpperLeg);
		Torso.addChild(LUpperLeg);

		RUpperArm.addChild(RLowerArm);
		RLowerArm.addChild(RHand);

		LUpperArm.addChild(LLowerArm);
		LLowerArm.addChild(LHand);

		RUpperLeg.addChild(RLowerLeg);
		RLowerLeg.addChild(RFoot);

		LUpperLeg.addChild(LLowerLeg);
		LLowerLeg.addChild(LFoot);
		
		// return root of the tree
		return Torso;
	}

}
