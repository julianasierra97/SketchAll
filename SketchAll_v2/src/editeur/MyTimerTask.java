package editeur;

import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

public class MyTimerTask extends TimerTask {

	
	private JProgressBar label; 
	private JLabel secondsLabel; 
	public  MyTimerTask(JProgressBar label, JLabel secondsLabel) {
	super();
	
	this.label=label;
	this.secondsLabel=secondsLabel;
	}
	
	@Override
	public void run() {
	for (int i = 60; i >0; i--) {
		if(i>=10) {
		label.setValue(i);
		secondsLabel.setText("00:"+i+"");
		completeTask();
		
		}
		else {
			label.setValue(i);
			secondsLabel.setText("00:0"+i+"");
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
