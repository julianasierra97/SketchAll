package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MiniGameBar extends JPanel {
	private static final long serialVersionUID = 1L;

	MiniGamePane pane;
	
	GridBagConstraints gbc;
	List<GameButton> buttonList = new ArrayList<GameButton>();
	
	GameButton game1Button = new GameButton("Game1");
	GameButton game2Button = new GameButton("Game2");
	GameButton game3Button = new GameButton("Game3");
	GameButton game4Button = new GameButton("Game4");
	
	public MiniGameBar(MiniGamePane pane) {
		this.pane = pane;
		
		setLayout(new GridBagLayout());
		setOpaque(false);

		Border border = BorderFactory.createLineBorder(new Color(50,50,50), 2);
		setBorder(border);
		
	    gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;	//Comment remplir l'espace disponible
	    gbc.anchor = GridBagConstraints.NORTH;	//Point d'ancrage
	    gbc.insets = new Insets(10,10,10,10);	//Margins & paddings
        gbc.weightx = 1;	//Comment répartir l'espace supplémentaire entre composants
        gbc.weighty = 1;
	    
        addButtonsToList();
        addButtonsToPane();
		
		for (GameButton button : buttonList) {
			button.setPreferredSize(new Dimension(30, 90));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					pane.setGame(button.getGame());
					button.setBorder(BorderFactory.createLineBorder(new Color(50,50,50), 3));
					for (JButton other : buttonList) {
						if (other != button) {
							other.setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
						}
					}
				}
			});
		}
	}
	
	public void addButtonsToList() {
		buttonList.add(game1Button);
        buttonList.add(game2Button);
        buttonList.add(game3Button);
        buttonList.add(game4Button);
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;  
        add(game1Button, gbc);
        gbc.gridx++;
        add(game2Button, gbc);
        gbc.gridx++;
        add(game3Button, gbc);
        gbc.gridx++;
        add(game4Button, gbc);     
	}
	
	class GameButton extends JButton {
		private static final long serialVersionUID = 1L;
		
		String gameName;
		
		public GameButton(String gameName) {
			this.gameName = gameName;
			
			setBorder(BorderFactory.createLineBorder(new Color(100,100,100), 1));
			setToolTipText(gameName);
			try {
				Image gameImage = ImageIO.read(getClass().getResource(gameName + ".png"));
				Image resizedImage = gameImage.getScaledInstance(155, 90, Image.SCALE_SMOOTH); // Bords lisses
				setIcon(new ImageIcon(resizedImage));
			} catch (Exception ex) {
				System.out.println("Image not found");;
			}
		}
		
		public String getGame() {
			return gameName;
		}

	}
	
}