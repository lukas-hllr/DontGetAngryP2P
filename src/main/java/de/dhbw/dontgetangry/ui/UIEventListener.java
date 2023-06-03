package de.dhbw.dontgetangry.ui;

import de.dhbw.dontgetangry.model.Player;

public interface UIEventListener {

    void rollDice();

    void setPosition(Player player, int character, boolean forward);

    void setTurn(Player player);

    void endTurn();

}
