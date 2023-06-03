package de.dhbw.dontgetangry.ui.starter;

import de.dhbw.dontgetangry.model.Player;

public interface StarterUserInterface {
    void error(String message);

    void awaitGameStart();

    void setPlayersInQueue(int i);

    void show(boolean show);
}
