package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPane extends JPanel implements KeyListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private ArrayList<String> messages;

	
	private JTextArea messageDisplay;
	
	private JTextField textToSend;
	
	private JPanel panelSouth;
	
	public ChatPane () {
		

		setSize(200, 400);
		
		messages= new ArrayList<>();


		
		messageDisplay= new JTextArea();
		messageDisplay.setEditable(false);
		
		textToSend= new JTextField();
		textToSend.addKeyListener(this);
		setBorder( BorderFactory.createTitledBorder("Chat"));
		
		setLayout(new BorderLayout());
		
		setPreferredSize(new Dimension(200,200));
		
		panelSouth= new JPanel();
		panelSouth.setLayout(new BorderLayout());
		
		panelSouth.add(textToSend, BorderLayout.CENTER);
	
		
		add(panelSouth, BorderLayout.SOUTH);
		
		add(messageDisplay, BorderLayout.CENTER);
		
		
		
		add(panelSouth, BorderLayout.SOUTH);

		
		setVisible(true);
		
		
		
	}
	
	
	public void recevoirMessage(String message) {
		boolean alreadyDisplayed=false;
		for (String string : messages) {
			if(message.equals(string)) {
				alreadyDisplayed=true;
			}
		}
		if(!alreadyDisplayed) {
			messages.add(message);
			String text= "";
			for (String string : messages) {
				text+=string+"\n";
			}
			
			messageDisplay.setText(text);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ENTER) {
			messages.add(textToSend.getText());

			String text= "";
			for (String string : messages) {
				text+=string+"\n";
			}
			textToSend.setText("");
			messageDisplay.setText(text);
		
		
		}
	
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	
		
	}
	
	
}
