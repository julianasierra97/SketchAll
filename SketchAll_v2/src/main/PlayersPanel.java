package main;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class PlayersPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private FrameClient frame;
	private ArrayList<JTextArea> playersList;
	
	public PlayersPanel(FrameClient frame) {
		this.frame=frame;
		playersList = new ArrayList<JTextArea>();
	}
	
	public void addPlayer(Player player) {
		
	}
	
}
