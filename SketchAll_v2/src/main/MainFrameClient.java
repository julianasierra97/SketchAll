package main;

public class MainFrameClient {

	// main permettant de lancer un éditeur local 
		public static void main (String [] args) {
			// le nom de la machine qui héberge le serveur distant
			// String nomMachineServeur = "10.29.227.68" ;
			String serverMachineName = "localhost" ; // mettre l'adresse IP de votre serveur ici
			// le numro de port sur lequel est déclaré le serveur distant
			int serverRMIPort = 2010 ;
			// le nom du serveur distant
			String collaborativeEditorName = "EditeurCollaboratif" ;
			// le nom de l'éditeur local (ne sert pas à grand chose, du moins pour le moment)
			//String nomMachineClient = "10.29.227.68" ;
			String clientMachineName = "localhost" ; // mettre l'adresse IP de votre client ici
			System.out.println ("Connexion à un serveur avec les caractéristiques :") ;
			System.out.println ("machine du client : " + clientMachineName) ;
			System.out.println ("nom du serveur distant : " + serverMachineName) ;
			System.out.println ("port rmi du serveur : " + serverRMIPort) ;
			System.out.println ("nom de l'univers partagé : " + collaborativeEditorName) ;
			// instanciation d'un client déporté qui fera le lien avec le navigateur
			new FrameClient (clientMachineName, collaborativeEditorName, serverMachineName, serverRMIPort, "user2") ;

		}
	
}
