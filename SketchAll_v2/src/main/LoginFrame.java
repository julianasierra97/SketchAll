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

public class LoginFrame extends JFrame implements ActionListener{

	   JPanel panel;
	   JLabel user_label; 
	   JLabel password_label;
	   JLabel message;
	   JTextField userName_text;
	   JPasswordField password_text;
	   JButton submit, cancel;
	   Login logDoc;
	public LoginFrame() {
	      user_label = new JLabel();
	      user_label.setText("User Name :");
	      userName_text = new JTextField();
	      password_label = new JLabel();
	      password_label.setText("Password :");
	      password_text = new JPasswordField();
	      
	      submit = new JButton("SUBMIT");

	      panel = new JPanel(new GridLayout(3, 1));
	      panel.add(user_label);
	      panel.add(userName_text);
	      panel.add(password_label);
	      panel.add(password_text);
	      message = new JLabel();
	      panel.add(message);
	      panel.add(submit);

	      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	      // Adding the listeners to components..
	      submit.addActionListener(this);
	      add(panel, BorderLayout.CENTER);
	      setTitle("Login Here !");
	      setSize(250,150);
	      setVisible(true);
	 
  
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String userName = userName_text.getText();
	    String password = password_text.getText();
	    logDoc = new Login();
	    System.out.println(userName);
	    System.out.println(password);
	   if( logDoc.readFile(userName, password)) {
		 dispose();
		 new MainFrameClient();
		
	   }
	   else {
		   JOptionPane.showMessageDialog(null, "The username and password are not correct!", "Login Error", JOptionPane.ERROR_MESSAGE);
	   }
	      
	    
	    logDoc.writteFile(userName, password); 	
	}

	   public static void main(String[] args) {
	      new LoginFrame();
	      
	   }
}
