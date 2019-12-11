package menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class OptionButton extends JButton {
	private static final long serialVersionUID = 1L;
	
	String optionName;
	
	public OptionButton(String optionName) {
		this.optionName = optionName;
		
		setOpaque(false);
		setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setFocusPainted(false);
        Border padding = BorderFactory.createEmptyBorder(20, 20, 20, 20);
		setBorder(padding);
		
		setText(optionName);
        setFont(new Font("Comic Sans MS",1,13));        
	}
	
	public String getOption() {
		return optionName;
	}
	
	@Override
    public void paint (Graphics g) {
		int offset = getModel().isPressed() ? 2 : 0;
        Dimension size = getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getBackground().darker());
        g.fillRoundRect(offset + 1, offset + 2, size.width - offset - 1, size.height + offset - 2, 10, 10);
        g.setColor(getBackground());
        g.fillRoundRect(offset, offset, size.width - offset - 3, size.height + offset - 4, 10, 10);
        super.paint(g);
	}
}
