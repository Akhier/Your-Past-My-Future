package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;

public class HelpScreen implements Screen{
	protected Creature player;
	private String[] abilities;

	public HelpScreen(Creature player) {
		this.player = player;
		abilities = new String[4];
		if(player.pClass() == 0) {
			abilities[0] = "Lunge";
			abilities[1] = "Wide Swipe";
			abilities[2] = "Dragon Rampages";
		} else if (player.pClass() == 1) {
			abilities[0] = "Phase Strike";
			abilities[1] = "Deadly Strike";
			abilities[2] = "Tiger Arrogantly Slaughters";
		} else if (player.pClass() == 2) {
			abilities[0] = "Spark";
			abilities[1] = "Fireball";
			abilities[2] = "Pheonix Burst Beam";
		}
		switch(player.previousClass) {
		case(0):
			abilities[3] = "Lunge";
			break;
		case(1):
			abilities[3] = "Phase Strike";
			break;
		case(2):
			abilities[3] = "Spark";
			break;
		case(3):
			abilities[3] = "Wide Swipe";
			break;
		case(4):
			abilities[3] = "Deadly Strike";
			break;
		case(5):
			abilities[3] = "Fireball";
			break;
		case(6):
			abilities[3] = "Dragon Rampages";
			break;
		case(7):
			abilities[3] = "Tiger Arrogantly Slaughters";
			break;
		case(8):
			abilities[3] = "Pheonix Burst Beam";
			break;
		}
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("roguelike help", 1);
		terminal.write("Descend the Caves Of Slight Danger, find the lost Teddy Bear, and return to", 1, 3);
		terminal.write("the surface to win. Use what you find to avoid dying.", 1, 4);
		int y = 6;
		terminal.write("use arrow keys or the numpad to move", 2, y++);
		terminal.write("[?] for help", 2, y++);
		terminal.write("[;] to look around", 2, y++);
		terminal.write("[esc] to quit", 2, y++);
		terminal.write("[f] for " + abilities[0], 2, y++);
		if(player.z >= 1) {
			terminal.write("[d] for " + abilities[1], 2, y++);
		}
		if(player.z >= 2) {
			terminal.write("[s] for " + abilities[2], 2, y++);
		}
		if(abilities[3] != null) {
			terminal.write("[a] for your hereditary " + abilities[3], 2, y++);
		}
		terminal.writeCenter("-- press any key to continue --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}
}
