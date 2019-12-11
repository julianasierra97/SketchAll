package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class PlayersPane extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private HashMap<String,PlayerPane> playersList;
	
	public PlayersPane() {
		playersList = new HashMap<String,PlayerPane>();
		this.setPreferredSize(new Dimension(250,1000));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10,10,10,10));
		this.setBackground(Color.lightGray);
	}
	
	public void addPlayer(Player player) {
		PlayerPane playerPane = new PlayerPane(player);
		playersList.put(player.getUsername(), playerPane);
		playerPane.setPaneColor(Color.lightGray);
		this.add(playerPane);
		
	}
	
	
	
	public void setPoints(String username, int points) {
		playersList.get(username).getPlayerPoints().setText(String.valueOf(points));
	}
	
	private class PlayerPane extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextArea playerName;
		private JTextArea playerPoints;
		public PlayerPane(Player player) {
			setLayout(new GridLayout(1,2,10,10));
			playerName = new JTextArea();
			playerPoints = new JTextArea();
			playerName.setEditable(false);
			playerName.setText(player.getUsername());
			playerPoints.setEditable(false);
			playerPoints.setText(String.valueOf(player.getPoints()));
			playerName.setFont(new Font("Arial",Font.PLAIN, 24));
			playerPoints.setFont(new Font("Arial",Font.PLAIN, 36));
			add(playerName);
			add(playerPoints);
		}
		
		public JTextArea getPlayerName() {
			return this.playerName;
		}
		
		public JTextArea getPlayerPoints() {
			return this.playerPoints;
		}
		
		public void setPaneColor(Color c) {
		
		setBackground(c);
		getPlayerName().setBackground(c);
		getPlayerPoints().setBackground(c);
		}
	}
	
}
