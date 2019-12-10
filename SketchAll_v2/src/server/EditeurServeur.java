package server ;

import java.awt.Color;
import java.io.Serializable ;
import java.net.InetAddress;
import java.rmi.Naming ;
import java.rmi.RemoteException ;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap ;
import java.util.List;

import communication.EmetteurUnicast ;
import main.FrameClient;

//classe d'éditeur présente sur le serveur :
//- pour pouvoir invoquer des méthodes à distance, elle doit étendre UnicastRemote object ou implémenter l'interface Remote
//- ici elle fait les deux (car l'interface RemoteEditeurServeur étend l'interface Remote)
//- la classe doit également être Serializable si on veut la transmettre sur le réseau
public class EditeurServeur extends UnicastRemoteObject implements RemoteEditeurServeur, Serializable {

	// le nom du serveur
	protected String nomServeur ;

	// le port sur lequel est déclaré le serveur
	protected int portRMI ;
	
	// la machine sur laquelle se trouve le serveur
	protected String nomMachineServeur ;

	// un entier pour générer des noms de dessins différents
	protected int idDrawing ;

	// un entier pour générer des noms de dessins différents
	protected int portEmission ;
	
	// un diffuseur à une liste d'abonnés
	private List<EmetteurUnicast> transmitters ;
	
	// une strutcure pour stocker tous les dessins et y accéder facilement 
	private HashMap<String, RemoteDessinServeur> sharedDrawings = new HashMap<String, RemoteDessinServeur> () ;
	
	// liste des joueurs
	private List<String> playerList;

	// le constructeur du serveur : il le déclare sur un port rmi de la machine d'exécution
	protected EditeurServeur (String nomServeur, String nomMachineServeur, int portRMIServeur,	int portEmissionUpdate) throws RemoteException {
		this.nomServeur = nomServeur ;
		this.nomMachineServeur = nomMachineServeur ;
		this.portRMI = portRMIServeur ;
		this.portEmission = portEmissionUpdate ;
		transmitters = new ArrayList<EmetteurUnicast> () ;
		playerList = new ArrayList<String>();
		try {
			// attachcement sur serveur sur un port identifié de la machine d'exécution
			Naming.rebind ("//" + nomMachineServeur + ":" + portRMIServeur + "/" + nomServeur, this) ;
			System.out.println ("pret pour le service") ;
		} catch (Exception e) {
			System.out.println ("pb RMICentralManager") ;
		}
	}

	@Override
	public int getRMIPort () {
		return portRMI ;
	}

	// méthode permettant d'enregistrer un dessin sur un port rmi sur la machine du serveur :
	// - comme cela on pourra également invoquer directement des méthodes en rmi également sur chaque dessin
	public void registerObject (RemoteDessinServeur dessin) {
		try {
			Naming.rebind ("//" + nomMachineServeur + ":" + portRMI + "/" + dessin.getName (), dessin) ;
		} catch (Exception e) {
			e.printStackTrace () ;
			try {
				System.out.println ("échec lors de l'ajout de l'objet " + dessin.getName () + " sur le serveur " + nomMachineServeur + "/"+ portRMI) ;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	// méthodes permettant d'ajouter un nouveau dessin dans le système
	@Override
	public synchronized RemoteDessinServeur addDessin (int x, int y, int w, int h, Color color, String shapeType) throws RemoteException {
		// création d'un nouveau nom, unique, destiné à servir de clé d'accès au dessin
		// et création d'un nouveau dessin de ce nom et associé également à un émetteur multicast...
		// attention : la classe Dessin utilisée ici est celle du package serveur (et pas celle du package client)
		RemoteDessinServeur drawing = new DessinServeur ("dessin" + nextId (), transmitters, color, shapeType) ;
		// enregistrement du dessin pour accès rmi distant
		registerObject (drawing) ;
		// ajout du dessin dans la liste des dessins pour accès plus efficace au dessin
		sharedDrawings.put (drawing.getName (), drawing) ;
		// renvoi du dessin à l'éditeur local appelant : l'éditeur local récupèrera seulement un RemoteDessin
		// sur lequel il pourra invoquer des méthodes en rmi et qui seront relayées au référent associé sur le serveur  
		return drawing ;
	}

	// méthode permettant d'accéder à un proxy d'un des dessins
	@Override
	public synchronized RemoteDessinServeur getDessin (String name) throws RemoteException {
		//System.out.println ("getDessin " + name + " dans dessinsPartagés = " + dessinsPartages) ;
		return sharedDrawings.get (name) ;
	}

	// méthode qui incrémente le compteur de dessins pour avoir un id unique pour chaque dessin :
	// dans une version ultérieure avec récupération de dessins à aprtir d'une sauvegarde, il faudra également avoir sauvegardé ce nombre...
	public int nextId () {
		idDrawing++ ; 
		return idDrawing ; 
	}

	// méthode permettant de récupérer la liste des dessins : utile lorsqu'un éditeur client se connecte 
	@Override
	public synchronized ArrayList<RemoteDessinServeur> getDessinsPartages () throws RemoteException {
		return new ArrayList<RemoteDessinServeur> (sharedDrawings.values()) ;
	}

	// méthode indiquant quel est le port d'émission/réception à utiliser pour le client qui rejoint le serveur
	// on utilise une valeur arbitraitre de port qu'on incrémente de 1 à chaque arrivée d'un nouveau client
	// cela permettra d'avoir plusieurs clients sur la même machine, chacun avec un canal de communication distinct
	// sur un port différent des autres clients
	@Override
	public int getPortEmission (InetAddress clientAdress) throws RemoteException {
		EmetteurUnicast transmitter = new EmetteurUnicast (clientAdress, portEmission++) ;
		transmitters.add (transmitter) ;
		return (transmitter.getPortEmission ()) ;
	}

	// méthode permettant juste de vérifier que le serveur est lancé
	@Override
	public void answer (String question) throws RemoteException {
//		System.out.println ("SERVER : the question was : " + question) ;   
	}
	
	public void addPlayer(String username) throws RemoteException{
		this.playerList.add(username);
	}

}
