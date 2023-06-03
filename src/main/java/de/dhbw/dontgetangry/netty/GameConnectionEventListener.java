package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;

public interface GameConnectionEventListener {

    void onPlayerJoined(Player player);

    void onPlayerMove(Player player, int figure, int position);

    void onDiceRolled(Player player, int n);

    void onTurnEnded(Player player);
}
