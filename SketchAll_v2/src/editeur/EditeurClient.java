package editeur ;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.rmi.RemoteException ;
import java.util.ArrayList;
import java.util.HashMap ;

import javax.swing.JPanel;

import main.FrameClient;
import server.RemoteDessinServeur ;

// Classe d'édidion locale :
// - elle propage toutes ses actions au serveur via des "remote invocations"
// - elle reçoit également des mises à jour en provenance du serveur en mode "unicast"

public class EditeurClient extends JPanel {

	private static final long serialVersionUID = 1L ;
	
	Point debut, fin ;
	
	private FrameClient frame;
	
	// l'élément visuel dans lequel on va manipuler des dessins 
	private ZoneDeDessin ZoneDeDessin ;
	private ToolPane toolPane;
	
	
	// une table pour stocker tous les dessins produits :
	// - elle est redondante avec le contenu de la ZoneDe Dessin
	// - mais elle va permettre d'accéder plus rapidement à un Dessin à partir de son nom
	private HashMap<String, Drawing> drawings = new HashMap<String, Drawing> () ;

	// Constructeur à qui on transmet les informations suivantes :
	// - nom de l'éditeur
	// - nom du serveur distant
	// - nom de la machine sur laquelle se trouve le serveur
	// - numéro de port sur lequel est déclaré le serveur sur la machine distante
	public EditeurClient (FrameClient frame) {
		
		this.frame = frame;

		// création d'une ZoneDeDessin
		this.setLayout(new BorderLayout());
		ZoneDeDessin = new ZoneDeDessin (this) ;
		toolPane = new ToolPane (this);
		add(ZoneDeDessin, BorderLayout.CENTER);
		add(toolPane, BorderLayout.WEST);
	    this.setPreferredSize(new Dimension(1200,1000));
	    

		try {
			// récupération de tous les dessins déjà présents sur le serveur
			ArrayList<RemoteDessinServeur> dessinsDistants = frame.getServer().getDessinsPartages() ;
			// ajout de tous les dessins dans la zone de dessin
			for (RemoteDessinServeur rd : dessinsDistants) {
				addDrawing (rd, rd.getName(), rd.getX(), rd.getY(), rd.getWidth(), rd.getHeight(), rd.getColor()) ;
			}
		} catch (Exception e) {
			System.out.println ("probleme liaison CentralManager") ;
			e.printStackTrace () ;
			System.exit (1) ;
		}
		
	}

	// méthode permettant de mettre à jour les limites d'un dessin
	// - elle sera appelée suite à la réception d'un message diffusé par le serveur  
	public synchronized void objectUpdateBounds (String objectName, int x, int y, int w, int h) {
		// récupération du dessin à partir de son nom
		Drawing dessinToUpdate = drawings.get (objectName) ;
		if (dessinToUpdate != null) {
			dessinToUpdate.setBounds (x, y, w, h) ;
		}
	}

	// méthode permettant de mettre à jour la position d'un dessin
	// - elle sera appelée suite à la réception d'un message diffusé par le serveur  
	public synchronized void objectUpdateLocation (String objectName, int x, int y) {
		// récupération du dessin à partir de son nom
		Drawing dessinToUpdate = drawings.get (objectName) ;
		if (dessinToUpdate != null) {
			dessinToUpdate.setLocation (x, y) ;
		}		
	}
	
	public synchronized void objectUpdateColor (String objectName, Color color) {
		// récupération du dessin à partir de son nom
		Drawing dessinToUpdate = drawings.get (objectName) ;
		if (dessinToUpdate != null) {
			dessinToUpdate.setForeground (color) ;
		}		
	}

	// méthode permettant de créer un nouveau dessin suite à une interaction de l'utilisateur
	// - elle va devoir transmettre cette création au serveur et lui demander un proxy pour ce dessin   
	public synchronized Drawing creerDessin (int x, int y, int w, int h, Color color) {
		RemoteDessinServeur proxy = null ;
		String proxyName = null ;
		try {
			// ajout distant d'un dessin et récupération de son proxy 
			proxy = frame.getServer().addDessin (x, y, w, h, color);
			// récupération du nom attribué par le serveur :
			// - on le fait ici pour ne pas multiplier les blocs "try catch" nécessaires à chaque invocation distante de méthode 
			proxyName = proxy.getName () ;
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
		// création d'un nouveau dessin
		Object dessin;
		dessin = new Drawing(ZoneDeDessin, proxy);
		// ajout du dessin dans la liste des dessins s'il n'y est pas déjà :
		// - on a pu recevoir une information de création en parallèle vie le Thread de réception de messages diffusés par le serveur
		if (! drawings.containsKey (proxyName)) {
			drawings.put (proxyName, ((Drawing) dessin)) ;
		} else {
		}
		return ((Drawing)dessin) ;
	}

	// méthode permettant d'ajouter localement un dessin déjà présent sur le serveur
	// - elle sera appelée suite à la réception d'un message diffusé par le serveur
	public synchronized void addDrawing (String proxyName, int x, int y, int w, int h, Color color) {
		//System.out.println("ajout du dessin " + proxyName);
		// on ne l'ajoute que s'il n'est pas déjà présent
		// - il pourrait déjà être présent si il avait été créé localement par une interaction dans cet éditeur local
		if (! drawings.containsKey (proxyName)) {
			RemoteDessinServeur proxy = null ;
			try {
				// récupération du proxy via une demande au serveur
				proxy = frame.getServer().getDessin (proxyName);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			if (proxy == null) {
				//System.out.println("proxy " + proxyName + " null");
			}
			// ajout du dessin
			addDrawing (proxy, proxyName, x, y, w, h, color) ;
		} else {
			//System.out.println ("dessin " + proxyName + " était déjà présent") ;
		}
	}

	// méthode d'ajout d'un dessin : factorisation de code
	public void addDrawing (RemoteDessinServeur proxy, String proxyName, int x, int y, int w, int h, Color color) {
		// création effective d'un dessin
		Drawing dessin;
		dessin = new Drawing (ZoneDeDessin, proxy);
		
		// ajout du dessin dans la liste des dessins
		drawings.put (proxyName, dessin) ;
		// initialisation des limites du dessin
		dessin.setForeground(color);
		dessin.setBounds(x, y, w, h) ;
		// ajout du dessin dans la zone de dessin, au premier plan (grâce au "0" dans le add)
		ZoneDeDessin.add (dessin, 0) ;
	}
	
	public ZoneDeDessin getZoneDeDessin() {
		return ZoneDeDessin;
	}
	
	public ToolPane getToolPane() {
		return toolPane;
	}
	
}
