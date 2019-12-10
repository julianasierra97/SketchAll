package editeur;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
 
public class ColorChooser extends JFrame {
	private static final long serialVersionUID = 1L;

	PropertyChangeSupport chooserChange = new PropertyChangeSupport(this);
	ColorPane colorP;
	Color oldColor;
	Color newColor;
	JColorChooser colorChooser = new JColorChooser();
	JButton selectButton = new JButton("Select");
	
	public ColorChooser(ColorPane colorP) {
	    this.setTitle("Color chooser");
	    this.setSize(500, 300);
	    this.setLocationRelativeTo(null);
	    this.setVisible(false);
	    
	    Container content = this.getContentPane();
	    content.add(colorChooser, BorderLayout.CENTER);
	    content.add(selectButton, BorderLayout.SOUTH);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener change) {
		chooserChange.addPropertyChangeListener(change);
    }

    public void removePropertyChangeListener(PropertyChangeListener change) {
    	chooserChange.removePropertyChangeListener(change);
    }
	
    public void initialize(ColorPane colorP) {
    	this.colorP = colorP;
    	setVisible(true);
    	
		colorChooser.setColor(colorP.getColor());
			    
	    selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oldColor = newColor;
				newColor = colorChooser.getColor();
				chooserChange.firePropertyChange("color", oldColor, newColor);
			    colorP.setColor(colorChooser.getColor());
			    dispose();
			}
		});
    }
    
	public Color getColor() {
		return newColor;
	}
}