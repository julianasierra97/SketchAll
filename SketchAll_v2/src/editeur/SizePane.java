package editeur;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class SizePane extends JPanel {
	private static final long serialVersionUID = 1L;

	EditeurClient editor;
	
	PropertyChangeSupport sizeChange = new PropertyChangeSupport(this);
	GridBagConstraints gbc;
	List<SizeButton> buttonList = new ArrayList<SizeButton>();
	int oldSize;
	int newSize = 7 ;
	
	ButtonGroup sizeGroup = new ButtonGroup();
	SizeButton smallButton = new SizeButton("Small", 6);
	SizeButton mediumButton = new SizeButton("Medium", 9);
	SizeButton bigButton = new SizeButton("Big", 12);
	
	public SizePane(EditeurClient editor, ToolPane tool) {
		this.editor = editor;
		setLayout(new GridBagLayout());
		setBackground(new Color(240,240,240));

		TitledBorder border;
		Border borderLine;
		Color borderColor = new Color(160,160,160);
		Font borderFont = new Font("Arial",1,17);
		borderLine = BorderFactory.createLineBorder(borderColor);
		border = BorderFactory.createTitledBorder(borderLine, "Sizes");
		border.setTitleFont(borderFont);
		border.setTitleJustification(TitledBorder.LEFT);
		border.setTitleColor(borderColor);
		setBorder(border);
		
	    gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;	//Comment remplir l'espace disponible
	    gbc.anchor = GridBagConstraints.NORTH;	//Point d'ancrage
	    gbc.insets = new Insets(5, 5, 5, 5);	//Margins & paddings
        gbc.weightx = 1;	//Comment répartir l'espace supplémentaire entre composants
        gbc.weighty = 1;
	    
        addButtonsToList();
        addButtonsToPane();
			
		for (SizeButton button : buttonList) {
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oldSize = newSize;
					newSize = button.getRadius();
					sizeChanged();
				}
			});
		}
	}
	
    public void addPropertyChangeListener(PropertyChangeListener change) {
        sizeChange.addPropertyChangeListener(change);
    }

    public void removePropertyChangeListener(PropertyChangeListener change) {
    	sizeChange.removePropertyChangeListener(change);
    }
    
    public int getRadius() {
    	return newSize;
    }
    
    public void setSize(int newSize) {
    	this.newSize = newSize;
    }
    
    public void sizeChanged() {
        editor.getZoneDeDessin().setRadius(newSize);
        sizeChange.firePropertyChange("size", oldSize, newSize);
    }
	
	public void addButtonsToList() {
		sizeGroup.add(smallButton);
		sizeGroup.add(mediumButton);
		sizeGroup.add(bigButton);
		buttonList.add(smallButton);
        buttonList.add(mediumButton);
        buttonList.add(bigButton);
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;  
        add(smallButton, gbc);
        gbc.gridx++;
        add(mediumButton, gbc);
        gbc.gridx++;
        add(bigButton, gbc);
	}
	
	class SizeButton extends JRadioButton {
		private static final long serialVersionUID = 1L;
		int size;
		
		public SizeButton (String name, int size) {
			this.setText(name);
			this.size = size;
		}
		
		public int getRadius() {
			return size;
		}
	}
	
}
