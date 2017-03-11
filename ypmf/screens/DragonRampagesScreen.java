package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.World;

public class DragonRampagesScreen implements Screen {
	protected Creature player;
	protected World world;

	public DragonRampagesScreen(Creature player, World world) {
		this.player = player;
		this.world = world;
		player.doAction("should choose a direction to unleash the Dragon Rampages");
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
		player.modifyMana(-12);
		return null;
	}

	private void attackTiles(String dir) {
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				if(x == 0 && y == 0) {
					continue;
				}
				Creature c = world.creature(player.x + x, player.y + y, player.z);
				if(c != null) {
					player.commonAttack(c, player.attackValue() * 20, "crush the %s with your killing intent doing %d damage", c.name());
				}
			}
		}
		int x = player.x, y = player.y, z = player.z;
		for(int i = 0; i < 10; i++) {
			if(x < 1 || x > world.width() - 2 || y < 1 || y > world.height() - 2) {
				break;
			}
			switch(dir) {
			case("N"):
				y--;
				blastTile(x, y, z);
				blastTile(x - 1, y, z);
				blastTile(x + 1, y, z);
				break;
			case("S"):
				y++;
				blastTile(x, y, z);
				blastTile(x - 1, y, z);
				blastTile(x + 1, y, z);
				break;
			case("E"):
				x++;
				blastTile(x, y, z);
				blastTile(x, y - 1, z);
				blastTile(x, y + 1, z);
				break;
			case("W"):
				x--;
				blastTile(x, y, z);
				blastTile(x, y - 1, z);
				blastTile(x, y + 1, z);
				break;
			case("NE"):
				x++;
				y--;
				blastTile(x, y, z);
				blastTile(x - 1, y, z);
				blastTile(x, y + 1, z);
				break;
			case("NW"):
				x--;
				y--;
				blastTile(x, y, z);
				blastTile(x + 1, y, z);
				blastTile(x, y + 1, z);
				break;
			case("SE"):
				x++;
				y++;
				blastTile(x, y, z);
				blastTile(x - 1, y, z);
				blastTile(x, y - 1, z);
				break;
			case("SW"):
				x--;
				y++;
				blastTile(x, y, z);
				blastTile(x + 1, y, z);
				blastTile(x, y - 1, z);
				break;
			}
			world.dig(x, y, z);
		}
	}

	private void blastTile(int x, int y, int z) {
		Creature c = world.creature(x, y, z);
		if(c != null) {
			player.commonAttack(c, player.attackValue() * 8, "striking the %s with your spear intent doing %d damage", c.name());
		}
	}
}
