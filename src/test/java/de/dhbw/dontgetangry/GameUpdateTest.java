package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.netty.GameClient;
import de.dhbw.dontgetangry.netty.GameServer;

import java.util.ArrayList;
import java.util.List;

public class GameUpdateTest {

    public static void main(String[] args) throws Exception {
        int port = 5000;
        String serverHost = "localhost";
        GameUpdateHandler gameUpdateHandler = new GameUpdateHandler();
        GameServer server = new GameServer(gameUpdateHandler);

        // Start the server on a new thread
        Thread serverThread = new Thread(() -> {
            try {

                server.start(port);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

        serverThread.sleep(2000); // Wait for the server to start
        // Create and start multiple clients
        List<GameClient> clients = new ArrayList<>();
        clients.add(new GameClient("Alice"));
        clients.add(new GameClient("Bob"));
        clients.add(new GameClient("Charlie"));

        for (GameClient client : clients) {
            Thread clientThread = new Thread(() -> {
                try {
                    client.sendUpdate("Some update", serverHost, port);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            clientThread.start();
        }

        // Perform your operations...

        // Shutdown the clients
        for (GameClient client : clients) {
            client.stop();
        }

        // Shutdown the server
        serverThread.sleep(2000);
        server.stop();
    }
}

