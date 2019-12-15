package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

import javafx.scene.control.ProgressBar;

public class WaitingPanel extends JPanel {
	
	JProgressBar progressBar;
	JPanel panel_1;

	public WaitingPanel(FrameClient fc) {

		setLayout(new BorderLayout(0, 0));
		fc.setSize(new Dimension(1000, 600));
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(2, 1));

		JLabel lblPleaseWaitUntil = new JLabel("Please wait for more players join the game...");
		lblPleaseWaitUntil.setForeground(new Color(71, 174, 243));
		lblPleaseWaitUntil.setFont(new Font("Comic Sans MS", Font.BOLD, 40));

		lblPleaseWaitUntil.setBorder(new EmptyBorder(70,40,40,40));
		panel.add(lblPleaseWaitUntil);

		 panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		progressBar = new JProgressBar();

		progressBar.setForeground(new Color(71, 174, 243));
		progressBar.setBackground(Color.WHITE);
		progressBar.setBorder(new EmptyBorder(200,20,200,20));
		panel_1.add(progressBar);
		progressBar.setMaximum(4);
		progressBar.setValue(1);

		URL url = this.getClass().getResource("spinner.gif");

		ImageIcon icon = new ImageIcon(url);


		JButton btnImage = new JButton();
		btnImage.setBorder(new EmptyBorder(0,0,0,0));
		btnImage.setIcon(icon);
		btnImage.setDisabledIcon(icon);
		btnImage.setEnabled(false);
		panel_1.add(btnImage);
	}
	
	public JProgressBar getProgressBar() {
		return progressBar;
	}

}
