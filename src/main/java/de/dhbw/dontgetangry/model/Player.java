package de.dhbw.dontgetangry.model;

import de.dhbw.dontgetangry.ui.GameWindow;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public enum Player {
    BLUE(0, new Color(33, 155, 211), GameWindow.getImageIcon("blue64.png")),
    RED(1, new Color(226, 9, 74), GameWindow.getImageIcon("red64.png")),
    YELLOW(2, new Color(254, 204, 0), GameWindow.getImageIcon("yellow64.png")),
    GREEN(3, new Color(136, 186, 20), GameWindow.getImageIcon("green64.png"));

    public final int id;
    public final Color color;
    public final Icon icon;

    private Player(int id, Color color, Icon icon) {
        this.id = id;
        this.color = color;
        this.icon = icon;
    }

    public static Player getPlayerById(int id) {
        for (Player player : Player.values()) {
            if (player.id == id) {
                return player;
            }
        }
        return null;
    }
}
