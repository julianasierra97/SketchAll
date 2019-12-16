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

//classe d'�diteur pr�sente sur le serveur :
//- pour pouvoir invoquer des m�thodes à distance, elle doit �tendre UnicastRemote object ou impl�menter l'interface Remote
//- ici elle fait les deux (car l'interface RemoteEditeurServeur �tend l'interface Remote)
//- la classe doit �galement être Serializable si on veut la transmettre sur le r�seau
public class EditeurServeur extends UnicastRemoteObject implements RemoteEditeurServeur, Serializable {

	// le nom du serveur
	protected String nomServeur ;

	// le port sur lequel est d�clar� le serveur
	protected int portRMI ;
	
	// la machine sur laquelle se trouve le serveur
	protected String nomMachineServeur ;

	// un entier pour g�n�rer des noms de dessins diff�rents
	protected int idDrawing ;

	// un entier pour g�n�rer des noms de dessins diff�rents
	protected int portEmission ;
	
	// un diffuseur à une liste d'abonn�s
	private List<EmetteurUnicast> transmitters ;
	
	// une strutcure pour stocker tous les dessins et y acc�der facilement 
	private HashMap<String, RemoteDessinServeur> sharedDrawings = new HashMap<String, RemoteDessinServeur> () ;
	
	// liste des joueurs
	private HashMap<String, RemoteUserServeur> playerList = new HashMap<String, RemoteUserServeur> () ;
	
	private HashMap<String,String> loginList;
	

	// le constructeur du serveur : il le d�clare sur un port rmi de la machine d'ex�cution
	protected EditeurServeur (String nomServeur, String nomMachineServeur, int portRMIServeur,	int portEmissionUpdate, HashMap<String,String> loginList) throws RemoteException {
		this.nomServeur = nomServeur ;
		this.nomMachineServeur = nomMachineServeur ;
		this.portRMI = portRMIServeur ;
		this.portEmission = portEmissionUpdate ;
		transmitters = new ArrayList<EmetteurUnicast> () ;
		this.loginList=loginList;
		try {
			// attachcement sur serveur sur un port identifi� de la machine d'ex�cution
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

	// m�thode permettant d'enregistrer un dessin sur un port rmi sur la machine du serveur :
	// - comme cela on pourra �galement invoquer directement des m�thodes en rmi �galement sur chaque dessin
	public void registerObject (RemoteDessinServeur dessin) {
		try {
			Naming.rebind ("//" + nomMachineServeur + ":" + portRMI + "/" + dessin.getName (), dessin) ;
		} catch (Exception e) {
			e.printStackTrace () ;
			try {
				System.out.println ("�chec lors de l'ajout de l'objet " + dessin.getName () + " sur le serveur " + nomMachineServeur + "/"+ portRMI) ;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	//m�thode permettant d'enregistrer un utilisateur sur un port rmi
	public void registerPlayer (RemoteUserServeur player) {
		try {
			Naming.rebind ("//" + nomMachineServeur + ":" + portRMI + "/" + player.getUsername(), player) ;
		} catch (Exception e) {
			e.printStackTrace () ;
			try {
				System.out.println ("�chec lors de l'ajout de l'objet " + player.getUsername() + " sur le serveur " + nomMachineServeur + "/"+ portRMI) ;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L ;

	// m�thodes permettant d'ajouter un nouveau dessin dans le syst�me
	@Override
	public synchronized RemoteDessinServeur addDessin (int x, int y, int w, int h, Color color, String shapeType) throws RemoteException {
		// cr�ation d'un nouveau nom, unique, destin� à servir de cl� d'acc�s au dessin
		// et cr�ation d'un nouveau dessin de ce nom et associ� �galement à un �metteur multicast...
		// attention : la classe Dessin utilis�e ici est celle du package serveur (et pas celle du package client)
		RemoteDessinServeur drawing = new DessinServeur ("dessin" + nextId (), transmitters, color, shapeType) ;
		// enregistrement du dessin pour acc�s rmi distant
		registerObject (drawing) ;
		// ajout du dessin dans la liste des dessins pour acc�s plus efficace au dessin
		sharedDrawings.put (drawing.getName (), drawing) ;
		// renvoi du dessin à l'�diteur local appelant : l'�diteur local r�cup�rera seulement un RemoteDessin
		// sur lequel il pourra invoquer des m�thodes en rmi et qui seront relay�es au r�f�rent associ� sur le serveur  
		return drawing ;
	}

	// m�thode permettant d'acc�der à un proxy d'un des dessins
	@Override
	public synchronized RemoteDessinServeur getDessin (String name) throws RemoteException {
		//System.out.println ("getDessin " + name + " dans dessinsPartag�s = " + dessinsPartages) ;
		return sharedDrawings.get (name) ;
	}
	
	

	// m�thode qui incr�mente le compteur de dessins pour avoir un id unique pour chaque dessin :
	// dans une version ult�rieure avec r�cup�ration de dessins à aprtir d'une sauvegarde, il faudra �galement avoir sauvegard� ce nombre...
	public int nextId () {
		idDrawing++ ; 
		return idDrawing ; 
	}

	// m�thode permettant de r�cup�rer la liste des dessins : utile lorsqu'un �diteur client se connecte 
	@Override
	public synchronized ArrayList<RemoteDessinServeur> getDessinsPartages () throws RemoteException {
		return new ArrayList<RemoteDessinServeur> (sharedDrawings.values()) ;
	}

	// m�thode indiquant quel est le port d'�mission/r�ception à utiliser pour le client qui rejoint le serveur
	// on utilise une valeur arbitraitre de port qu'on incr�mente de 1 à chaque arriv�e d'un nouveau client
	// cela permettra d'avoir plusieurs clients sur la même machine, chacun avec un canal de communication distinct
	// sur un port diff�rent des autres clients
	@Override
	public int getPortEmission (InetAddress clientAdress) throws RemoteException {
		EmetteurUnicast transmitter = new EmetteurUnicast (clientAdress, portEmission++) ;
		transmitters.add (transmitter) ;
		return (transmitter.getPortEmission ()) ;
	}

	// m�thode permettant juste de v�rifier que le serveur est lanc�
	@Override
	public void answer (String question) throws RemoteException {
//		System.out.println ("SERVER : the question was : " + question) ;   
	}
	
	public synchronized RemoteUserServeur addPlayer(String username) throws RemoteException{
		RemoteUserServeur player = new UserServeur(username, transmitters);
		registerPlayer(player);
		playerList.put (player.getUsername (), player) ;
		return player;
	}
	
	public synchronized RemoteUserServeur getPlayer (String username) throws RemoteException {
		return playerList.get (username) ;
	}

	public synchronized HashMap<String,RemoteUserServeur> getPlayerList () throws RemoteException {
		return this.playerList;
	}
	
	public void sendMessage(String username, String message) throws RemoteException{
		HashMap<String, Object> hm = new HashMap <String, Object> () ;
		hm.put("name", username);
		hm.put("message", message);
		for (EmetteurUnicast sender : transmitters) {
			sender.diffuseMessage ("Send message", username, hm) ;
		}
	}
	
	public boolean loginCorrect(String username, String password) throws RemoteException{
		if (loginList.containsKey(username)) {
			return (loginList.get(username).equals(password));
		}
		else {
			return false;
		}
			
		
	}
	
	public void setClientInGame(String username, boolean inGame) throws RemoteException {
		
		playerList.get(username).setInGame(inGame);
		HashMap<String, Object> hm = new HashMap <String, Object> () ;
		
		hm.put("inGame", inGame);
		hm.put ("name", username);
		// envoi des mises à jour à tous les clients, via la liste des émetteurs
		for (EmetteurUnicast sender : transmitters) {
			sender.diffuseMessage ("InGame", username, hm) ;
		}
	}
	
	public void deleteDessin(String name) {
		if (sharedDrawings.containsKey(name)){
			sharedDrawings.remove(name);
			HashMap<String, Object> hm = new HashMap <String, Object> () ;
			hm.put ("name", name);
			// envoi des mises � jour � tous les clients, via la liste des �metteurs
			for (EmetteurUnicast sender : transmitters) {
				sender.diffuseMessage ("Delete dessin", name, hm) ;
			}
		}
	}

}
