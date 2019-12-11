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
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class RankedGamePane extends JPanel {
	private static final long serialVersionUID = 1L;

	SideBar pane;
	
	GridBagConstraints gbc;
	List<OptionButton> buttonList = new ArrayList<OptionButton>();
	
	OptionButton playButton = new OptionButton("PLAY !");
	OptionButton rankingButton = new OptionButton("Classement");
	
	public RankedGamePane(SideBar pane) {
		this.pane = pane;
		
		setLayout(new GridBagLayout());
		setOpaque(false);
		setPreferredSize(new Dimension(200,160));

		TitledBorder border;
		Border borderLine;
		Color borderColor = new Color(50,50,50);
		Font borderFont = new Font("Comic Sans MS",1,15);
		borderLine = BorderFactory.createLineBorder(borderColor, 2);
		border = BorderFactory.createTitledBorder(borderLine, "Ranked game");
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
	    
        addButtonsToPane();
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;  
        playButton.setFont(new Font("Comic Sans MS",1,13));
        add(playButton, gbc);
        gbc.gridy++;
        rankingButton.setFont(new Font("Comic Sans MS",1,13));
        add(rankingButton, gbc);   
	}
	
}