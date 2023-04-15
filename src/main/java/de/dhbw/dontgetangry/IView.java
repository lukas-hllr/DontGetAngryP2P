package main.java.de.dhbw.dontgetangry;

public interface IView {

	/**
	 * 
	 * @param player player
	 * @param character character-id from 0-3
	 * @param position absolute position of player from -4 to 43 while -1 to -4 is out; 0 - 39 is board; 40 - 43 is home
	 */
	public void setPosition(Player player, int character, int position);
	
	public void setTurn(Player player);
	
	public void setDice(int r);
}
