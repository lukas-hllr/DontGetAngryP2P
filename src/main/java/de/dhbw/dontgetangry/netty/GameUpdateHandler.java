package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;


public class GameUpdateHandler {

    private final GameConnectionEventListener listener;

    public GameUpdateHandler(GameConnectionEventListener listener) {
        this.listener = listener;
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
            case StartConnection -> listener.onPlayerJoined(updateFromPlayer, host, port);
            case PlayerMove ->
                    listener.onPlayerMove(updateFromPlayer, Integer.parseInt(updateArgs[1]), Integer.parseInt(updateArgs[2]));
            case DiceRolled -> listener.onDiceRolled(updateFromPlayer, Integer.parseInt(updateArgs[1]));
            case TurnEnded -> listener.onTurnEnded(updateFromPlayer);
            default -> throw new IllegalStateException("Unexpected value: " + keyword);
        }
    }
}
