package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;

public interface GameConnection {

    void start(Player player, String domain, int port);
    void start(Player player, int port);
    void playerMove(int figure, int position);
    void rollDice(int n);
    void endTurn();
}
