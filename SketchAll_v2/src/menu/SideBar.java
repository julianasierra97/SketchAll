package menu;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.FrameClient;

public class SideBar extends JPanel {

	private static final long serialVersionUID = 1L;
	
	GroupGamePane groupGamePane;
	RankedGamePane rankedGamePane = new RankedGamePane(this);
	

	
	public SideBar(FrameClient frame) {		
		
		setLayout(new BorderLayout());
		setOpaque(false);
		Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(padding);
		
		groupGamePane = new GroupGamePane(this, frame);
		
        addButtonsToPane();
	}
	
	

	public void addButtonsToPane() {
        add(groupGamePane, BorderLayout.NORTH);
        add(rankedGamePane, BorderLayout.SOUTH);    
	}

}