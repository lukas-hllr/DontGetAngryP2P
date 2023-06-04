package de.dhbw.dontgetangry.ui.components;
import de.dhbw.dontgetangry.model.Player;
import de.dhbw.dontgetangry.ui.GameWindow;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class FieldPanel extends JButton {
	
	private Map<Player, List<Integer>> players;
	private final GameWindow window;
	
	public FieldPanel(GameWindow window) {
		super();
		this.window = window;
		init(window);
	}

	private void init(GameWindow window) {
		players = new HashMap<>();
		players.put(Player.BLUE, new ArrayList<>());
		players.put(Player.RED, new ArrayList<>());
		players.put(Player.YELLOW, new ArrayList<>());
		players.put(Player.GREEN, new ArrayList<>());

		this.setSize(64, 64);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setLayout(null);

		this.addActionListener(e -> window.fieldClicked(this));
	}

	public void setPlayer(Player player, int character, boolean isOnField) {
		if (isOnField) {
			players.get(player).add(Integer.valueOf(character));
			Icon i = player.icon;
			this.setIcon(i);
		} else if(players.get(player).contains(character)){
			players.get(player).remove(Integer.valueOf(character));


			if(!players.get(Player.BLUE).isEmpty()){
				this.setIcon(Player.BLUE.icon);
			} else if(!players.get(Player.RED).isEmpty()){
				this.setIcon(Player.RED.icon);
			} else if(!players.get(Player.YELLOW).isEmpty()){
				this.setIcon(Player.YELLOW.icon);
			} else if(!players.get(Player.GREEN).isEmpty()){
				this.setIcon(Player.GREEN.icon);
			} else {
				this.setIcon(null);
			}
		}
	}

	public void highlight(boolean highlight){
		if(highlight){
			this.setBorder(new LineBorder(new Color(255, 87, 51), 3));
		} else {
			this.setBorder(new LineBorder(new Color(0, 0, 0)));
		}
	}

	public int getPlayerOfType(Player player){
		if(players.get(player).size() > 0){
			return players.get(player).get(0);
		}
		return -1;
	}

}
