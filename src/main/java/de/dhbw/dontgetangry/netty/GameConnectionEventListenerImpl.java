package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.GamestateUpdater;
import de.dhbw.dontgetangry.model.Player;

public class GameConnectionEventListenerImpl implements GameConnectionEventListener {

    private final GameConnectionsMgr connectionsMgr;

    private final GamestateUpdater gameUpdater = new GamestateUpdater();

    public GameConnectionEventListenerImpl(GameConnectionsMgr connectionsMgr) {
        this.connectionsMgr = connectionsMgr;
    }


    @Override
    public void onPlayerJoined(Player player, String domain, int port) {
        this.connectionsMgr.addPlayerAddress(new PlayerAddress(player, domain, port));
        gameUpdater.onPlayerJoined(player, domain, port);
    }

    @Override
    public void onPlayerMove(Player player, int figure, int position) {
        gameUpdater.onPlayerMove(player, figure, position);
    }

    @Override
    public void onDiceRolled(Player player, int n) {
        gameUpdater.onDiceRolled(player, n);
    }

    @Override
    public void onTurnEnded(Player player) {
        gameUpdater.onTurnEnded(player);
    }
}
