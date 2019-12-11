package menu;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class SideBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	GroupGamePane groupGamePane = new GroupGamePane(this);
	RankedGamePane rankedGamePane = new RankedGamePane(this);
	
	public SideBar() {		
		setLayout(new BorderLayout());
		setOpaque(false);
		Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(padding);
		
        addButtonsToPane();
	}
	
	public void addButtonsToPane() {
        add(groupGamePane, BorderLayout.NORTH);
        add(rankedGamePane, BorderLayout.SOUTH);    
	}

}