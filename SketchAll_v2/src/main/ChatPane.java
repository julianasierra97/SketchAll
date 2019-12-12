package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPane extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private ArrayList<String> messages;
	private JTextArea messageDisplay;
	private JTextField textToSend;
	private String currentWord;
	private JLabel wordLabel;
	private JPanel panelSouth;
	private FrameClient frame;

	public ChatPane(FrameClient frame) {

		this.frame = frame;

		setSize(200, 400);		
		setBorder(BorderFactory.createTitledBorder("Tchat"));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200, 200));
		
		messages = new ArrayList<>();

		messageDisplay = new JTextArea();
		messageDisplay.setEditable(false);

		currentWord = frame.nextWord();

		panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());
		System.out.println(frame.getPlayer().isSketcher());
		if (!frame.getPlayer().isSketcher()) {
			textToSend = new JTextField();
			textToSend.addKeyListener(this);
			panelSouth.add(textToSend, BorderLayout.CENTER);
		} else {
			wordLabel = new JLabel(currentWord);
			panelSouth.add(wordLabel, BorderLayout.CENTER);
		}
		System.out.println(currentWord);

		add(panelSouth, BorderLayout.SOUTH);

		add(messageDisplay, BorderLayout.CENTER);

		add(panelSouth, BorderLayout.SOUTH);

		setVisible(true);

	}

	public synchronized void receiveMessage(String username, String message) {

		messages.add(username + " : " + message);
		String text = "";
		for (String string : messages) {
			text += string + "\n";
		}

		messageDisplay.setText(text);
	}

	public synchronized void sendMessage(String message) {
		try {
			this.frame.getServer().sendMessage(this.frame.getUsername(), message);
			//On ne tient pas compte de la casse ni des espaces
			if (message.toLowerCase().trim().contains(currentWord.toLowerCase().trim())) {
				System.out.println("YOU WON !!!!!!!!!!!!");
				frame.setGuesserPoints(10);
				frame.setSketcherPoints(5);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			sendMessage(textToSend.getText());
			textToSend.setText("");
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
