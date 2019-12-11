package server;
import java.rmi.RemoteException ;
import java.rmi.registry.LocateRegistry ;

public class MainEditeurServeur {

	public static void main (String args[]) {
		//String nomMachineRMI = "192.168.43.146" ;
		String nomMachineRMI = "localhost" ; // mettre l'adresse IP de votre serveur ici
		//String nomMachineRMI = "10.29.227.68" ;
		int portRMI = 2010;
		String nomServeur = "EditeurCollaboratif" ;
		int portEmissionUpdates = 4020 ;
		System.out.println ("Création d'un serveur avec les caractéristiques :") ;
		System.out.println ("nom du serveur : " + nomMachineRMI) ;
		System.out.println ("port du serveur : " + portRMI) ;
		System.out.println ("nom du monde partagé : " + nomServeur) ;
		System.out.println ("port de diffusion objets : " + portEmissionUpdates) ;
		System.setProperty ("java.rmi.server.hostname", nomMachineRMI) ;
		try {
			// création d'un registre rmi sur le port rmi choisi, indispensable pour pouvoir attacher ensuite un serveur 
			LocateRegistry.createRegistry (portRMI) ;
			new EditeurServeur (nomServeur, nomMachineRMI, portRMI, portEmissionUpdates) ;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
