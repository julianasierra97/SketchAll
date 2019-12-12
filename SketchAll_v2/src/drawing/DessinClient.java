package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import editeur.ZoneDeDessin;
import listener.ModificationListener;
import server.RemoteDessinServeur;

public abstract class DessinClient extends JPanel {

	private static final long serialVersionUID = 1L;

	ModificationListener ML;
	RemoteDessinServeur proxy;
	ZoneDeDessin sheet;

	// constructeur Dessin : paramétré par une instance de RemoteDessin permettant
	// d'envoyer des informations au référent sur le serveur
	public DessinClient(ZoneDeDessin sheet, RemoteDessinServeur proxy) {
		this.sheet = sheet;
		this.proxy = proxy;

		setForeground(sheet.getForeground());
		setOpaque(false);
		ML = new ModificationListener(this);
		if (sheet.getEditor().isSketcher()) {
			addMouseListener(ML);
			addMouseMotionListener(ML);
		}
	}

	public void setProxyColor(Color color) {
		setForeground(color);
	}

	// méthode permettant de "retailler" un Dessin :
	// - elle réalise un "setBounds" pour fournir un retour visuel immédiat)
	// - ensuite elle propage le changement au référent, vie une remote invocation
	// sur le proxy
	public void setProxyBounds(int x, int y, int w, int h) {
		setBounds(x, y, w, h);
		try {
			proxy.setBounds(x, y, w, h);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// méthode permettant de déplacer un Dessin :
	// - elle réalise un "setLocation" pour fournir un retour visuel immédiat
	// - ensuite elle propage le changement au référent, vie une remote invocation
	// sur le proxy
	public void setProxyLocation(int x, int y) {
		setLocation(x, y);
		try {
			proxy.setLocation(x, y);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void paint(Graphics graph) {
		super.paint(graph);
	}

}
