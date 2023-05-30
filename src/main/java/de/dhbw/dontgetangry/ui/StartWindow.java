package de.dhbw.dontgetangry.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import java.awt.Choice;
import javax.swing.JTextField;

public class StartWindow {

	private JFrame frame;
	private JTextField ipTextField;
	private JTextField portTextField;


	/**
	 * Create the application.
	 */
	public StartWindow() {
		initialize();
	}

	public void show(boolean show){
		this.frame.setVisible(show);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		JPanel centerPanel = new JPanel();
		frame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BorderLayout(10, 10));
		
		JPanel clientServerPanel = new JPanel();
		centerPanel.add(clientServerPanel, BorderLayout.WEST);
		clientServerPanel.setLayout(new BorderLayout(10, 10));
		
		JRadioButton serverRadioButton = new JRadioButton("Start a new Game");
		serverRadioButton.setToolTipText("");
		serverRadioButton.setSelected(true);
		clientServerPanel.add(serverRadioButton, BorderLayout.NORTH);
		
		JRadioButton clientRadioButton = new JRadioButton("Join a Game");
		clientServerPanel.add(clientRadioButton, BorderLayout.SOUTH);

		ButtonGroup serverClientGroup = new ButtonGroup();
		serverClientGroup.add(serverRadioButton);
		serverClientGroup.add(clientRadioButton);
		
		JPanel ipPortPanel = new JPanel();
		centerPanel.add(ipPortPanel, BorderLayout.CENTER);
		ipPortPanel.setLayout(new BorderLayout(10, 10));
		
		ipTextField = new JTextField();
		ipTextField.setText("IP-Address");
		ipPortPanel.add(ipTextField, BorderLayout.CENTER);
		ipTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setToolTipText("");
		portTextField.setText("Port");
		ipPortPanel.add(portTextField, BorderLayout.EAST);
		portTextField.setColumns(10);
		
		Choice colorSelector = new Choice();
		centerPanel.add(colorSelector, BorderLayout.SOUTH);
		
		JPanel joinStartPanel = new JPanel();
		frame.getContentPane().add(joinStartPanel, BorderLayout.SOUTH);
		joinStartPanel.setLayout(new BorderLayout(10, 10));
		
		JButton joinStartButton = new JButton("Join!");
		joinStartPanel.add(joinStartButton);
	}

}
