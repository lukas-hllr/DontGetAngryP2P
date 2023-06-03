package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static de.dhbw.dontgetangry.netty.GameProtocolKeywords.*;

public class GameConnectionsMgr implements GameConnection {

    Player player;
    private GameClient gameClient;
    private GameServer gameServer;
    private final List<PlayerAddress> playerAddresses = new ArrayList<>();
    private final GameConnectionEventListener listener;


    public GameConnectionsMgr(GameConnectionEventListener listener){
        this.listener = listener;
    }

    /**
     * Starts connection to join a game
     */
    @Override
    public void start(Player player, String domain, int port) {
        createServerAndClient(player, port);

        try {
            gameClient.sendUpdate(PlayerJoined.getKeyword() + "/" + this.player.id, domain, port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts a Connection for the first player
     */
    @Override
    public void start(Player player, int port) {
        createServerAndClient(player, port);
    }

    private void createServerAndClient(Player player, int port) {
        this.player = player;
        GameUpdateHandler gameUpdateHandler = new GameUpdateHandler(this, listener);
        gameServer = new GameServer(gameUpdateHandler);
        gameClient = new GameClient(player);

        Thread serverThread = new Thread(() -> {
            try {
                gameServer.start(port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.start();
    }

    @Override
    public void playerMove(int figure, int position) {
        try {
            for (PlayerAddress playerAddress : playerAddresses) {
                gameClient.sendUpdate(PlayerMove.getKeyword() + "/" + this.player.id + ";" + figure + ";" + position, playerAddress.domain(), playerAddress.port());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void rollDice(int n) {
        try {
            for (PlayerAddress playerAddress : playerAddresses) {
                gameClient.sendUpdate(DiceRolled.getKeyword() + "/" + this.player.id + ";" + n, playerAddress.domain(), playerAddress.port());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endTurn() {
        try {
            for (PlayerAddress playerAddress : playerAddresses) {
                gameClient.sendUpdate(TurnEnded.getKeyword() + "/" + this.player.id, playerAddress.domain(), playerAddress.port());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void startGame() {
        try {
            for (PlayerAddress playerAddress : playerAddresses) {
                gameClient.sendUpdate(GameStarted.getKeyword() + "/" + this.player.id, playerAddress.domain(), playerAddress.port());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addPlayerAddress(PlayerAddress playerAddress) {
        playerAddresses.add(playerAddress);
    }

    public Player getPlayer() {
        return player;
    }

    public void stop() throws InterruptedException {
        gameClient.stop();
        gameServer.stop();
    }

    public GameClient getGameClient() {
        return gameClient;
    }

    public GameServer getGameServer() {
        return gameServer;
    }

    public List<PlayerAddress> getPlayerAddresses() {
        return playerAddresses;
    }
}
