package server;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

import communication.EmetteurUnicast;

public class UserServeur implements RemoteUserServeur, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// attributs minimaux de l'utilisateur
	private String username;
	private int points;
	private boolean sketcher;
	private boolean inGame;
	
	// un attribut permettant au User de diffuser directement ses mises à jour, sans passer par le serveur associé
		// - cet attribut n'est pas Serializable, du coup on le déclare transient pour qu'il ne soit pas inclu dans la sérialisation
		protected transient List<EmetteurUnicast> emetteurs ;
		
		public UserServeur (String username, List<EmetteurUnicast> senders) throws RemoteException {
			this.emetteurs = senders ;
			this.username = username ;
			this.sketcher = true;
			this.points = 0;
			this.inGame=false;
			HashMap<String, Object> hm = new HashMap <String, Object> () ;
			hm.put ("points", new Integer (0)) ;
			hm.put ("sketcher", sketcher) ;
			hm.put ("name", username) ;
			// envoi des mises à jour à tous les clients, via la liste des émetteurs
			for (EmetteurUnicast sender : senders) {
				sender.diffuseMessage ("User", getUsername (), hm) ;
			}
		}
		

		public void setSketcher(boolean sketcher, String word) throws RemoteException{
			this.sketcher = sketcher;
			HashMap<String, Object> hm = new HashMap <String, Object> () ;
			hm.put("sketcher", sketcher);
			hm.put ("name", this.username);
			hm.put("word", word);
			// envoi des mises à jour à tous les clients, via la liste des émetteurs
			for (EmetteurUnicast sender : emetteurs) {
				sender.diffuseMessage ("Sketcher", getUsername (), hm) ;
			}
		}
		
		public void setPoints(int points) throws RemoteException{
			this.points = points;
			HashMap<String, Object> hm = new HashMap <String, Object> () ;
			hm.put("points", points);
			hm.put ("name", this.username);
			// envoi des mises à jour à tous les clients, via la liste des émetteurs
			for (EmetteurUnicast sender : emetteurs) {
				sender.diffuseMessage ("Points", getUsername (), hm) ;
			}
		}

		public boolean isSketcher() throws RemoteException{
			return sketcher;
		}

		public String getUsername() throws RemoteException {
			return username;
		}

		public int getPoints() throws RemoteException {
			return points;
		}
		
		public boolean isInGame() {
			return inGame;
		}

		public void setInGame(boolean inGame) throws RemoteException {
			this.inGame = inGame;
			
		}


		@Override
		public boolean getInGame() {
			// TODO Auto-generated method stub
			return inGame;
		}
		
		public void startRound() throws RemoteException{
			HashMap<String, Object> hm = new HashMap <String, Object> () ;
			hm.put ("name", this.username);
			// envoi des mises à jour à tous les clients, via la liste des émetteurs
			for (EmetteurUnicast sender : emetteurs) {
				sender.diffuseMessage ("Start", getUsername (), hm) ;
			}
		}
		
		public void hasWon() throws RemoteException{
			HashMap<String, Object> hm = new HashMap <String, Object> () ;
			hm.put ("name", this.username);
			// envoi des mises à jour à tous les clients, via la liste des émetteurs
			for (EmetteurUnicast sender : emetteurs) {
				sender.diffuseMessage ("Has won", getUsername (), hm) ;
			}
		}
}
