package editeur;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class ZoneDeDessin extends JPanel {

	private static final long serialVersionUID = 4588428293101431953L ;
	
	int radius = 4;

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
	
	class CreationListener implements MouseListener, MouseMotionListener {

		EditeurClient editor;
		ZoneDeDessin sheet;
		Drawing drawing;
		Point debut, fin ;
		Color color;
		int radius;
		
		public CreationListener (EditeurClient editor, ZoneDeDessin sheet) {
			this.editor = editor;
			this.sheet = sheet;
		}

		@Override
		public void mouseEntered (MouseEvent e) {
		}

		@Override
		public void mouseExited (MouseEvent e) {
		}

		@Override
		public void mousePressed (MouseEvent e) {
			debut = e.getPoint () ;
			fin = e.getPoint () ;
			
			color = ((ZoneDeDessin)e.getSource()).getForeground();
			radius = ((ZoneDeDessin)e.getSource()).getRadius();
			
			// création d'un dessin via l'éditeur local : il fera les appels nécéssaires au seveur distant 
			drawing = editor.creerDessin (debut.x, debut.y, 0, 0, getForeground()) ;
			// ajout de le "présentation" du dessin
			add (drawing, 0) ;
			// mise à jour des limites du dessin courant, ne sert pas à grand chose puisque w et h valent 0
			drawing.setProxyBounds (debut.x, debut.y, 0, 0) ;
			drawing.setProxyColor (color) ;
		}

		@Override
		public void mouseReleased (MouseEvent e) {
			fin = null ;
			debut = null ;
			drawing = null ;
		}

		@Override
		public void mouseDragged (MouseEvent e) {
			debut = e.getPoint () ;
			fin = e.getPoint () ;
			
			color = ((ZoneDeDessin)e.getSource()).getForeground();
			
			// création d'un dessin via l'éditeur local : il fera les appels nécéssaires au seveur distant 
			drawing = editor.creerDessin (debut.x, debut.y, 0, 0, getForeground()) ;
			// ajout de le "présentation" du dessin
			add (drawing, 0) ;
			// mise à jour des limites du dessin courant, ne sert pas à grand chose puisque w et h valent 0
			drawing.setProxyBounds (debut.x, debut.y, 0, 0) ;
			drawing.setProxyColor (color) ;
		}

		@Override
		public void mouseMoved (MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {		
		}

	}
}
