package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatPane extends JPanel implements KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> messages;

	private JTextArea messageDisplay;

	private JTextField textToSend;

	private JPanel panelSouth;

	private FrameClient frame;

	public ChatPane(FrameClient frame) {

		this.frame = frame;

		setSize(200, 400);

		messages = new ArrayList<>();

		messageDisplay = new JTextArea();
		messageDisplay.setEditable(false);

		textToSend = new JTextField();
		textToSend.addKeyListener(this);
		setBorder(BorderFactory.createTitledBorder("Chat"));

		setLayout(new BorderLayout());

		setPreferredSize(new Dimension(200, 200));

		panelSouth = new JPanel();
		panelSouth.setLayout(new BorderLayout());

		if (!frame.getPlayer().isSketcher()) {
			panelSouth.add(textToSend, BorderLayout.CENTER);
		} else {
			System.out.println("A word has to be displayed !");
		}

		add(panelSouth, BorderLayout.SOUTH);

		add(messageDisplay, BorderLayout.CENTER);

		add(panelSouth, BorderLayout.SOUTH);

		setVisible(true);

	}

	public synchronized void receiveMessage(String username, String message) {

		messages.add(username + ": " + message);
		String text = "";
		for (String string : messages) {
			text += string + "\n";
		}

		messageDisplay.setText(text);
	}

	public synchronized void sendMessage(String message) {
		try {
			this.frame.getServer().sendMessage(this.frame.getUsername(), message);
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
