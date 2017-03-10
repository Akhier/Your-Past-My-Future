package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;

public class WideSwipeScreen implements Screen {
	protected Creature player;

	public WideSwipeScreen(Creature player) {
		this.player = player;
		player.doAction("should choose a direction to swipe");
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
		int[] x = new int[8], y = new int[8];
		switch(dir) {
		case("N"):
			y[0] = y[2] = y[5] = player.y - 1;
			y[1] = y[3] = y[4] = y[6] = y[7] = player.y - 2;
			x[1] = player.x - 2;
			x[0] = x[3] = player.x - 1;
			x[5] = x[6] = player.x + 1;
			x[7] = player.x + 2;
			break;
		case("S"):
			y[0] = y[2] = y[5] = player.y + 1;
			y[1] = y[3] = y[4] = y[6] = y[7] = player.y + 2;
			x[1] = player.x + 2;
			x[0] = x[3] = player.x + 1;
			x[5] = x[6] = player.x - 1;
			x[7] = player.x - 2;
			break;
		case("E"):
			x[0] = x[2] = x[5] = player.x + 1;
			x[1] = x[3] = x[4] = x[6] = x[7] = player.x + 2;
			y[1] = player.y - 2;
			y[0] = y[3] = player.y - 1;
			y[5] = y[6] = player.y + 1;
			y[7] = player.y + 2;
			break;
		case("W"):
			x[0] = x[2] = x[5] = player.x - 1;
			x[1] = x[3] = x[4] = x[6] = x[7] = player.x - 2;
			y[1] = player.y + 2;
			y[0] = y[3] = player.y + 1;
			y[5] = y[6] = player.y - 1;
			y[7] = player.y - 2;
			break;
		case("NE"):
			y[1] = y[3] = y[4] = player.y - 2;
			y[0] = y[2] = y[5] = player.y - 1;
			x[2] = x[3] = x[6] = player.x + 1;
			x[4] = x[5] = x[7] = player.x + 2;
			break;
		case("NW"):
			y[1] = y[3] = y[4] = player.y + 2;
			y[0] = y[2] = y[5] = player.y + 1;
			x[2] = x[3] = x[6] = player.x - 1;
			x[4] = x[5] = x[7] = player.x - 2;
			break;
		case("SE"):
			y[4] = y[5] = y[7] = player.y + 2;
			y[2] = y[3] = y[6] = player.y + 1;
			x[0] = x[2] = x[5] = player.x + 1;
			x[1] = x[3] = x[4] = player.x + 2;
			break;
		case("SW"):
			y[4] = y[5] = y[7] = player.y - 2;
			y[2] = y[3] = y[6] = player.y - 1;
			x[0] = x[2] = x[5] = player.x - 1;
			x[1] = x[3] = x[4] = player.x - 2;
			break;
		}
		for(int i = 0; i < 8; i++) {
			Creature c = player.creature(x[i], y[i], player.z);
			if(c != null) {
				double modifier = i / 10 + 1;
				player.commonAttack(c, (int)(player.attackValue() * modifier), "swipe the %s for %d damage", c.name());
			}
		}
		player.modifyMana(-4);
	}
}
