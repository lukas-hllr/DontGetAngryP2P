package de.dhbw.dontgetangry;

import de.dhbw.dontgetangry.model.Player;
import de.dhbw.dontgetangry.ui.GameWindow;
import de.dhbw.dontgetangry.ui.UIEventListener;
import de.dhbw.dontgetangry.ui.UserInterface;
import de.dhbw.dontgetangry.ui.starter.StartWindow;
import de.dhbw.dontgetangry.ui.starter.StarterEventListener;
import de.dhbw.dontgetangry.ui.starter.StarterUserInterface;
import java.util.Arrays;

public class DontGetAngry implements StarterEventListener, UIEventListener {

	private UserInterface ui;
	private StarterUserInterface starterUi;

	public DontGetAngry(){
		this.ui = new GameWindow(this);
		this.starterUi = new StartWindow(this);
	}

	public void start(){
		starterUi.show(true);
	}

	@Override
	public void rollDice() {
		System.out.println("roll dice");
	}

	@Override
	public void setPosition(Player player, int character, boolean forward) {

	}

	@Override
	public void setTurn(Player player) {

	}

	@Override
	public void endTurn() {

	}

	@Override
	public void startServer(int port, Player player) {
		starterUi.awaitGameStart();
		System.out.println("start server");
		startGame();
	}

	@Override
	public void startClient(String domain, int Port, Player player) {
		starterUi.awaitGameStart();
		System.out.println("start client");
		startGame();

	}

	@Override
	public void startGame() {
		starterUi.show(false);
		ui.startGame(Arrays.stream(new Player[]{Player.BLUE, Player.RED}).toList());
	}
}
