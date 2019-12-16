package drawing;

import java.awt.Color;
import java.awt.Graphics;
import java.rmi.RemoteException;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import editeur.ZoneDeDessin;
import listener.ModificationListener;
import server.RemoteDessinServeur;

public abstract class DessinClient extends JPanel {

	private static final long serialVersionUID = 1L ;

	   ModificationListener ML ;
	   
	   RemoteDessinServeur proxy ;
	   ZoneDeDessin sheet;
	   boolean selection;
	   

	   // constructeur Dessin : param�tr� par une instance de RemoteDessin permettant d'envoyer des informations au r�f�rent sur le serveur 
	   public DessinClient (ZoneDeDessin sheet, RemoteDessinServeur proxy) {
		   this.sheet = sheet;
		   this.proxy = proxy ;
		   
		   setForeground(sheet.getForeground());
		   setOpaque (false) ;
		   ML = new ModificationListener (this);
		   addMouseMotionListener(ML);
		   addMouseListener(ML);
		   setSelection(false);
	   }
	
	   
	  public String getName() {
		 String name="";
		  try {
			name= proxy.getName();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return name;
	  }

	public void setProxyColor (Color color) {
		   setForeground (color) ;
	   }
	   
	   // m�thode permettant de "retailler" un Dessin :
	   // - elle r�alise un "setBounds" pour fournir un retour visuel imm�diat)
	   // - ensuite elle propage le changement au r�f�rent, vie une remote invocation sur le proxy  
	   public void setProxyBounds (int x, int y, int w, int h) {
		   setBounds (x, y, w, h) ;
		   try {
			   proxy.setBounds (x, y, w, h) ;
		   } catch (RemoteException e) {
			   e.printStackTrace();
		   }
	   }

	   // m�thode permettant de d�placer un Dessin :
	   // - elle r�alise un "setLocation" pour fournir un retour visuel imm�diat
	   // - ensuite elle propage le changement au r�f�rent, vie une remote invocation sur le proxy  
	   public void setProxyLocation (int x, int y) {
		   setLocation (x, y) ;
		   try {
			   proxy.setLocation (x, y) ;
		   } catch (RemoteException e) {
			   e.printStackTrace();
		   }
	   }
	   
	   public void paint (Graphics graph) {
			super.paint (graph) ;
		}
	   
	   public void select() {
		   if (!selection) {
			   
			   DessinClient dSelect = sheet.getDessinSelect();
			   if (dSelect!=null) {
			   dSelect.setSelection(false);
			   }
			   
			   this.setSelection(true);
			   sheet.setDessinSelect(this);
			   
		   }
		   else {
			   this.setSelection(false);
			   sheet.setDessinSelect(null);
		   }
		   
	   }

	public boolean isSelection() {
		return selection;
	}

	public void setSelection(boolean selection) {
		this.selection = selection;
		
		if (this.selection) {
			this.setBorder(new LineBorder(Color.black,1));	
		}
		else {
			this.setBorder(new EmptyBorder(0,0,0,0));
			
		}
	}
      
	public ZoneDeDessin getSheet() {
		return sheet;
	}
}
