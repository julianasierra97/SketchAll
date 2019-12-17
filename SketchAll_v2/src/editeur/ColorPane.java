package editeur;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;


public class ColorPane extends JPanel {
	private static final long serialVersionUID = 1L;

	EditeurClient editor;
	
	PropertyChangeSupport colorChange = new PropertyChangeSupport(this);
	GridBagConstraints gbc;
	List<JButton> buttonList = new ArrayList<JButton>();
	Color oldColor;
	Color newColor = Color.black ;
	
	JButton cranberryButton = new JButton();
	JButton maroonButton = new JButton();
	JButton redButton = new JButton();
	JButton strawberryButton = new JButton();
	JButton coralButton = new JButton();
	
	JButton chocolateButton = new JButton();
	JButton orangeButton = new JButton();
	JButton carrotButton = new JButton();
	JButton yellowButton = new JButton();
	JButton sunshineButton = new JButton();

	JButton oliveButton = new JButton();
	JButton forestButton = new JButton();
	JButton greenButton = new JButton();
	JButton limeButton = new JButton();
	JButton mintButton = new JButton();
	
	JButton navyButton = new JButton();
	JButton royalButton = new JButton();
	JButton blueButton = new JButton();
	JButton aquaButton = new JButton();
	JButton skyButton = new JButton();
	
	JButton grapeButton = new JButton();
	JButton purpleButton = new JButton();
	JButton raspberryButton = new JButton();
	JButton magentaButton = new JButton();
	JButton lilaButton = new JButton();
	
	JButton blackButton = new JButton();
	JButton charcoalButton = new JButton();
	JButton greyButton = new JButton();
	JButton platinumButton = new JButton();
	JButton whiteButton = new JButton();
		
	public ColorPane(EditeurClient editor, ToolPane tool) {
		this.editor = editor;
		setLayout(new GridBagLayout());
		setBackground(new Color(240,240,240));

		TitledBorder border;
		Border borderLine;
		Color borderColor = new Color(160,160,160);
		Font borderFont = new Font("Arial",1,17);
		borderLine = BorderFactory.createLineBorder(borderColor);
		border = BorderFactory.createTitledBorder(borderLine, "Colors");
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
        colorButtons();
        addButtonsToPane();
			
		for (JButton button : buttonList) {
			button.setPreferredSize(new Dimension(30, 10));
			button.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					oldColor = newColor;
					newColor = button.getBackground();
					colorChanged();
			        button.setBorder(BorderFactory.createBevelBorder(1, new Color(105,105,105), new Color(120,120,120)));
					for (JButton other : buttonList) {
						if (other != button) {
							other.setBorder(BorderFactory.createBevelBorder(0, new Color(150,150,150), new Color(150,150,150)));
						}
					}
				}
			});
		}
		
	}
	
    public void addPropertyChangeListener(PropertyChangeListener change) {
        colorChange.addPropertyChangeListener(change);
    }

    public void removePropertyChangeListener(PropertyChangeListener change) {
    	colorChange.removePropertyChangeListener(change);
    }
    
    public Color getColor() {
    	return newColor;
    }
    
    public void setColor(Color newColor) {
    	this.newColor = newColor;
    }
    
    public void colorChanged() {
        editor.getZoneDeDessin().setForeground(newColor);
        colorChange.firePropertyChange("color", oldColor, newColor);
    }
	
	public void addButtonsToList() {
		buttonList.add(cranberryButton);
        buttonList.add(maroonButton);
        buttonList.add(redButton);
        buttonList.add(strawberryButton);
        buttonList.add(coralButton);
        buttonList.add(chocolateButton);
        buttonList.add(orangeButton);
        buttonList.add(carrotButton);
        buttonList.add(sunshineButton);
        buttonList.add(yellowButton);
        buttonList.add(forestButton);
        buttonList.add(oliveButton);
        buttonList.add(greenButton);
        buttonList.add(limeButton);
        buttonList.add(mintButton);
        buttonList.add(navyButton);
        buttonList.add(royalButton);
        buttonList.add(blueButton);
        buttonList.add(aquaButton);
        buttonList.add(skyButton);
        buttonList.add(grapeButton);
        buttonList.add(purpleButton);
        buttonList.add(raspberryButton);
        buttonList.add(magentaButton);
        buttonList.add(lilaButton);
        buttonList.add(blackButton);
        buttonList.add(charcoalButton);
        buttonList.add(greyButton);
        buttonList.add(platinumButton);
        buttonList.add(whiteButton);
	}
	
	public void colorButtons() {
		cranberryButton.setBackground(new Color(110,0,30));
        maroonButton.setBackground(new Color(170,30,0));
        redButton.setBackground(new Color(250,0,0));
        strawberryButton.setBackground(new Color(255,80,95));
        coralButton.setBackground(new Color(250,125,100));
        
        chocolateButton.setBackground(new Color(140,65,15));
        orangeButton.setBackground(new Color(245,95,0));
        carrotButton.setBackground(new Color(250,155,90));
        sunshineButton.setBackground(new Color(255,190,20));
        yellowButton.setBackground(new Color(255,255,100));
        
        forestButton.setBackground(new Color(30,95,20));
        oliveButton.setBackground(new Color(110,130,30));
        greenButton.setBackground(new Color(0,220,0));
        limeButton.setBackground(new Color(185,245,45));
        mintButton.setBackground(new Color(185,255,185));
        
        navyButton.setBackground(new Color(0,0,130));
        royalButton.setBackground(new Color(50,80,235));
        blueButton.setBackground(new Color(0,150,255));
        aquaButton.setBackground(new Color(0,220,220));
        skyButton.setBackground(new Color(135,240,255));
        
        grapeButton.setBackground(new Color(90,0,140));
        purpleButton.setBackground(new Color(135,20,200));
        raspberryButton.setBackground(new Color(185,0,200));
        magentaButton.setBackground(new Color(200,0,155));
        lilaButton.setBackground(new Color(230,150,245));
        
        blackButton.setBackground(new Color(0,0,0));
        charcoalButton.setBackground(new Color(60,60,60));
        greyButton.setBackground(new Color(120,120,120));
        platinumButton.setBackground(new Color(185,185,185));
        whiteButton.setBackground(new Color(255,255,255));
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;  
        add(cranberryButton, gbc);
        gbc.gridx++;
        add(maroonButton, gbc);
        gbc.gridx++;
        add(redButton, gbc);
        gbc.gridx++;
        add(strawberryButton, gbc);
        gbc.gridx++;
        add(greyButton, gbc);
        gbc.gridx++;      
        add(chocolateButton, gbc);
        gbc.gridx++;
        add(orangeButton, gbc);
        gbc.gridx++;
        add(carrotButton, gbc);
        gbc.gridx++;
        add(sunshineButton, gbc);
        gbc.gridx++;
        add(yellowButton, gbc);
        gbc.gridx++;     
        add(forestButton, gbc);
        gbc.gridx++;
        add(oliveButton, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;  
        add(greenButton, gbc);
        gbc.gridx++;
        add(limeButton, gbc);
        gbc.gridx++;
        add(whiteButton, gbc);
        gbc.gridx++;       
        add(navyButton, gbc);
        gbc.gridx++;
        add(royalButton, gbc);
        gbc.gridx++;
        add(blueButton, gbc);
        gbc.gridx++;
        add(aquaButton, gbc);
        gbc.gridx++;
        add(skyButton, gbc);
        gbc.gridx++;        
        add(grapeButton, gbc);
        gbc.gridx++;
        add(purpleButton, gbc);
        gbc.gridx++;
        add(blackButton, gbc);
        gbc.gridx++;
        add(magentaButton, gbc);
        
        
//        gbc.gridx = 0;
//        gbc.gridy = 3; 
//        add(lilaButton, gbc);
//        gbc.gridx++;       
//        add(raspberryButton, gbc);
//        gbc.gridx++;
//        add(charcoalButton, gbc);
//        gbc.gridx++;
//        add(coralButton, gbc);
//        gbc.gridx++;
//        add(platinumButton, gbc);
//        gbc.gridx++;
//        add(mintButton, gbc);
//        gbc.gridx++;
	}
	
}
