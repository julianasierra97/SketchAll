package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class PlayersPane extends JPanel{

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
		
		private static final long serialVersionUID = 1L;
		private GridBagConstraints gbc;
		private JTextArea playerName;
		private JTextArea playerPoints;
		
		public PlayerPane(Player player) {
			setLayout(new GridBagLayout());
			gbc = new GridBagConstraints();
		    gbc.gridx = 0;
		    gbc.gridy = 0;
	        gbc.fill = GridBagConstraints.BOTH;	//Comment remplir l'espace disponible
		    gbc.anchor = GridBagConstraints.NORTH;	//Point d'ancrage
		    gbc.insets = new Insets(0, 15, 80, 5);	//Margins & paddings
	        gbc.weightx = 1;	//Comment répartir l'espace supplémentaire entre composants
	        gbc.weighty = 20;
			
			playerName = new JTextArea();
			playerName.setEditable(false);
			playerName.setText(player.getUsername());
			playerName.setFont(new Font("Arial",Font.PLAIN, 24));
			
			JButton pencilIcon = new JButton();
			pencilIcon.setPreferredSize(new Dimension(10, 10));
			pencilIcon.setOpaque(false);
			pencilIcon.setBackground(Color.WHITE);
			pencilIcon.setFocusPainted(false);
	        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
	        pencilIcon.setBorder(padding);
			if (player.isSketcher()) {
				try {
					Image image = ImageIO.read(getClass().getResource("pencil.png"));
					Image resizedImage = image.getScaledInstance(30, 30, Image.SCALE_SMOOTH); // Bords lisses
					pencilIcon.setIcon(new ImageIcon(resizedImage));
				} catch (Exception ex) {
					System.out.println("Image not found");
				}
			}
			
			playerPoints = new JTextArea();
			playerPoints.setEditable(false);
			playerPoints.setText(String.valueOf(player.getPoints()));
			playerPoints.setFont(new Font("Arial",Font.PLAIN, 36));
			
			
	        add(playerName, gbc);
	        gbc.gridx++;
	        add(pencilIcon, gbc);
	        gbc.gridx++;
	        add(playerPoints, gbc);
	        gbc.gridy++;
	        gbc.gridx = 0;
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
