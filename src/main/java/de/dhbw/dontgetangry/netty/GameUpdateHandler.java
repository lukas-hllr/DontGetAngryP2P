package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;


public class GameUpdateHandler {

    private final GameConnectionEventListener listener;
    private final GameConnectionsMgr mgr;

    public GameUpdateHandler(GameConnectionsMgr mgr, GameConnectionEventListener listener) {
        this.listener = listener;
        this.mgr = mgr;
    }

    public void handleUpdate(String host, String player, String update) {
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
            case StartConnection -> {
                mgr.addPlayerAddress(new PlayerAddress(updateFromPlayer, host, port));
                listener.onPlayerJoinedByNetwork(updateFromPlayer);
            }
            case PlayerMove ->
                    listener.onPlayerMoveByNetwork(updateFromPlayer, Integer.parseInt(updateArgs[1]), Integer.parseInt(updateArgs[2]));
            case DiceRolled -> listener.onDiceRolledByNetwork(updateFromPlayer, Integer.parseInt(updateArgs[1]));
            case TurnEnded -> listener.onTurnEndedByNetwork(updateFromPlayer);
            default -> throw new IllegalStateException("Unexpected value: " + keyword);
        }
    }
}
