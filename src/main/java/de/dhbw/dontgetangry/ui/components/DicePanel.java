package de.dhbw.dontgetangry.ui.components;

import de.dhbw.dontgetangry.ui.UIEventListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

public class DicePanel extends JButton {
    private static final int PANEL_SIZE = 64;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 6;
    private static final int ROLL_DURATION = 2000; // 2 seconds

    private final UIEventListener listener;
    private JLabel numberLabel;
    private Timer rollTimer;
    private long rollStartTime;
    private final Icon[] diceImages = new ImageIcon[]{
            new ImageIcon("src/main/resources/dice1.png"),
            new ImageIcon("src/main/resources/dice2.png"),
            new ImageIcon("/src/main/resources/dice3.png"),
            new ImageIcon("src/main/resources/dice4.png"),
            new ImageIcon("src/main/resources/dice5.png"),
            new ImageIcon("src/main/resources/dice6.png")
    };

    public DicePanel(UIEventListener listener) {
        this.listener = listener;
        this.setSize(PANEL_SIZE, PANEL_SIZE);
//        this.setBorder(new LineBorder(new Color(0, 0, 0)));
        this.setLayout(new BorderLayout());

        this.addActionListener(e -> listener.onRollDiceByUI());

        this.setIcon(diceImages[5]);
    }

    public void rollDice(int r) {
        this.setIcon(null);
        this.setIcon(diceImages[r-1]);
        //rollTimer = new Timer(100, e -> updateRollingNumber(r));

        //rollStartTime = System.currentTimeMillis();
        //rollTimer.start();
    }

    private void setDiceIcon(int number) {
        //set the image of the dice
        Icon icon = diceImages[number - 1];
        this.setIcon(icon);
    }

    private void updateRollingNumber(int r) {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - rollStartTime;

        if (elapsedTime >= ROLL_DURATION) {
            rollTimer.stop();
            System.out.println(r);
            setDiceIcon(r);
        } else {
            int newNumber = new Random().nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
            setDiceIcon(newNumber);
        }
    }
}
