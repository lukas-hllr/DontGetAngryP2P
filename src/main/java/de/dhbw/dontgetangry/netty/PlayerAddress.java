package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.model.Player;

public record PlayerAddress(Player player, String domain, int port) {
}
