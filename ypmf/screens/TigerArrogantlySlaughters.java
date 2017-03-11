package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.Point;
import ypmf.World;

public class TigerArrogantlySlaughters implements Screen {
	protected Creature player;
	protected World world;

	public TigerArrogantlySlaughters(Creature player, World world) {
		this.player = player;
		this.world = world;
		player.doAction("should choose a direction to let the Tiger Arrogantly Slaughter");
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
		int x = player.x, y = player.y, z = player.z;
		Point lastFreeTile = new Point(x, y, z);
		for(int i = 0; i < 10; i++) {
			if(x < 1 || x > world.width() - 2 || y < 1 || y > world.height() - 2) {
				break;
			}
			if(world.checkForEmptyLocation(x, y, z)) {
				lastFreeTile = new Point(x, y, z);
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
		}
		player.moveBy(lastFreeTile.x - player.x, lastFreeTile.y - player.y, 0);
		player.modifyMana(-13);
	}

	private void blastTile(int x, int y, int z) {
		Creature c = world.creature(x, y, z);
		if(c != null) {
			player.commonAttack(c, player.attackValue() * 10, "savage the %s for %d damage", c.name());
		}
	}
}
