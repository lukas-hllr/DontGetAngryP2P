package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.model.Player;
import de.dhbw.dontgetangry.netty.GameClient;
import de.dhbw.dontgetangry.netty.GameServer;
import de.dhbw.dontgetangry.ui.GameWindow;
import de.dhbw.dontgetangry.ui.starter.StarterEventListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameLauncher extends JFrame {

    private JTextField domainTextField;
    private JTextField portTextField;

    private JRadioButton serverRadioButton;
    private JRadioButton clientRadioButton;

    private JButton joinStartButton;
    private ActionListener joinStartButtonAL;

    private JComboBox<Player> colorSelector;

    public GameLauncher() {
        this.setBounds(100, 100, 450, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JPanel centerPanel = new JPanel();
        this.getContentPane().add(centerPanel, BorderLayout.CENTER);
        centerPanel.setLayout(new BorderLayout(10, 10));

        JPanel clientServerPanel = new JPanel();
        centerPanel.add(clientServerPanel, BorderLayout.WEST);
        clientServerPanel.setLayout(new BorderLayout(10, 10));

        serverRadioButton = new JRadioButton("Start a new Game!");
        serverRadioButton.setSelected(true);
        serverRadioButton.addActionListener(e -> {
            domainTextField.setEnabled(false);
            joinStartButton.setText("Start a new Game!");
        });
        clientServerPanel.add(serverRadioButton, BorderLayout.NORTH);

        clientRadioButton = new JRadioButton("Join a Game");
        clientRadioButton.addActionListener(e -> {
            domainTextField.setEnabled(true);
            joinStartButton.setText("Join!");
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
        this.getContentPane().add(joinStartPanel, BorderLayout.SOUTH);
        joinStartPanel.setLayout(new BorderLayout(10, 10));

        joinStartButton = new JButton("Start a new Game!");
        joinStartPanel.add(joinStartButton);
        ActionListener joinStartButtonAL = e -> joinStartButtonPressed();
        joinStartButton.addActionListener(joinStartButtonAL);
        this.setVisible(true);
    }

    public void startGame2(Player player) {
        GameUpdateHandler gameUpdateHandler = new GameUpdateHandler();
        GameServer gameServer = new GameServer(gameUpdateHandler);
        try {
            gameServer.start(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameClient gameClient = new GameClient(player);
        try {
            gameClient.sendUpdate("test", "localhost", 5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        EventQueue.invokeLater(() -> {
            try {
                //GameWindow gameWindow = new GameWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void startServer(int port) {
        GameUpdateHandler gameUpdateHandler = new GameUpdateHandler();
        GameServer gameServer = new GameServer(gameUpdateHandler);
        try {
            gameServer.start(port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void startClient(String domain, int port, Player player) {
        GameClient gameClient = new GameClient(player);
        try {
            gameClient.sendUpdate("join", domain, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        EventQueue.invokeLater(() -> {
            try {
                //GameWindow gameWindow = new GameWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void joinStartButtonPressed() {
        if(clientRadioButton.isSelected()){
            String domain = domainTextField.getText();
            int port = Integer.parseInt(portTextField.getText());
            Player player = (Player) colorSelector.getSelectedItem();
            startClient(domain, port, player);
        } else {
            int port = Integer.parseInt(portTextField.getText());
            Player player = (Player) colorSelector.getSelectedItem();
            startServer(port);
            startClient("localhost", port, player);
        }
    }
}