package server ;

import java.awt.Color;
import java.io.Serializable ;
import java.rmi.RemoteException ;
import java.rmi.server.UnicastRemoteObject ;
import java.util.HashMap ;
import java.util.List;

import communication.EmetteurUnicast ;

// classe de Dessin présente sur le serveur :
// - pour pouvoir invoquer des méthodes à distance, elle doit étendre UnicastRemote object ou implémenter l'interface Remote
// - ici elle fait les deux (car l'interface RemoteDessin étend l'interface Remote)
// - la classe doit également être Serializable si on veut la transmettre sur le réseau
public class DessinServeur extends UnicastRemoteObject implements RemoteDessinServeur, Serializable {

	// les attrribus minimaux d'un Dessin
	private int x ;
	private int y ;
	private int w ;
	private int h ;
	private Color color ;
	private String name ;
	
	// un attribut permettant au Dessin de diffuser directement ses mises à jour, sans passer par le serveur associé
	// - cet attribut n'est pas Serializable, du coup on le déclare transient pour qu'il ne soit pas inclu dans la sérialisation
	protected transient List<EmetteurUnicast> emetteurs ;

	private static final long serialVersionUID = 1L ;

	// constructeur du Dessin sur le serveur : il diffuse alors qu'il faut créer un nouveau dessin sur tous les clients 
	public DessinServeur (String name, List<EmetteurUnicast> senders, Color color) throws RemoteException {
		this.emetteurs = senders ;
		this.name = name ;
		this.color = color ;
		HashMap<String, Object> hm = new HashMap <String, Object> () ;
		hm.put ("x", new Integer (0)) ;
		hm.put ("y", new Integer (0)) ;
		hm.put ("w", new Integer (0)) ;
		hm.put ("h", new Integer (0)) ;
		hm.put ("color", color) ;
		// envoi des mises à jour à tous les clients, via la liste des émetteurs
		for (EmetteurUnicast sender : senders) {
			sender.diffuseMessage ("Dessin", getName (), hm) ;
		}
	}

	// méthode qui met à jour les limites du Dessin, qui diffuse ensuite ce changement à tous les éditeurs clients
	public void setBounds (int x, int y, int w, int h) throws RemoteException {
		this.x = x ;
		this.y = y ;
		this.w = w ;
		this.h = h ;
		HashMap<String, Object> hm = new HashMap <String, Object> () ;
		hm.put ("x", new Integer (x)) ;
		hm.put ("y", new Integer (y)) ;
		hm.put ("w", new Integer (w)) ;
		hm.put ("h", new Integer (h)) ;
		// envoi des mises à jour à tous les clients, via la liste des émetteurs
		for (EmetteurUnicast sender : emetteurs) {
			sender.diffuseMessage ("Bounds", getName (), hm) ;
		}
	}

	// méthode qui met à jour la position du Dessin, qui diffuse ensuite ce changement à tous les éditeurs clients
	public void setLocation (int x, int y) throws RemoteException {
		this.x = x ;
		this.y = y ;
		HashMap<String, Object> hm = new HashMap <String, Object> () ;
		hm.put ("x", new Integer (x)) ;
		hm.put ("y", new Integer (y)) ;
		// envoi des mises à jour à tous les clients, via la liste des émetteurs
		for (EmetteurUnicast emetteur : emetteurs) {
			emetteur.diffuseMessage ("Location", getName (), hm) ;
		}
	}

	public String getName () throws RemoteException {
		return name ;
	}

	@Override
	public int getX() throws RemoteException {
		return x ;
	}

	@Override
	public int getY() throws RemoteException {
		return y ;
	}

	@Override
	public int getWidth() throws RemoteException {
		return w ;
	}

	@Override
	public int getHeight() throws RemoteException {
		return h ;
	}

	@Override
	public Color getColor() throws RemoteException {
		return color;
	}

}