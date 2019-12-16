package server;

import java.rmi.RemoteException;
import java.util.TimerTask;

public class TimerTaskServeur extends TimerTask{
	
	private PartieServeur partie;
	private int compteur;
	

	public TimerTaskServeur(PartieServeur partie) {
		super();
		this.partie = partie;
		
	}

	@Override
	public void run() {
		while (compteur>0) {
			compteur-=1;
			completeTask();
			
			if(compteur==65){
				for (String player : partie.getGameList()) {
					try {
						partie.getServer().getPlayerList().get(player).startRound();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

			}

			if(compteur ==5) {
				partie.gameEnd();
			}

		}

	}


	private void completeTask() {
		try {
			Thread.sleep(1000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

}
