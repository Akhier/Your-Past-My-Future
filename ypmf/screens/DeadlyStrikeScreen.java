package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;

public class DeadlyStrikeScreen implements Screen {
	protected Creature player;

	public DeadlyStrikeScreen(Creature player) {
		this.player = player;
		player.doAction("should choose a direction for your deadly strike");
	}

	@Override
	public void displayOutput(AsciiPanel terminal) { }

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case(KeyEvent.VK_UP):
		case(KeyEvent.VK_NUMPAD8):
			attackTiles("N");
			break;
		case(KeyEvent.VK_DOWN):
		case(KeyEvent.VK_NUMPAD2):
			attackTiles("S");
			break;
		case(KeyEvent.VK_RIGHT):
		case(KeyEvent.VK_NUMPAD6):
			attackTiles("E");
			break;
		case(KeyEvent.VK_LEFT):
		case(KeyEvent.VK_NUMPAD4):
			attackTiles("W");
			break;
		case(KeyEvent.VK_NUMPAD9):
			attackTiles("NE");
			break;
		case(KeyEvent.VK_NUMPAD7):
			attackTiles("NW");
			break;
		case(KeyEvent.VK_NUMPAD3):
			attackTiles("SE");
			break;
		case(KeyEvent.VK_NUMPAD1):
			attackTiles("SW");
			break;
		}
		return null;
	}

	private void attackTiles(String dir) {
		int x = player.x, y = player.y;
		if(dir.contains("N")) {
			y--;
		} else if(dir.contains("S")) {
			y++;
		}
		if(dir.contains("E")) {
			x++;
		} else if(dir.contains("W")) {
			x--;
		}
		Creature c = player.creature(x, y, player.z);
		if(c != null) {
			player.commonAttack(c, (int)(player.attackValue() * 5), "strike %s on a weakpoint dealing %d damage", c.name());
		}
		player.modifyMana(-6);
	}
}
