package editeur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPane extends JPanel implements ActionListener{

	
	private static final String ENVOYER = "envoyer";

	private ArrayList<String> messages;
	
	private JButton buttonEnvoyer;
	
	private JTextArea messageDisplay;
	
	private JTextField textToSend;
	
	private JPanel panelSouth;
	
	public ChatPane () {
		

		setSize(200, 400);
		
		messages= new ArrayList<>();
		
		buttonEnvoyer= new JButton("Envoyer");
		buttonEnvoyer.setActionCommand(ENVOYER);
		buttonEnvoyer.addActionListener(this);
		buttonEnvoyer.setSize(60, 80);
		
		messageDisplay= new JTextArea();
		
		textToSend= new JTextField();
		setBorder( BorderFactory.createLineBorder(Color.GRAY, 2));
		
		setLayout(new BorderLayout());
		
		setPreferredSize(new Dimension(200,200));
		
		panelSouth= new JPanel();
		panelSouth.setLayout(new BorderLayout());
		
		panelSouth.add(textToSend, BorderLayout.CENTER);
		
		panelSouth.add(buttonEnvoyer, BorderLayout.SOUTH);
		
		add(panelSouth, BorderLayout.SOUTH);
		
		add(messageDisplay, BorderLayout.CENTER);
		
		
		
		add(panelSouth, BorderLayout.SOUTH);
		
//		
//		chronometre= new Chronometre();
//		
//		add(chronometre,BorderLayout.NORTH);
		
		setVisible(true);
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(ENVOYER)) {
			messages.add(textToSend.getText());
		
			String text= "";
			for (String string : messages) {
				text+=string+"\n";
			}
			textToSend.setText("");
			messageDisplay.setText(text);
			repaint();
		}
		
	}
	
	
}
