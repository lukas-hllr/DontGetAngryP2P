package de.dhbw.dontgetangry.ui.starter;

import de.dhbw.dontgetangry.model.Player;

public interface StarterEventListener {
    void onStartNewGameRequestedByUI(int port, Player player);

    void onJoinGameRequestedByUI(String domain, int port, Player player);

    void onStartGameRequestedByUI();
}
