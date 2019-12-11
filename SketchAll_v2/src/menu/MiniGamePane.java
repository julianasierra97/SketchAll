package menu;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.FrameClient;

public class MiniGamePane extends JPanel {

	private static final long serialVersionUID = 1L;
	FrameClient frame;
	
	String gameName = "Game1";
	MiniGameDescription gameDescription = new MiniGameDescription(this);
	
	public MiniGamePane () {

		Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(padding);
		setLayout(new BorderLayout(10, 10));
		setOpaque(false);
		setPreferredSize(new Dimension(750, 300));

		add(gameDescription, BorderLayout.CENTER);
		add(new MiniGameBar(this), BorderLayout.SOUTH);
	}

	public String getGame() {
		return gameName;
	}
	
	public void setGame(String selectedGame) {
		gameName = selectedGame;
		gameDescription.setDescription(gameName);
	}

}
