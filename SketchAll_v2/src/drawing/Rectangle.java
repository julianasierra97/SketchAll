package drawing;

import server.RemoteDessinServeur;

import java.awt.Graphics;

import editeur.ZoneDeDessin;

public class Rectangle extends DessinClient {
	private static final long serialVersionUID = 1L;
	
	int x2;
	int y2;
	
	public Rectangle (ZoneDeDessin sheet, RemoteDessinServeur proxy) {
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
		//graph.drawRect(0,0,getWidth()-1,getHeight()-1);
		graph.fillRect(2,2,getWidth()-5,getHeight()-5);
	}
	
}
