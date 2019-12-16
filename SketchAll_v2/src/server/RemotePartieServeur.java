package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemotePartieServeur extends Remote {
	
	public void addPlayer(String username) throws RemoteException ;
	public boolean isComplete() throws RemoteException ;
	public void startGame() throws RemoteException ;
	public void newRound() throws RemoteException ;
	
}