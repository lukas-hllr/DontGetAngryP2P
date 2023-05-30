package de.dhbw.dontgetangry.ui;

import de.dhbw.dontgetangry.model.Player;

public interface UserInterface {

	/**
	 * 
	 * @param player player
	 * @param character character-id from 0-3
	 * @param position absolute position of player from -4 to 43 while -1 to -4 is out; 0 - 39 is board; 40 - 43 is home
	 */
	void setPosition(Player player, int character, int position);
	
	void setTurn(Player player);
	
	void setDice(int r);

	void setWinner(Player player);

	void startGame();

	void error(String message);
}
