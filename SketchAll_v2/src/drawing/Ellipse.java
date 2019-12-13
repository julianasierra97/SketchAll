package drawing;

import server.RemoteDessinServeur;

import java.awt.Graphics;
import java.awt.geom.*;

import editeur.ZoneDeDessin;

public class Ellipse extends DessinClient {
	private static final long serialVersionUID = 1L;
	
	int x2;
	int y2;
	
	public Ellipse (ZoneDeDessin sheet, RemoteDessinServeur proxy) {
		super(sheet, proxy);
	}
	
	public void setProxyBounds (int x, int y, int w, int h) {
		super.setProxyBounds(Math.min(x,x+w),Math.min(y,y+h),Math.abs(w),Math.abs(h));
	}
	
	public void setProxyLocation (int x, int y) {
		super.setProxyLocation (x,y);
	}
	
	public void paint (Graphics graph) {
		super.paint (graph) ;
		graph.fillOval(2,2,getWidth()-4,getHeight()-4) ;
	}
	
	public boolean contains (int x, int y) {
		java.awt.Shape shape;
		shape = new Ellipse2D.Float(0,0,getWidth(),getHeight());
		return (shape.contains(x,y));
	}

}
