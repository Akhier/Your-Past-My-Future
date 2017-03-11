package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.Tile;
import ypmf.World;

public class PheonixBurstBeamScreen implements Screen {
	protected Creature player;
	protected World world;

	public PheonixBurstBeamScreen(Creature player, World world) {
		this.player = player;
		this.world = world;
		player.doAction("should choose a direction to unleash a Pheonix Burst Beam");
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
		for(int x = -2; x <= 2; x++) {
			for(int y = -2; y <= 2; y++) {
				if(x == 0 && y == 0) {
					continue;
				}
				Creature c = world.creature(player.x + x, player.y + y, player.z);
				if(c != null) {
					player.commonAttack(c, player.attackValue() * 10, "burn down the %s for %d damage", c.name());
				}
			}
		}
		int x = player.x, y = player.y, z = player.z;
		do {
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
		} while(world.tile(x, y, z) != Tile.WALL);
	}

	private void blastTile(int x, int y, int z) {
		Creature c = world.creature(x, y, z);
		if(c != null) {
			player.commonAttack(c, player.attackValue() * 5, "blast the %s doing %d damage", c.name());
		}
	}
}
