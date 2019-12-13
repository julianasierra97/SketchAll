package main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import server.RemoteEditeurServeur;

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	JLabel message;
	JTextField username_text;
	JPasswordField password_text;
	JButton submit, cancel;
	Login logDoc;
	RemoteEditeurServeur server;
	final String clientMachineName;
	final String serverEditorName;
	final String serverMachineName;
	final int serverRMIPort;

	public LoginFrame(final String clientMachineName, final String serverEditorName, final String serverMachineName,
			final int serverRMIPort) {

		this.clientMachineName = clientMachineName;
		this.serverEditorName = serverEditorName;
		this.serverMachineName = serverMachineName;
		this.serverRMIPort = serverRMIPort;
		this.logDoc = new Login();
		
		try {
			// tentative de connexion au serveur distant
			server = (RemoteEditeurServeur) Naming
					.lookup("//" + serverMachineName + ":" + serverRMIPort + "/" + serverEditorName);
			// invocation d'une premi�re m�thode juste pour test
			server.answer("hello from " + getName());

		} catch (Exception e) {
			System.out.println("probleme liaison CentralManager");
			e.printStackTrace();
			System.exit(1);
		}

		JLabel user_label = new JLabel();
		user_label.setFont(new Font("Arial", Font.PLAIN, 40));
		user_label.setText("User Name :");
		username_text = new JTextField();
		username_text.setFont(new Font("Arial", Font.PLAIN, 40));
		JLabel password_label = new JLabel();
		password_label.setFont(new Font("Arial", Font.PLAIN, 40));
		password_label.setText("Password :");
		password_text = new JPasswordField();
		password_text.setFont(new Font("Arial", Font.PLAIN, 40));

		JButton submit = new JButton("SUBMIT");
		submit.setPreferredSize(new Dimension(200, 100));
		submit.setFont(new Font("Arial", Font.PLAIN, 40));
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel userpanel = new JPanel(new GridLayout(1, 2, 10, 20));
		userpanel.setPreferredSize(new Dimension(500, 60));
		JPanel passwordpanel = new JPanel(new GridLayout(1, 2, 10, 20));
		passwordpanel.setPreferredSize(new Dimension(500, 60));
		userpanel.add(user_label);
		userpanel.add(username_text);
		passwordpanel.add(password_label);
		passwordpanel.add(password_text);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Adding the listeners to components..
		submit.addActionListener(new LoginListener(this));
		JPanel loginPane = new JPanel();
		loginPane.setLayout(new BoxLayout(loginPane, BoxLayout.PAGE_AXIS));
		loginPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		loginPane.add(userpanel);
		loginPane.add(Box.createRigidArea(new Dimension(0, 20)));
		loginPane.add(passwordpanel);
		loginPane.add(Box.createRigidArea(new Dimension(0, 20)));
		loginPane.add(submit);
		setTitle("Login Here !");
		setLocationRelativeTo(null);
		setSize(800, 500);
		add(loginPane);
		setVisible(true);

	}

	class LoginListener implements ActionListener {

		private LoginFrame login;

		public LoginListener(LoginFrame login) {
			this.login = login;

		}

		public void actionPerformed(ActionEvent arg0) {
			String username = username_text.getText();
			String password = String.valueOf(password_text.getPassword());
			System.out.println(username);
			try {
				if (server.loginCorrect(username, password)) {
					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "The username and password are not correct!", "Login Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			login.createClient(username);
		}

	}

	public FrameClient createClient(String username) {
		return new FrameClient(clientMachineName,  username, server);
	}
}
