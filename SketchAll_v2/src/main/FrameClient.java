package main;


import java.awt.BorderLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import communication.RecepteurUnicast;
import editeur.EditeurClient;
import server.RemoteEditeurServeur;
import server.RemoteUserServeur;

public class FrameClient extends JFrame{
	
private static final long serialVersionUID = 1L ;
	
	
	// le Thread pour pouvoir recevoir des mises à jour en provenance du serveur
	private Thread threadRecepteur ;
	
	// le r�cepteur de messages diffus�s aux abonn�s
	private RecepteurUnicast RecepteurUnicast ;
	
	// le serveur distant qui centralise toutes les informations
	private RemoteEditeurServeur server ;
	
	// le nom de la machine qui h�berge l'�diteur local
	protected String clientMachineName ;
	
	// le port rmi sur lequel est d�clar� le serveur distant
	protected int RMIPort ;
	
	// le nom de la machine sur laquelle se trouve le serveur distant :
	// - le système saura calculer l'adresse IP de la machine à partir de son nom
	// - sinon on met directement l'adresse IP du serveur dans cette chaine de caractère 
	protected String serverMachineName ;
	
	private EditeurClient editeur;
	
	private String username;
	
	private HashMap<String, Player> players;


	private ChatPane chatPane;
	
	

	




		// Constructeur à qui on transmet les informations suivantes :
		// - nom de l'�diteur
		// - nom du serveur distant
		// - nom de la machine sur laquelle se trouve le serveur
		// - num�ro de port sur lequel est d�clar� le serveur sur la machine distante
		FrameClient (final String clientMachineName, final String serverEditorName, final String serverMachineName, final int serverRMIPort, final String username) {
			this.clientMachineName = clientMachineName ;
			this.username = username;
			players = new HashMap<String, Player> ();
			try {
				// tentative de connexion au serveur distant
				server = (RemoteEditeurServeur)Naming.lookup ("//" + serverMachineName + ":" + serverRMIPort + "/" + serverEditorName) ;
				// invocation d'une première m�thode juste pour test
				server.answer ("hello from " + getName ()) ;
				
			} catch (Exception e) {
				System.out.println ("probleme liaison CentralManager") ;
				e.printStackTrace () ;
				System.exit (1) ;
			}
			try {
				// cr�ation d'un r�cepteur unicast en demandant l'information de num�ro port au serveur
				// en même temps on transmet au serveur l'adresse IP de la machine du client au serveur
				// de façon à ce que ce dernier puisse par la suite envoyer des messages de mise à jour à ce r�cepteur 
				RecepteurUnicast = new RecepteurUnicast (InetAddress.getByName (clientMachineName), server.getPortEmission (InetAddress.getByName (clientMachineName))) ;
				// on aimerait bien demander automatiquement quel est l'adresse IP de la machine du client,
				// mais le problème est que celle-ci peut avoir plusieurs adresses IP (filaire, wifi, ...)
				// et qu'on ne sait pas laquelle sera retourn�e par InetAddress.getLocalHost ()...
				//recepteurUnicast = new RecepteurUnicast (serveur.getPortEmission (InetAddress.getLocalHost ())) ;
				RecepteurUnicast.setLocalClient (this) ;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			// cr�ation d'un Thread pour pouvoir recevoir les messages du serveur en parallèle des interactions avec les dessins
			threadRecepteur = new Thread (RecepteurUnicast) ;
			// d�marrage effectif du Thread
			threadRecepteur.start () ;
			
			try {
				// récupération de tous les dessins déjà présents sur le serveur
				ArrayList<RemoteUserServeur> onlinePlayers = new ArrayList<RemoteUserServeur> (server.getPlayerList().values()) ;
				// ajout de tous les dessins dans la zone de dessin
				for (RemoteUserServeur player: onlinePlayers) {
					addPlayer(player.getUsername(), player);
				}
			} catch (Exception e) {
				System.out.println ("probleme liaison CentralManager") ;
				e.printStackTrace () ;
				System.exit (1) ;
			}
			
			// ajout du joueur dans la liste serveur
			try {
				if (! server.getPlayerList().containsKey (this.username)) {
					RemoteUserServeur proxy = server.addPlayer(this.username);
					players.put(this.username, new Player(proxy, this.username));
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// demande d'affichage de l'�diteur
			// - faite "seulement"/"tardivement" ici pour que tous les objets r�cup�r�s du serveur apparaissent bien du premier coup
			
			this.setSize(1500,1000);
			this.setLayout(new BorderLayout());
			this.editeur = new EditeurClient(this);
			this.getContentPane().add(editeur,BorderLayout.CENTER);
			this.setTitle("Paint");
		    this.setLocationRelativeTo(null);
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    chatPane= new ChatPane(this);
		    this.getContentPane().add(chatPane,BorderLayout.EAST);
			
			setVisible (true) ;
			
		}
		
		


		public synchronized void addPlayer(String username) {
			if (! players.containsKey(username)) {
				try {
					this.addPlayer(username,server.getPlayer(username));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public synchronized void addPlayer(String username, RemoteUserServeur proxy) {
			
			players.put(username, new Player(proxy, username));
			System.out.println(players);
			
		}
		
		public synchronized void setPlayerSketcher(String username, boolean sketcher) {
			players.get(username).setSketcher(sketcher);
		}
		
		public synchronized void setPlayerPoints(String username, int points) {
			players.get(username).setPoints(points);
		}

		public HashMap<String, Player> getPlayers() {
			return players;
		}


		public RemoteEditeurServeur getServer() {
			return server;
		}

		public EditeurClient getEditeur() {
		return editeur;
		}
		
		
		public ChatPane getChatPane() {
			return chatPane;
		}
		
		public String getUsername() {
			return username;
		}

		public void setChatPane(ChatPane chatPane) {
			this.chatPane = chatPane;
		}
}
