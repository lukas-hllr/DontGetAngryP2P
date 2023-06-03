package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;

public interface GameConnectionEventListener {

    void onPlayerJoinedByNetwork(Player player);

    void onPlayerMoveByNetwork(Player player, int figure, int position);

    void onDiceRolledByNetwork(Player player, int n);

    void onTurnEndedByNetwork(Player player);
}
