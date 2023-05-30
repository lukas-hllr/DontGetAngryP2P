package de.dhbw.dontgetangry.ui;

import de.dhbw.dontgetangry.model.Player;

public interface UIEventListener {

    void startServer(int port, Player player);

    void startClient(String domain, int Port, Player player);

    void checkColorAvailability();

    void rollDice();

    void setPosition(Player player, int character, boolean forward);

    void endTurn();

}
