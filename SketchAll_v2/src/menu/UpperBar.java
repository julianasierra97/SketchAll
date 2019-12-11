package menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class UpperBar extends JPanel {
	private static final long serialVersionUID = 1L;
	
	GridBagConstraints gbc;
	List<OptionButton> buttonList = new ArrayList<OptionButton>();
	
	OptionButton logOutButton = new OptionButton("Log out");
	OptionButton profileButton = new OptionButton("Profile");
	OptionButton friendsButton = new OptionButton("Friends");
	OptionButton mailButton = new OptionButton("Mail");
	OptionButton settingsButton = new OptionButton("Settings");
	
	public UpperBar() {		
		setLayout(new GridBagLayout());
		setOpaque(false);
		
	    gbc = new GridBagConstraints();
	    gbc.gridx = 0;
	    gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;	//Comment remplir l'espace disponible
	    gbc.anchor = GridBagConstraints.NORTH;	//Point d'ancrage
	    gbc.insets = new Insets(5, 50, 5, 50);	//Margins & paddings
        gbc.weightx = 1;	//Comment répartir l'espace supplémentaire entre composants
        gbc.weighty = 1;
	    
        addButtonsToList();
        addButtonsToPane();
		
		for (OptionButton button : buttonList) {
			button.setPreferredSize(new Dimension(10, 30));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.out.println(button.getOption());
				}
			});
		}
	}
	
	public void addButtonsToList() {
		buttonList.add(logOutButton);
        buttonList.add(profileButton);
        buttonList.add(friendsButton);
        buttonList.add(mailButton);
        buttonList.add(settingsButton);
	}
	
	public void addButtonsToPane() {
        gbc.gridx = 0;
        gbc.gridy = 0;  
        add(logOutButton, gbc);
        gbc.gridx++;
        add(profileButton, gbc);
        gbc.gridx++;
        add(friendsButton, gbc);
        gbc.gridx++;
        add(mailButton, gbc);     
        gbc.gridx++;
        add(settingsButton, gbc);   
	}
	
}