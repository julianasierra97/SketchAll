package editeur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JPanel;

import listener.CreationListener;

public class ZoneDeDessin extends JPanel {

	private static final long serialVersionUID = 4588428293101431953L ;
	
	int radius = 4;
	String shapeOn = "Rectangle";

	//-----------------------------------------------------------------------
	// le constructeur :
	// - il instancie et positionne les listeners de créations de dessins
	// - il supprime le layout manager pour gérer lui même les dessins
	//-----------------------------------------------------------------------

	public ZoneDeDessin (EditeurClient editor) {
		CreationListener CL = new CreationListener (editor, this) ;
		addMouseListener (CL) ;
		addMouseMotionListener (CL) ;
		setPreferredSize (new Dimension (900, 600)) ;
		setRadius (radius);
		setForeground (Color.black) ;
		setBackground (Color.white) ;
		setLayout (null) ;
		setCursor (Cursor.getPredefinedCursor (Cursor.CROSSHAIR_CURSOR)) ;
	}
	
	public void setRadius(int radius) {
		this.radius = radius;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public void setShape(String newShape) {
		shapeOn = newShape;
	}
	
	public String getShape() {
		return shapeOn;
	}
}
