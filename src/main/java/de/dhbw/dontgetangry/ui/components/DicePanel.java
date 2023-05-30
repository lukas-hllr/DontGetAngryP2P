package de.dhbw.dontgetangry.ui.components;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Random;

public class DicePanel extends JPanel {
    private static final int PANEL_SIZE = 64;
    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 6;
    private static final int ROLL_DURATION = 2000; // 2 seconds

    private final Random random;
    private JLabel numberLabel;
    private final Timer rollTimer;
    private long rollStartTime;
    private final boolean useImages;
    private final Icon[] diceImages = new ImageIcon[]{
            new ImageIcon("src/main/resources/dice1.png"),
            new ImageIcon("src/main/resources/dice2.png"),
            new ImageIcon("/src/main/resources/dice3.png"),
            new ImageIcon("src/main/resources/dice4.png"),
            new ImageIcon("src/main/resources/dice5.png"),
            new ImageIcon("src/main/resources/dice6.png")
    };

    public DicePanel(boolean useImages) {
        this.useImages = useImages;
        random = new Random();
        this.setSize(PANEL_SIZE, PANEL_SIZE);
//        this.setBorder(new LineBorder(new Color(0, 0, 0)));
        this.setLayout(new BorderLayout());

        numberLabel = new JLabel();
        numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numberLabel.setVerticalAlignment(SwingConstants.CENTER);
        numberLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(numberLabel, BorderLayout.CENTER);

        rollTimer = new Timer(100, e -> updateRollingNumber());

        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rollDice();
            }
        });

        rollDice();
    }

    private void rollDice() {
        int currentNumber = generateRandomNumber();
        if (useImages) {
            setDiceIcon(currentNumber);
        } else {
            setNumberText(currentNumber);
        }
        rollStartTime = System.currentTimeMillis();
        rollTimer.start();
    }

    private int generateRandomNumber() {
        return random.nextInt(MAX_VALUE - MIN_VALUE + 1) + MIN_VALUE;
    }

    private void setDiceIcon(int number) {
        //first clear out the old image
        if (numberLabel != null) {
            this.remove(numberLabel);
        }
        //set the image of the dice
        Icon icon = diceImages[number - 1];
        numberLabel = new JLabel(icon);
        this.add(numberLabel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void setNumberText(int number) {
        numberLabel.setText(String.valueOf(number));
    }

    private void updateRollingNumber() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - rollStartTime;

        if (elapsedTime >= ROLL_DURATION) {
            rollTimer.stop();
        } else {
            int newNumber = generateRandomNumber();
            if (useImages) {
                setDiceIcon(newNumber);
            } else {
                setNumberText(newNumber);
            }
        }
    }
}
