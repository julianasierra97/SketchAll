package communication;

import java.awt.Color;
import java.io.ByteArrayInputStream ;
import java.io.ObjectInputStream ;
import java.net.DatagramPacket ;
import java.net.DatagramSocket;
import java.net.InetAddress ;
import java.util.HashMap ;

import main.FrameClient;


//---------------------------------------------------------------------
// classe permettant de recevoir des messages diffusés à une adresse (en multicast)

public class RecepteurUnicast extends Thread implements Runnable {

	// la socket sur laquelle on va lire les messages
	private transient DatagramSocket socketReception ;

	// l'éditeur local qu'on préviendra suite aux messages reçus
	private FrameClient localClient ;
	public void setLocalClient (FrameClient localClient) {
		this.localClient = localClient ;
	}

	// les données à récupérer conformément au format des données envoyées :
	// - une chaine de caractère pour décrire l'action à réaliser
	private String command = new String () ;
	// - une chaine de caractère pour déterminer l'objet coble du message
	private String name = new String () ;
	// - une HashMap dans laquelle on récupèrera les paramètres nécessaires aux actions à effectuer
	private HashMap<String, Object> hm = new HashMap<String, Object> () ;

	public RecepteurUnicast (final InetAddress receiverAdress, final int receiverPort) {
		socketReception = null ;
		try {
			// création d'une socket de réception des messages adressés à ce groupe de diffusion
			socketReception = new DatagramSocket (receiverPort, receiverAdress) ;
			System.out.println ("socket réception : " + socketReception.getLocalPort() + " " + socketReception.getInetAddress ()) ;
		} catch (Exception e) {
			e.printStackTrace () ;
		}
	}

	// méthode de réception du message : on extrait d'un flux de très bas niveau des informations formatées correctement 
	@SuppressWarnings ("unchecked")
	public void receive () {
		try {
			// réception d'un paquet bas niveau
			byte [] message = new byte [1024] ;
			DatagramPacket packet = new DatagramPacket (message, message.length) ;
			socketReception.receive (packet) ;
			// extraction des informations au format type à partir du paquet
			ByteArrayInputStream bais = new ByteArrayInputStream (packet.getData ()) ;
			ObjectInputStream ois = new ObjectInputStream (bais) ;
			command = (String)ois.readObject () ;
			name = (String)ois.readObject () ;
			hm = (HashMap<String, Object>)ois.readObject () ;
		} catch (Exception e) {
			e.printStackTrace () ;
		}
	}

	// méthode qui permet de recevoir des messages en parralèle des interactions utilisateur
	public void run () {
		while (true) {
			// réception effective du message et décodage dans command, name et hm
			receive () ;
			// traitement du message en fonction de son intitulé
			if (command.equals ("Bounds")) {
				//System.out.println ("received Bounds") ;
				// mise à jour des limites d'un dessin
				localClient.getEditeur().objectUpdateBounds(name, (int)hm.get ("x"), (int)hm.get ("y"), (int)hm.get ("w"), (int)hm.get ("h"));
			} else if (command.equals ("Location")) {
				//System.out.println ("received Location") ;
				// mise à jour de la positiuon d'un dessin
				localClient.getEditeur().objectUpdateLocation (name, (int)hm.get ("x"), (int)hm.get ("y"));
			} else if (command.equals ("Dessin")) {
				//System.out.println ("received Dessin") ;
				// ajout d'un dessin
				localClient.getEditeur().addDrawing (name, (int)hm.get("x"), (int)hm.get("y"), (int)hm.get("w"), (int)hm.get("h"), (Color)hm.get("color"), (String)hm.get("shapeType"));
			}else if (command.equals ("User")) {
				//System.out.println ("received User") ;
				// ajout d'un joueur
				localClient.addPlayer(name);
			}
		}
	}

}