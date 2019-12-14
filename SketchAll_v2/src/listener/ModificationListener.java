package listener ;

import java.awt.Cursor;
import java.awt.Point ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.event.MouseMotionListener ;

import drawing.DessinClient;
import editeur.ZoneDeDessin;
import javafx.scene.Parent;

//---------------------------------------------------------------------------

public class ModificationListener implements MouseListener, MouseMotionListener {

   Point offset ;
   DessinClient drawing ;
   ZoneDeDessin zdd;

   public ModificationListener (DessinClient drawing) {
      this.drawing = drawing ;
      this.zdd =drawing.getSheet();
   }

   public void mouseClicked (MouseEvent e) {
	   drawing.select();
   }

   public void mouseEntered (MouseEvent e) {
	   if (drawing.isSelection()) {
		   drawing.setCursor (Cursor.getPredefinedCursor (Cursor.MOVE_CURSOR)) ;
	   }
   }

   public void mouseExited (MouseEvent e) {
   }

   public void mousePressed (MouseEvent e) {
	   if (drawing.isSelection()) {
		   offset = e.getPoint () ;
	   }
	   else {
		   zdd.dispatchEvent(transformEvent(e, MouseEvent.MOUSE_PRESSED));
	   }
	   
   }

   public void mouseReleased (MouseEvent e) {
	   if (drawing.isSelection()) {
		   zdd.dispatchEvent(transformEvent(e, MouseEvent.MOUSE_RELEASED));
	   }
   }

   public void mouseDragged (MouseEvent e) {
	   if (drawing.isSelection()) {
		   drawing.setProxyLocation (drawing.getX () + e.getX () - offset.x, drawing.getY () + e.getY () - offset.y) ;
	   }
	   else {
		   zdd.dispatchEvent(transformEvent(e, MouseEvent.MOUSE_DRAGGED));
	   }
   }

   public void mouseMoved (MouseEvent e) {
   }
   
   public MouseEvent transformEvent(MouseEvent e, int eventType) {
	   int x = e.getX()+drawing.getX();
	   int y = e.getY()+drawing.getY();
	   return new MouseEvent(zdd,eventType,e.getWhen(),e.getModifiers(),x,y,e.getClickCount(),false);
	   
   }

}