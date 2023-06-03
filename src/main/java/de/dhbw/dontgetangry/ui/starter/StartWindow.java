package de.dhbw.dontgetangry.ui.starter;

import de.dhbw.dontgetangry.model.Player;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

public class StartWindow implements StarterUserInterface{

	private JFrame frame;
	private JTextField domainTextField;
	private JTextField portTextField;

	private JRadioButton serverRadioButton;
	private JRadioButton clientRadioButton;

	private JButton joinStartButton;
	private ActionListener joinStartButtonAL;

	private StarterEventListener listener;

	private JComboBox<Player> colorSelector;

	/**
	 * Create the application.
	 */
	public StartWindow(StarterEventListener listener) {
		this.listener = listener;
		initialize();
	}

	@Override
	public void error(String message) {
		JOptionPane.showMessageDialog(frame,
				message,
				"Error",
				JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void awaitGameStart() {
		this.domainTextField.setEnabled(false);
		this.portTextField.setEnabled(false);
		this.clientRadioButton.setEnabled(false);
		this.serverRadioButton.setEnabled(false);
		this.colorSelector.setEnabled(false);
		if(clientRadioButton.isSelected()){
			this.joinStartButton.setEnabled(false);
			this.joinStartButton.setText("The Host will start the game soon.");
		} else {
			this.joinStartButton.setEnabled(false);
			this.joinStartButton.setText("Wait for others to join.");
		}
	}

	@Override
	public void setPlayersInQueue(int i) {
		if(i > 0){
			this.joinStartButton.setEnabled(true);
			this.joinStartButton.setText(i + " Player joined! Start game?");
		} else {
			this.joinStartButton.setEnabled(false);
			this.joinStartButton.setText("Wait for others to join.");
		}
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
		
		serverRadioButton = new JRadioButton("Start a new Game!");
		serverRadioButton.setSelected(true);
		serverRadioButton.addActionListener(e -> {
			domainTextField.setEnabled(false);
			joinStartButton.setText("Start a new Game!");
			joinStartButton.removeActionListener(joinStartButtonAL);
			joinStartButtonAL = ev -> startNewGameButtonPressed();
			joinStartButton.addActionListener(joinStartButtonAL);
		});
		clientServerPanel.add(serverRadioButton, BorderLayout.NORTH);
		
		clientRadioButton = new JRadioButton("Join a Game");
		clientRadioButton.addActionListener(e -> {
			domainTextField.setEnabled(true);
			joinStartButton.setText("Join!");
			joinStartButton.removeActionListener(joinStartButtonAL);
			joinStartButtonAL = ev -> joinGameButtonPressed();
			joinStartButton.addActionListener(joinStartButtonAL);
		});
		clientServerPanel.add(clientRadioButton, BorderLayout.SOUTH);

		ButtonGroup serverClientGroup = new ButtonGroup();
		serverClientGroup.add(serverRadioButton);
		serverClientGroup.add(clientRadioButton);
		
		JPanel ipPortPanel = new JPanel();
		centerPanel.add(ipPortPanel, BorderLayout.CENTER);
		ipPortPanel.setLayout(new BorderLayout(10, 10));
		
		domainTextField = new JTextField();
		domainTextField.setText("IP-Address");
		domainTextField.setEnabled(false);
		ipPortPanel.add(domainTextField, BorderLayout.CENTER);
		domainTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setToolTipText("");
		portTextField.setText("Port");
		ipPortPanel.add(portTextField, BorderLayout.EAST);
		portTextField.setColumns(10);

		colorSelector = new JComboBox<>(Player.values());
		centerPanel.add(colorSelector, BorderLayout.SOUTH);
		
		JPanel joinStartPanel = new JPanel();
		frame.getContentPane().add(joinStartPanel, BorderLayout.SOUTH);
		joinStartPanel.setLayout(new BorderLayout(10, 10));
		
		joinStartButton = new JButton("Start a new Game!");
		joinStartPanel.add(joinStartButton);
		joinStartButtonAL = ev -> startNewGameButtonPressed();
		joinStartButton.addActionListener(joinStartButtonAL);
	}

	private void joinGameButtonPressed() {
		String domain = domainTextField.getText();
		int port = Integer.parseInt(portTextField.getText());
		Player player = (Player) colorSelector.getSelectedItem();
		listener.onJoinGameRequestedByUI(domain, port, player);
	}

	private void startNewGameButtonPressed() {
		int port = Integer.parseInt(portTextField.getText());
		Player player = (Player) colorSelector.getSelectedItem();
		joinStartButton.removeActionListener(joinStartButtonAL);
		joinStartButtonAL = e -> startGameButtonPressed();
		joinStartButton.addActionListener(joinStartButtonAL);
		listener.onStartNewGameRequestedByUI(port, player);
	}

	private void startGameButtonPressed() {
		listener.onStartGameRequestedByUI();
	}

}
