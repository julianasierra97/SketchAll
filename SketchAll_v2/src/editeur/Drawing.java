package editeur;

import server.RemoteDessinServeur;

import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;

import javax.swing.JPanel;

public class Drawing extends JPanel {
	private static final long serialVersionUID = 1L;
	
	int radius = 5;
	RemoteDessinServeur proxy;
	ZoneDeDessin sheet;
	
	public Drawing (ZoneDeDessin sheet, RemoteDessinServeur proxy) {
		this.sheet = sheet;
		this.proxy = proxy;

		setForeground(sheet.getForeground());
		radius = sheet.getRadius();
		setOpaque(false);
	}

	public void setProxyColor(Color color) {
		setForeground(color);
	}
	
	public void setProxyRadius(int radius) {
		this.radius = radius;
	}
	
	public void setProxyBounds (int x, int y, int w, int h) {		
		setBounds(x,y,radius,radius);
		try {
			proxy.setBounds(x,y,radius,radius);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void setProxyLocation (int x, int y) {setLocation(x, y);
		try {
			proxy.setLocation(x, y);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void paint (Graphics graph) {
		super.paint(graph) ;
		graph.fillOval (0,0,getWidth(),getHeight()) ;
	}
	
}
