package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RoleInformation extends JPanel {
		
	 
	ImageIcon image;
	public RoleInformation(FrameClient fc) {

			
			//setLayout(new BorderLayout(0, 0));
			fc.setSize(new Dimension(750, 600));
			setSize(new Dimension(750, 600));
			
		setLayout(new BorderLayout());
		
		
			
			
			JLabel word=new JLabel("<html><div style='text-align: center;'><p style='color:red;font-size:40px; margin-left:400px'>" + "hila" + "</p></div></html>");
			Image gameImage=null;
			Image resizedImage=null;
			if( true)
			{
				
			
				try {
					gameImage = ImageIO.read(getClass().getResource("2.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				resizedImage = gameImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH); // Bords lisses
				


				
			}
			else 
			{
				 try {
					gameImage = ImageIO.read(getClass().getResource("2.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 resizedImage = gameImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH); // Bords lisses
				
			}
			
		


		
		}
}



