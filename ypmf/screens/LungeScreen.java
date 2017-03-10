package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.World;

public class LungeScreen implements Screen {
	protected Creature player;
	protected World world;

	public LungeScreen(Creature player, World world) {
		this.player = player;
		this.world = world;
		player.doAction("should choose a direction to lunge");
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
		System.out.println("attackTiles");
		int x1 = player.x, y1 = player.y;
		int x2 = x1, y2 = y1;
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
		System.out.println(player.x + " " + player.y + " " + x1 + " " + y1 + " " + x2 + " " + y2);
		Creature c1 = world.creature(x1, y1, player.z), c2 = world.creature(x2, y2, player.z);
		if(c1 != null) {
			player.commonAttack(c1, (int)(player.attackValue() * 1.5), "lunge at the %s for %d damage", c1.name());
		}
		if(c2 != null) {
			player.commonAttack(c2, (int)(player.attackValue() * 1.25), "lunge at the %s for %d damage", c2.name());
		}
		player.modifyMana(-1);
	}
}
