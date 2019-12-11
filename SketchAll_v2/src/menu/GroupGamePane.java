package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class GroupGamePane extends JPanel {
	private static final long serialVersionUID = 1L;

	SideBar pane;
	
	GridBagConstraints gbc;
	List<String> groupList = new ArrayList<String>();
	
	OptionButton playButton = new OptionButton("Play with friends");
	
	public GroupGamePane(SideBar pane) {
		this.pane = pane;
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		setPreferredSize(new Dimension(200,300));
		
		TitledBorder border;
		Border borderLine;
		Color borderColor = new Color(50,50,50);
		Font borderFont = new Font("Comic Sans MS",1,15);
		borderLine = BorderFactory.createLineBorder(borderColor, 2);
		border = BorderFactory.createTitledBorder(borderLine, "Group game");
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
	    
        selectGroup();
        addButtonsToPane();
	}
	
	public void selectGroup() {
		groupList.add("Juli97");
		groupList.add("D_Jaimes");
		groupList.add("Stan317");
		groupList.add("Odd");
		groupList.add("imtSketch");
		groupList.add("IHM29");
		groupList.add("BZHimt");
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;  
        for (String username : groupList) {
			JLabel label = new JLabel(username);
			label.setFont(new Font("Comic Sans MS",1,13));
	        add(label, gbc);
	        gbc.gridy++;
		}
        playButton.setFont(new Font("Comic Sans MS",1,13));
        add(playButton, gbc);   
	}
	
}