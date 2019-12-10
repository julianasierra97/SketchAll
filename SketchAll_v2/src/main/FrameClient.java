package main;


import java.awt.BorderLayout;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import javax.swing.JFrame;

import communication.RecepteurUnicast;
import editeur.EditeurClient;
import server.RemoteEditeurServeur;

public class FrameClient extends JFrame{
	
private static final long serialVersionUID = 1L ;
	
	
	// le Thread pour pouvoir recevoir des mises à jour en provenance du serveur
	private Thread threadRecepteur ;
	
	// le récepteur de messages diffusés aux abonnés
	private RecepteurUnicast RecepteurUnicast ;
	
	// le serveur distant qui centralise toutes les informations
	private RemoteEditeurServeur server ;
	
	// le nom de la machine qui héberge l'éditeur local
	protected String clientMachineName ;
	
	// le port rmi sur lequel est déclaré le serveur distant
	protected int RMIPort ;
	
	// le nom de la machine sur laquelle se trouve le serveur distant :
	// - le système saura calculer l'adresse IP de la machine à partir de son nom
	// - sinon on met directement l'adresse IP du serveur dans cette chaine de caractère 
	protected String serverMachineName ;
	
	private EditeurClient editeur;
	
	

		// Constructeur à qui on transmet les informations suivantes :
		// - nom de l'éditeur
		// - nom du serveur distant
		// - nom de la machine sur laquelle se trouve le serveur
		// - numéro de port sur lequel est déclaré le serveur sur la machine distante
		FrameClient (final String clientMachineName, final String serverEditorName, final String serverMachineName, final int serverRMIPort) {
			this.clientMachineName = clientMachineName ;
			try {
				// tentative de connexion au serveur distant
				server = (RemoteEditeurServeur)Naming.lookup ("//" + serverMachineName + ":" + serverRMIPort + "/" + serverEditorName) ;
				// invocation d'une première méthode juste pour test
				server.answer ("hello from " + getName ()) ;
				
			} catch (Exception e) {
				System.out.println ("probleme liaison CentralManager") ;
				e.printStackTrace () ;
				System.exit (1) ;
			}
			try {
				// création d'un récepteur unicast en demandant l'information de numéro port au serveur
				// en même temps on transmet au serveur l'adresse IP de la machine du client au serveur
				// de façon à ce que ce dernier puisse par la suite envoyer des messages de mise à jour à ce récepteur 
				RecepteurUnicast = new RecepteurUnicast (InetAddress.getByName (clientMachineName), server.getPortEmission (InetAddress.getByName (clientMachineName))) ;
				// on aimerait bien demander automatiquement quel est l'adresse IP de la machine du client,
				// mais le problème est que celle-ci peut avoir plusieurs adresses IP (filaire, wifi, ...)
				// et qu'on ne sait pas laquelle sera retournée par InetAddress.getLocalHost ()...
				//recepteurUnicast = new RecepteurUnicast (serveur.getPortEmission (InetAddress.getLocalHost ())) ;
				RecepteurUnicast.setLocalClient (this) ;
			} catch (RemoteException e1) {
				e1.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			// création d'un Thread pour pouvoir recevoir les messages du serveur en parallèle des interactions avec les dessins
			threadRecepteur = new Thread (RecepteurUnicast) ;
			// démarrage effectif du Thread
			threadRecepteur.start () ;
			// demande d'affichage de l'éditeur
			// - faite "seulement"/"tardivement" ici pour que tous les objets récupérés du serveur apparaissent bien du premier coup
			
			this.setSize(1300,1000);
			this.setLayout(new BorderLayout());
			this.editeur = new EditeurClient(this);
			this.getContentPane().add(editeur,BorderLayout.CENTER);
			this.setTitle("Paint");
		    this.setLocationRelativeTo(null);
		    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			
			setVisible (true) ;
			
		}

		public RemoteEditeurServeur getServer() {
			return server;
		}

		public EditeurClient getEditeur() {
		return editeur;
	}
}
