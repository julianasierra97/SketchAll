package main;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JLabel user_label; 
	JLabel password_label;
	JLabel message;
	JTextField username_text;
	JPasswordField password_text;
	JButton submit, cancel;
	Login logDoc;
	final String clientMachineName;
	final String serverEditorName;
	final String serverMachineName;
	final int serverRMIPort;
	   
	public LoginFrame(final String clientMachineName, final String serverEditorName, final String serverMachineName, final int serverRMIPort) {
	      
		this.clientMachineName=clientMachineName;
		this.serverEditorName=serverEditorName;
		this.serverMachineName=serverMachineName;
		this.serverRMIPort=serverRMIPort;
		
		user_label = new JLabel();
	    user_label.setText("User Name :");
	    username_text = new JTextField();
	    password_label = new JLabel();
	    password_label.setText("Password :");
	    password_text = new JPasswordField();
	    
	    submit = new JButton("SUBMIT");

	    panel = new JPanel(new GridLayout(3, 1));
	    panel.add(user_label);
	    panel.add(username_text);
	    panel.add(password_label);
	    panel.add(password_text);
	    message = new JLabel();
	    panel.add(message);
	    panel.add(submit);

	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      // Adding the listeners to components..
	    submit.addActionListener(new LoginListener(this));
	    add(panel, BorderLayout.CENTER);
	    setTitle("Login Here !");
	    setSize(250,150);
	    setVisible(true);
	 
	}

	
		class LoginListener implements ActionListener {
		
		private LoginFrame login;
		
		public LoginListener(LoginFrame login) {
			this.login=login;
			
		}
		
		public void actionPerformed(ActionEvent arg0) {
			String username = username_text.getText();
		    String password = String.valueOf(password_text.getPassword());
		    logDoc = new Login();
		    System.out.println(username);
		    if( logDoc.readFile(username, password)) {
			 dispose();
			 
			
		    }
		    else {
			   JOptionPane.showMessageDialog(null, "The username and password are not correct!", "Login Error", JOptionPane.ERROR_MESSAGE);
		    }
		      
		    
		    logDoc.writeFile(username, password); 
		    login.createClient(username);
		}
		
	}
	   
	   public FrameClient createClient(String username) {
		   return new FrameClient(clientMachineName, serverEditorName, serverMachineName, serverRMIPort, username);
	   }
}
