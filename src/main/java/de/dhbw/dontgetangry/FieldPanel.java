package de.dhbw.dontgetangry;
import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class FieldPanel extends JPanel {
	
	private Map<Player, Map<Integer, JLabel>> players;
	
	public FieldPanel() {
		super();
		
		players = new HashMap<Player, Map<Integer,JLabel>>();
		players.put(Player.BLUE, new HashMap<Integer, JLabel>());
		players.put(Player.RED, new HashMap<Integer, JLabel>());
		players.put(Player.YELLOW, new HashMap<Integer, JLabel>());
		players.put(Player.GREEN, new HashMap<Integer, JLabel>());
		
		this.setSize(64, 64);
		this.setBorder(new LineBorder(new Color(0, 0, 0)));
		this.setLayout(null);
		
	}
	
	public void setPlayer(Player player, int character, boolean isOnField) {
		if (isOnField) {
			Icon i = player.icon;
			JLabel l = new JLabel(i);
			l.setBounds(0, 0, 64, 64);
			if(players.get(player).containsKey(character)) {
				this.remove(players.get(player).get(character));
			}
			players.get(player).put(character, l);
			this.add(l);
		} else if(players.get(player).get(character) != null){
			this.remove(players.get(player).get(character));
			players.get(player).remove(character);
		}
	}

}
