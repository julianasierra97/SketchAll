package listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import drawing.DessinClient;

//listener qui gère la séléction du dessin
public class SelectionListener implements MouseListener{
	
	private DessinClient dessin;

	public SelectionListener(DessinClient dessin) {
		super();
		this.dessin = dessin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		dessin.select();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
