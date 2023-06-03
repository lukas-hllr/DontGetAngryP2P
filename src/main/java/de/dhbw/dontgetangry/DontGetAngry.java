package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.model.Player;
import de.dhbw.dontgetangry.netty.GameConnection;
import de.dhbw.dontgetangry.netty.GameConnectionEventListener;
import de.dhbw.dontgetangry.netty.GameConnectionsMgr;
import de.dhbw.dontgetangry.ui.GameWindow;
import de.dhbw.dontgetangry.ui.UIEventListener;
import de.dhbw.dontgetangry.ui.UserInterface;
import de.dhbw.dontgetangry.ui.starter.StartWindow;
import de.dhbw.dontgetangry.ui.starter.StarterEventListener;
import de.dhbw.dontgetangry.ui.starter.StarterUserInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DontGetAngry implements StarterEventListener, UIEventListener, GameConnectionEventListener {

	private final UserInterface ui;
	private final StarterUserInterface starterUi;
	private final GameConnection connection;


	private final List<Player> players = new ArrayList<>();


	public DontGetAngry(){
		this.ui = new GameWindow(this);
		this.starterUi = new StartWindow(this);
		this.connection = new GameConnectionsMgr(this);
	}

	public void start(){
		starterUi.show(true);
	}

	@Override
	public void onRollDiceByUI() {
		System.out.println("roll dice");
	}

	@Override
	public void onSetPositionByUI(int character, boolean forward) {

	}

	@Override
	public void onEndTurnByUI() {

	}

	@Override
	public void onStartNewGameRequestedByUI(int port, Player player) {
		System.out.println("start server");

		starterUi.awaitGameStart();
		players.add(player);
		connection.start(player, port);
	}

	@Override
	public void onJoinGameRequestedByUI(String domain, int port, Player player) {
		System.out.println("start client");

		starterUi.awaitGameStart();
		players.add(player);
		connection.start(player, domain, port);
	}

	@Override
	public void onStartGameRequestedByUI() {
		starterUi.show(false);
		ui.startGame(Arrays.stream(new Player[]{Player.BLUE, Player.RED}).toList());
	}

	@Override
	public void onPlayerJoinedByNetwork(Player player) {
		players.add(player);
		starterUi.setPlayersInQueue(players.size()-1);
	}

	@Override
	public void onPlayerMoveByNetwork(Player player, int figure, int position) {

	}

	@Override
	public void onDiceRolledByNetwork(Player player, int n) {

	}

	@Override
	public void onTurnEndedByNetwork(Player player) {

	}

	@Override
	public void onGameStartedByNetwork() {

	}
}
