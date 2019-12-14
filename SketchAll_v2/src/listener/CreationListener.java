package listener;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import drawing.DessinClient;
import editeur.EditeurClient;
import editeur.ZoneDeDessin;

public class CreationListener implements MouseListener, MouseMotionListener {

	EditeurClient editor;
	ZoneDeDessin sheet;
	String selectedShape;
	static Object shapeToCreate;
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
		shapeToCreate = editor.creerDessin (debut.x, debut.y, 0, 0, sheet.getForeground(), sheet.getShape()) ;
		// ajout de le "présentation" du dessin
		sheet.add (((DessinClient) shapeToCreate), 0) ;
		// mise à jour des limites du dessin courant, ne sert pas à grand chose puisque w et h valent 0
		((DessinClient) shapeToCreate).setProxyBounds (debut.x, debut.y, 0, 0) ;
		((DessinClient) shapeToCreate).setProxyColor (color) ;
		((DessinClient) shapeToCreate).select();
	}

	@Override
	public void mouseReleased (MouseEvent e) {
		fin = null ;
		debut = null ;
		shapeToCreate = null ;
	}

	@Override
	public void mouseDragged (MouseEvent e) {
		fin = e.getPoint () ;
		if (sheet.getShape() == "Drawing") {
			debut = e.getPoint () ;
			shapeToCreate = editor.creerDessin (debut.x, debut.y, 0, 0, sheet.getForeground(), sheet.getShape()) ;
			// ajout de le "présentation" du dessin
			sheet.add (((DessinClient) shapeToCreate), 0) ;
			// mise à jour des limites du dessin courant, ne sert pas à grand chose puisque w et h valent 0
			((DessinClient) shapeToCreate).setProxyBounds (debut.x, debut.y, 0, 0) ;
			
		} else if (shapeToCreate != null) {
			// mise à jour des limites du dessin via une méthode spécifiquequi fera également une mise à jour sur le serveur
			((DessinClient) shapeToCreate).setProxyBounds (Math.min (debut.x, fin.x),
										  Math.min (debut.y, fin.y),
										  Math.abs (fin.x - debut.x),
										  Math.abs (fin.y - debut.y)) ;
		}
		((DessinClient) shapeToCreate).setProxyColor (color) ;
	}

	@Override
	public void mouseMoved (MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

}
