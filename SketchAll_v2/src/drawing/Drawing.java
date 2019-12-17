package drawing;

import server.RemoteDessinServeur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.rmi.RemoteException;


import editeur.ZoneDeDessin;

public class Drawing extends DessinClient {
	private static final long serialVersionUID = 1L;
	
	int radius = 5;
	
	public Drawing (ZoneDeDessin sheet, RemoteDessinServeur proxy) {
		super(sheet, proxy);
		radius = sheet.getRadius();
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
		graph.drawLine (0,0,getWidth()/5,getHeight()/5) ;
	}
	
	public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(10));
        g2.draw(new Line2D.Float(0,0,getWidth(),getHeight()));
        g2.draw(new Line2D.Float(getWidth(),0,0,getHeight()));
    }
	
}
