package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;

import java.util.List;
import java.util.StringJoiner;

import static de.dhbw.dontgetangry.netty.GameProtocolKeywords.ConnectionInfos;
import static de.dhbw.dontgetangry.netty.GameProtocolKeywords.PlayerJoined;


public class GameUpdateHandler {

    private final GameConnectionEventListener listener;
    private final GameConnectionsMgr mgr;

    public GameUpdateHandler(GameConnectionsMgr mgr, GameConnectionEventListener listener) {
        this.listener = listener;
        this.mgr = mgr;
    }

    public void handleUpdate(String host, String player, String update) {
        System.out.println(update);
        String[] address = host.split(":");
        address[0] = address[0].substring(1);

        //TODO: Remove
        System.out.println("Player " + player + " sent update: " + update + "\n from domain: " + address[0] + " and port: " + address[1]);

        handleUpdateByKeyword(update, address[0], Integer.parseInt(address[1]));
    }


    private void handleUpdateByKeyword(String update, String host, int port) {
        String[] updateArray = update.split("/");
        String keyword = updateArray[0];
        String[] updateArgs = updateArray[1].split(";");
        Player updateFromPlayer = Player.getPlayerById(Integer.parseInt(updateArgs[0]));

        switch (GameProtocolKeywords.valueOf(keyword)) {
            case JoinRequest -> handleJoinRequest(updateFromPlayer, updateArgs, host, port);
            case ConnectionInfos -> handleConnectionInfos(updateFromPlayer, updateArgs, host, port);
            case PlayerJoined -> {
                mgr.addPlayerAddress(new PlayerAddress(updateFromPlayer, host, port));
                listener.onPlayerJoinedByNetwork(updateFromPlayer);
            }
            case PlayerMove ->
                    listener.onPlayerMoveByNetwork(updateFromPlayer, Integer.parseInt(updateArgs[1]), Integer.parseInt(updateArgs[2]));
            case DiceRolled -> listener.onDiceRolledByNetwork(updateFromPlayer, Integer.parseInt(updateArgs[1]));
            case TurnEnded -> listener.onTurnEndedByNetwork(updateFromPlayer);
            case GameStarted -> listener.onGameStartedByNetwork();
            default -> throw new IllegalStateException("Unexpected value: " + keyword);
        }
    }

    private void handleJoinRequest(Player player, String[] updateArgs, String host, int port){
        StringJoiner addressjoiner = new StringJoiner("+");
        for (PlayerAddress address : mgr.getPlayerAddresses()) {
            addressjoiner.add(address.player().id + "|" + address.domain() + "|" + address.port());
        }
        try {
            mgr.getGameClient().sendUpdate(ConnectionInfos.getKeyword() + "/" + mgr.getPlayer().id + ";" + addressjoiner, host, mgr.port);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleConnectionInfos(Player player, String[] updateArgs, String host, int port){
        String[] addresses = updateArgs[1].split("\\+");
        for (String address : addresses) {
            String[] addressValues = address.split("\\|");
            Player newPlayer = Player.getPlayerById(Integer.parseInt(addressValues[0]));
            String newHost = addressValues[1];
            int newPort = Integer.parseInt(addressValues[2]);

            mgr.addPlayerAddress(new PlayerAddress(newPlayer, newHost, mgr.port));
            listener.onPlayerJoinedByNetwork(newPlayer);

            try {
                mgr.getGameClient().sendUpdate(PlayerJoined.getKeyword() + "/" + newPlayer.id, newHost, mgr.port);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
