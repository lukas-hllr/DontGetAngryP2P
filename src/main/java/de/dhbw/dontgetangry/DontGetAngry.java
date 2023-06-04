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

import java.util.*;

public class DontGetAngry implements StarterEventListener, UIEventListener, GameConnectionEventListener {

	private final UserInterface ui;
	private final StarterUserInterface starterUi;
	private final GameConnection connection;
	private final List<Player> players = new ArrayList<>();
	private Map<Player, int[]> player_positions;

	private Player mainPlayer;


	public DontGetAngry(){
		this.ui = new GameWindow(this);
		this.starterUi = new StartWindow(this);
		this.connection = new GameConnectionsMgr(this);
		this.player_positions = new HashMap<>();
	}

	public void start(){
		starterUi.show(true);
	}

	@Override
	public void onRollDiceByUI() {
		int r = new Random().nextInt(6) + 1;
		ui.setDice(3);
		connection.rollDice(r);
	}

	@Override
	public void onSetPositionByUI(int character, boolean forward) {
		int current = player_positions.get(mainPlayer)[character];
		int newPos = 0;
		if(forward && current < 43){
			if(current < 0){
				newPos = 0;
			} else {
				newPos = current + 1;
			}

			ui.setPosition(mainPlayer, character, newPos);
			player_positions.get(mainPlayer)[character] = newPos;
			connection.playerMove(character, newPos);
		} else if(current < 43 && current != 0){
			newPos = current -1;
			ui.setPosition(mainPlayer, character, newPos);
			player_positions.get(mainPlayer)[character] = newPos;
			connection.playerMove(character, newPos);
		}
	}

	@Override
	public void onEndTurnByUI() {
		connection.endTurn();

		int next = mainPlayer.id + 1 < 3 ? mainPlayer.id + 1 : 0;
		while (!players.contains(Player.getPlayerById(next))){
			next++;
			if(next > 3){
				next = 0;
			}
		}
		ui.setTurn(Player.getPlayerById(next));

		checkForWinner();
	}

	private void checkForWinner() {
		for (Player player : players) {

			int sum = Arrays.stream(player_positions.get(player)).reduce(0, Integer::sum);
			System.out.println(sum);
			if(sum >= 166){
				ui.setWinner(player);
				break;
			}
		}
	}

	@Override
	public void onStartNewGameRequestedByUI(int port, Player player) {
		starterUi.awaitGameStart();
		mainPlayer = player;
		players.add(player);
		player_positions.put(player, new int[]{-4, -3, -2, -1});
		connection.start(player, port);
	}

	@Override
	public void onJoinGameRequestedByUI(String domain, int port, Player player) {
		starterUi.awaitGameStart();
		mainPlayer = player;
		players.add(player);
		player_positions.put(player, new int[]{-4, -3, -2, -1});
		connection.start(player, domain, port);
	}

	@Override
	public void onStartGameRequestedByUI() {
		starterUi.show(false);
		connection.startGame();
		ui.startGame(players);

		setInitialTurn();
	}

	private void setInitialTurn() {
		if(players.contains(Player.BLUE)){
			ui.setTurn(Player.BLUE);
		} else if(players.contains(Player.RED)){
			ui.setTurn(Player.RED);
		} else if(players.contains(Player.YELLOW)){
			ui.setTurn(Player.YELLOW);
		} else if(players.contains(Player.GREEN)){
			ui.setTurn(Player.GREEN);
		}
	}

	@Override
	public void onPlayerJoinedByNetwork(Player player) {
		players.add(player);
		player_positions.put(player, new int[]{-4, -3, -2, -1});
		starterUi.setPlayersInQueue(players.size()-1);
	}

	@Override
	public void onPlayerMoveByNetwork(Player player, int figure, int position) {
		player_positions.get(player)[figure] = position;
		ui.setPosition(player, figure, position);
	}

	@Override
	public void onDiceRolledByNetwork(Player player, int n) {
		ui.setDice(n);
	}

	@Override
	public void onTurnEndedByNetwork(Player player) {
		int next = player.id + 1 < 3 ? player.id + 1 : 0;
		while (!players.contains(Player.getPlayerById(next))){
			next++;
			if(next > 3){
				next = 0;
			}
		}
		ui.setTurn(Player.getPlayerById(next));
	}

	@Override
	public void onGameStartedByNetwork() {
		starterUi.show(false);
		ui.startGame(players);
		setInitialTurn();
	}
}
