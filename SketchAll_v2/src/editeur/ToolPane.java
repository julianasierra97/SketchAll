package editeur;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ToolPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	GridBagConstraints gbc;
	SizePane sizePane;
	ColorPane color;
	
	public ToolPane(EditeurClient editor) {
		setLayout(new GridBagLayout());
		setBackground(new Color(240,240,240));
		
		Color innerBorder = null;
		Color innerShadow = null;
		Color outerBorder = null;
		Color outerShadow = new Color(200,200,200);
	    Border borderBis = BorderFactory.createBevelBorder(0, innerBorder, outerBorder, innerShadow, outerShadow);
		setBorder(borderBis);
		
		sizePane = new SizePane(editor, this);
		color = new ColorPane(editor, this);
		
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.33;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);		//Top, left, bottom, right
		
		gbc.gridy++;
        add(sizePane, gbc);
        sizePane.setPreferredSize(new Dimension(400,500));
		
        gbc.gridy++;
        add(color, gbc);
        color.setPreferredSize(new Dimension(400,600));
	}
	
	public SizePane getSizePane() {
		return sizePane;
	}
	
}
