package editeur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class ShapePane extends JPanel {
	private static final long serialVersionUID = 1L;
	
	EditeurClient editor;
	PropertyChangeSupport shapeChange = new PropertyChangeSupport(this); 
	GridBagConstraints gbc;
	List<ShapeButton> buttonList = new ArrayList<ShapeButton>();
	String oldShape;
	String newShape;
	
	ShapeButton rectButton = new ShapeButton("Rectangle");
	ShapeButton ellipseButton = new ShapeButton("Ellipse");
	ShapeButton drawingButton = new ShapeButton("Drawing");
	
	public ShapePane(EditeurClient editor, ToolPane tool) {
		this.editor = editor;
		
		setLayout(new GridBagLayout());
		setBackground(new Color(240,240,240));

		TitledBorder border;
		Border borderLine;
		Color borderColor = new Color(160,160,160);
		Font borderFont = new Font("Arial",1,17);
		borderLine = BorderFactory.createLineBorder(borderColor);
		border = BorderFactory.createTitledBorder(borderLine, "Shapes");
		border.setTitleFont(borderFont);
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitleColor(borderColor);
		setBorder(border);
		
	    //setBorder(new CompoundBorder(new TitledBorder("Shapes"), new EmptyBorder(8, 0, 0, 0)));
	    gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
        //gbc.gridwidth = GridBagConstraints.REMAINDER;	//Nbr de cases disponibles
        gbc.fill = GridBagConstraints.BOTH;	//Comment remplir l'espace disponible
	    gbc.anchor = GridBagConstraints.NORTH;	//Point d'ancrage
	    gbc.insets = new Insets(5, 5, 5, 5);	//Margins & paddings
        gbc.weightx = 4;	//Comment répartir l'espace supplémentaire entre composants
        gbc.weighty = 4;
	    
        addButtonsToList();
        addButtonsToPane();
        
		for (ShapeButton button : buttonList) {
			button.setPreferredSize(new Dimension(30, 10));
			button.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oldShape = newShape;
					newShape = button.getShape();
					editor.getZoneDeDessin().setShape(newShape);
					shapeChange.firePropertyChange("shape", oldShape, newShape);
			        button.setBorder(BorderFactory.createBevelBorder(1, new Color(105,105,105), new Color(120,120,120)));
					
					for (ShapeButton other : buttonList) {
						if (other != button) {
							other.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
						}
					}
				}
			});
		}
	}
	
    public void addPropertyChangeListener(PropertyChangeListener change) {
        shapeChange.addPropertyChangeListener(change);
    }

    public void removePropertyChangeListener(PropertyChangeListener change) {
    	shapeChange.removePropertyChangeListener(change);
    }
	
	public void addButtonsToList() {
		buttonList.add(rectButton);
        buttonList.add(ellipseButton);
        buttonList.add(drawingButton);
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(rectButton, gbc);
        gbc.gridx++;
        add(ellipseButton, gbc);
        gbc.gridx++;
        add(drawingButton, gbc);
	}
	
}