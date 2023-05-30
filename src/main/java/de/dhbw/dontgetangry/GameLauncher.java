package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.netty.GameClient;
import de.dhbw.dontgetangry.netty.GameServer;

import javax.swing.*;
import java.awt.*;

public class GameLauncher extends JFrame {

    private final JComboBox<Player> playerComboBox;

    public GameLauncher() {
        setTitle("Player Selection");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Select a player:");
        playerComboBox = new JComboBox<>(Player.values());

        JButton selectButton = new JButton("Select");
        selectButton.addActionListener(e -> {
            Player selectedPlayer = (Player) playerComboBox.getSelectedItem();
            int playerId = selectedPlayer.id;
            Color playerColor = selectedPlayer.color;
            Icon playerIcon = selectedPlayer.icon;

            System.out.println("Selected Player: " + selectedPlayer);
            System.out.println("Player ID: " + playerId);
            System.out.println("Player Color: " + playerColor);
            System.out.println("Player Icon: " + playerIcon);

            // Perform any other logic with the selected player
            startGame(playerId + "");

        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(label);
        panel.add(playerComboBox);
        panel.add(selectButton);
        add(panel);

        setVisible(true);
    }

    public void startGame(String playerName) {
        GameUpdateHandler gameUpdateHandler = new GameUpdateHandler();
        GameServer gameServer = new GameServer(gameUpdateHandler);
        try {
            gameServer.start(5000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GameClient gameClient = new GameClient(playerName);


        EventQueue.invokeLater(() -> {
            try {
                GameWindow gameWindow = new GameWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}