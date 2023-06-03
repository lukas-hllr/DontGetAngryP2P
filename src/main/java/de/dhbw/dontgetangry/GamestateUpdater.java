package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.model.Player;
import de.dhbw.dontgetangry.netty.GameConnectionEventListener;

public class GamestateUpdater implements GameConnectionEventListener {
    @Override
    public void onPlayerJoined(Player player, String domain, int port) {

    }

    @Override
    public void onPlayerMove(Player player, int figure, int position) {

    }

    @Override
    public void onDiceRolled(Player player, int n) {

    }

    @Override
    public void onTurnEnded(Player player) {

    }
}
