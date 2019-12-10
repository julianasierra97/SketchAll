package server ;

import java.awt.Color;
import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException ;
import java.util.ArrayList;

import main.FrameClient;

// interface qui décrit les services offerts par un proxy d'éditeur(côté serveur) :
// - quand un client demandera à accéder à distance à un tel éditeur, il récupérera un proxy de cet éditeur
// - il sera possible d'invoquer des méthodes sur ce proxy
// - les méthodes seront en fait exécutées côté serveur, sur le référent 

public interface RemoteEditeurServeur extends Remote {

   int getPortEmission (InetAddress clientAdress) throws RemoteException ;
   void answer (String question) throws RemoteException ;
   int getRMIPort () throws RemoteException ;
   RemoteDessinServeur addDessin (int x, int y, int w, int h, Color color) throws RemoteException ;
   ArrayList <RemoteDessinServeur> getDessinsPartages () throws RemoteException ;
   RemoteDessinServeur getDessin (String name) throws RemoteException ;
   void addPlayer(String username)throws RemoteException;

}
