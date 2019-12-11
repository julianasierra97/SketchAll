package listener ;

import java.awt.Cursor;
import java.awt.Point ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.event.MouseMotionListener ;

import drawing.DessinClient;

//---------------------------------------------------------------------------

public class ModificationListener implements MouseListener, MouseMotionListener {

   Point offset ;
   DessinClient drawing ;

   public ModificationListener (DessinClient drawing) {
      this.drawing = drawing ;
   }

   public void mouseClicked (MouseEvent e) {
   }

   public void mouseEntered (MouseEvent e) {
		drawing.setCursor (Cursor.getPredefinedCursor (Cursor.MOVE_CURSOR)) ;
   }

   public void mouseExited (MouseEvent e) {
		drawing.setCursor (Cursor.getPredefinedCursor (Cursor.CROSSHAIR_CURSOR)) ;
   }

   public void mousePressed (MouseEvent e) {
      offset = e.getPoint () ;
   }

   public void mouseReleased (MouseEvent e) {
   }

   public void mouseDragged (MouseEvent e) {
      drawing.setProxyLocation (drawing.getX () + e.getX () - offset.x, drawing.getY () + e.getY () - offset.y) ;
   }

   public void mouseMoved (MouseEvent e) {
   }

}