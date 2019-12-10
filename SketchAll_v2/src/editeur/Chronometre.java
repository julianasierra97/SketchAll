package editeur;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class Chronometre extends JPanel {
	   private JProgressBar progressBar;

	    
	    private JLabel countLabel;
	
	public Chronometre(){
	      progressBar = new JProgressBar(0, 60);
	      progressBar.setValue(60);
	      
	      setLayout(new BorderLayout());
	      
	      add(progressBar, BorderLayout.CENTER);
	      countLabel= new JLabel();
	 
	      countLabel.setFont(countLabel.getFont().deriveFont(30.0f));
	      
	      add(countLabel, BorderLayout.EAST);
	      
	     MyTimerTask tt= new MyTimerTask(progressBar,countLabel);
	     Timer timer = new Timer(true);
	     timer.scheduleAtFixedRate(tt, 0, 10*1000);

	
	      
	    
	}
	
	
	

	
	
	
	
}
