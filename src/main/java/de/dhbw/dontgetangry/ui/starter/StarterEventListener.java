package de.dhbw.dontgetangry.ui.starter;

import de.dhbw.dontgetangry.model.Player;

public interface StarterEventListener {
    void startServer(int port, Player player);

    void startClient(String domain, int Port, Player player);

    void startGame();
}
