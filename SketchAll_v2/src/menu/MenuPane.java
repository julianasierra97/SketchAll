package menu;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import main.FrameClient;

public class MenuPane extends JPanel {

	private static final long serialVersionUID = 1L;
	FrameClient frame;
	MiniGamePane gamePane;
	UpperBar upperBar;
	SideBar sideBar;
	
	public MenuPane (FrameClient frame) {
			
			this.frame = frame;
			setLayout(new BorderLayout());
			
			gamePane = new MiniGamePane ();
			add(gamePane, BorderLayout.WEST);
			
			upperBar = new UpperBar ();
			add(upperBar, BorderLayout.NORTH);

			sideBar = new SideBar ();
			add(sideBar, BorderLayout.EAST);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			Image gameImage = ImageIO.read(getClass().getResource("Background.png"));
			Image resizedImage = gameImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH); // Bords lisses
			g.drawImage(resizedImage, 0, 0, null);
		} catch (Exception ex) {
			System.out.println("Image not found");
		}
	}
	
}
