package editeur;


import java.awt.BorderLayout;
import java.util.Timer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class Chronometre extends JPanel {
	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JProgressBar progressBar;

	    
	    private JLabel countLabel;
	
	public Chronometre(EditeurClient editeur){
	      progressBar = new JProgressBar(0, 60);
	      progressBar.setValue(60);
	      
	      setLayout(new BorderLayout());
	      
	      add(progressBar, BorderLayout.CENTER);
	      countLabel= new JLabel();
	 
	      countLabel.setFont(countLabel.getFont().deriveFont(30.0f));
	      
	      add(countLabel, BorderLayout.EAST);
	      
	     MyTimerTask tt= new MyTimerTask(progressBar,countLabel, editeur);
	     Timer timer = new Timer(true);
	     timer.scheduleAtFixedRate(tt, 0, 10*1000);

	
	      
	    
	}
	
	
	

	
	
	
	
}
