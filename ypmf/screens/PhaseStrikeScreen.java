package ypmf.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.World;

public class PhaseStrikeScreen implements Screen {
	protected Creature player;
	protected World world;

	public PhaseStrikeScreen(Creature player, World world) {
		this.player = player;
		this.world = world;
		player.doAction("need to choose a direction to phasestrike");
	}

	@Override
	public void displayOutput(AsciiPanel terminal) { }

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case(KeyEvent.VK_UP):
		case(KeyEvent.VK_NUMPAD8):
			attackThrough("N");
			break;
		case(KeyEvent.VK_DOWN):
		case(KeyEvent.VK_NUMPAD2):
			attackThrough("S");
			break;
		case(KeyEvent.VK_RIGHT):
		case(KeyEvent.VK_NUMPAD6):
			attackThrough("E");
			break;
		case(KeyEvent.VK_LEFT):
		case(KeyEvent.VK_NUMPAD4):
			attackThrough("W");
			break;
		case(KeyEvent.VK_NUMPAD9):
			attackThrough("NE");
			break;
		case(KeyEvent.VK_NUMPAD7):
			attackThrough("NW");
			break;
		case(KeyEvent.VK_NUMPAD3):
			attackThrough("SE");
			break;
		case(KeyEvent.VK_NUMPAD1):
			attackThrough("SW");
			break;
		}
		return null;
	}

	private void attackThrough(String dir) {
		int x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		if(dir.contains("N")) {
			y2 = --y1;
			y2--;
		} else if(dir.contains("S")) {
			y2 = ++y1;
			y2++;
		}
		if(dir.contains("E")) {
			x2 = ++x1;
			x2++;
		} else if(dir.contains("W")) {
			x2 = --x1;
			x2--;
		}
		if(world.checkForEmptyLocation(player.x + x2, player.y + y2, player.z)) {
			Creature c1 = world.creature(player.x + x1, player.y + y1, player.z);
			if(c1 != null) {
				player.commonAttack(c1, (int)(player.attackValue() * .8), "swipe at the %s for %d damage", c1.name());
			}
			player.moveBy(x2, y2, 0);
		} else {
			player.doAction("fail your phasestrike");
		}
		player.modifyMana(-1);
	}

}
