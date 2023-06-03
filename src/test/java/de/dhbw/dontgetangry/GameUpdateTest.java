package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.model.Player;
import de.dhbw.dontgetangry.netty.GameClient;
import de.dhbw.dontgetangry.netty.GameServer;
import de.dhbw.dontgetangry.netty.*;

import java.util.ArrayList;
import java.util.List;

import static de.dhbw.dontgetangry.netty.GameProtocolKeywords.DiceRolled;

public class GameUpdateTest {

    public static void main(String[] args) throws Exception {
        int port = 5000;
        String serverHost = "localhost";
        GameConnectionsMgr gameConnectionsMgr = new GameConnectionsMgr();


        // only for testing (to use .sleep()) not needed in real game
        Thread serverThread = new Thread(() -> {
            try {

                gameConnectionsMgr.start(Player.getPlayerById(0), port);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        serverThread.sleep(2000); // Wait for the server to start
        // Create and start multiple clients
        List<GameClient> clients = new ArrayList<>();
        clients.add(new GameClient(Player.RED));
        clients.add(new GameClient(Player.BLUE));
        clients.add(new GameClient(Player.GREEN));

        for (GameClient client : clients) {
            Thread clientThread = new Thread(() -> {
                try {
                    client.sendUpdate(DiceRolled.getKeyword() + "/" + client.getPlayer() + ";" + 6, serverHost, port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            clientThread.start();
        }

        // Shutdown the clients
        for (GameClient client : clients) {
            client.stop();
        }

        // Shutdown the server
        serverThread.sleep(2000);
        gameConnectionsMgr.getGameServer().stop();
    }
}

