package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MiniGameDescription extends JPanel {

	private static final long serialVersionUID = 1L;

	MiniGamePane pane;
	
	int width = 710;
	int height = 350;
	
	Image resizedImage;
	
	public MiniGameDescription(MiniGamePane pane) {
		this.pane = pane;
		setPreferredSize(new Dimension(width,height));
		Border border = BorderFactory.createLineBorder(new Color(50,50,50), 2);
		setBorder(border);
		setOpaque(false);
		setDescription(pane.getGame());
	}
	
	public void setDescription(String gameName) {
		try {
			Image gameImage = ImageIO.read(getClass().getResource(gameName + "Description.png"));
			resizedImage = gameImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Bords lisses
			repaint();
		} catch (Exception ex) {
			System.out.println("Image not found");
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(resizedImage, 0, 0, null);
	}
	
	
}
