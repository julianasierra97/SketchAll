package server;

import java.util.TimerTask;

public class TimerTaskServeur extends TimerTask{
	
	private PartieServeur partie;
	
	public TimerTaskServeur(PartieServeur partie) {
		super();
		this.partie = partie;
	}

	@Override
	public void run() {
		for (int i = 70; i >=0; i--) {
			
			
			if(i==65){

			}

			if(i>=10) {
				
				completeTask();

			}
			else {
				
				completeTask();
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

}
