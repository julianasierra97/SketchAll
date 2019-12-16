package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteUserServeur extends Remote {
	
	public void setSketcher(boolean sketcher, String word) throws RemoteException;
	public void setPoints(int points) throws RemoteException;
	public String getUsername() throws RemoteException ;
	public int getPoints() throws RemoteException ;
	public boolean isSketcher() throws RemoteException;
	public void setInGame(boolean inGame) throws RemoteException;
	public boolean getInGame();

}
