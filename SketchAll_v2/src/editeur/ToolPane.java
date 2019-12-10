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
	ShapePane shape;
	ColorPane color;
	SizePane size;
	
	public ToolPane(EditeurClient editor) {
		setLayout(new GridBagLayout());
		setBackground(new Color(240,240,240));
		
		Color innerBorder = null;
		Color innerShadow = null;
		Color outerBorder = null;
		Color outerShadow = new Color(200,200,200);
	    Border borderBis = BorderFactory.createBevelBorder(0, innerBorder, outerBorder, innerShadow, outerShadow);
		setBorder(borderBis);
		
		size = new SizePane(editor, this);
		shape = new ShapePane(editor, this);
		color = new ColorPane(editor, this);
		
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.weighty = 0.33;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(4, 4, 4, 4);		//Top, left, bottom, right
		
        add(size, gbc);
        size.setPreferredSize(new Dimension(50,90));
        
		gbc.gridx++;
        add(shape, gbc);
        shape.setPreferredSize(new Dimension(50,90));
		
        gbc.gridx++;
        add(color, gbc);
        color.setPreferredSize(new Dimension(200,90));
	}
	
	public ShapePane getShapePane() {
		return shape;
	}
	
}
