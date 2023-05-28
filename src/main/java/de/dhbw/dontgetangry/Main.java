package de.dhbw.dontgetangry;

import java.awt.*;

public class Main {

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GameWindow gameWindow = new GameWindow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
