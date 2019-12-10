package editeur;

import java.awt.Image;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ShapeButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	String shape;
	
	public ShapeButton(String shape) {
		this.shape = shape;
	
		try {
			setToolTipText(shape);
	        Image shapeImage = ImageIO.read(getClass().getResource(shape + ".png"));
	        Image resizedImage = shapeImage.getScaledInstance(40,40, Image.SCALE_SMOOTH);	//Bords lisses
	        setIcon(new ImageIcon(resizedImage));
	      } catch (Exception ex) {setEnabled(false);}
	}
	
	public String getShape() {
		return shape;
	}

}
