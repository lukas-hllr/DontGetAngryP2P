package de.dhbw.dontgetangry;

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
            int playerPort = selectedPlayer.getPort();

            System.out.println("Selected Player: " + selectedPlayer);
            System.out.println("Player ID: " + playerId);
            System.out.println("Player Color: " + playerColor);
            System.out.println("Player Icon: " + playerIcon);
            System.out.println("Player Port: " + playerPort);

            // Perform any other logic with the selected player
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(label);
        panel.add(playerComboBox);
        panel.add(selectButton);
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameLauncher::new);
    }
}