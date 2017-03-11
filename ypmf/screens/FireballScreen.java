package ypmf.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.Line;
import ypmf.Point;
import ypmf.World;

public class FireballScreen implements Screen {
	protected Creature player;
	protected World world;
	private int sx, sy, x, y;

	public FireballScreen(Creature player, World world, int sx, int sy) {
		this.player = player;
		this.world = world;
		this.sx = sx;
		this.sy = sy;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		Point lastP = null;
		for (Point p : new Line(sx, sy, sx + x, sy + y)) {
			if(p.x < 0 || p.x >= 80 || p.y < 0 || p.y >= 24) {
				continue;
			}
			lastP = p;
			terminal.write('*', p.x, p.y, AsciiPanel.brightMagenta);
		}
		for(int x = -1; x <= 1; x++) {
			for(int y = -1; y <= 1; y++) {
				terminal.write('*', lastP.x + x, lastP.y + y, AsciiPanel.brightRed);
			}
		}
		terminal.clear(' ', 0, 23, 80, 1);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		int px = x, py = y;
		switch(key.getKeyCode()) {
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_NUMPAD4:
			x--;
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_NUMPAD6:
			x++;
			break;
		case KeyEvent.VK_UP:
		case KeyEvent.VK_NUMPAD8:
			y--;
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_NUMPAD2:
			y++;
			break;
		case KeyEvent.VK_NUMPAD7:
			x--;
			y--;
			break;
		case KeyEvent.VK_NUMPAD9:
			x++;
			y--;
			break;
		case KeyEvent.VK_NUMPAD1:
			x--;
			y++;
			break;
		case KeyEvent.VK_NUMPAD3:
			x++;
			y++;
			break;
		case KeyEvent.VK_ENTER:
			selectWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y);
			return null; 
		case KeyEvent.VK_ESCAPE:
			return null;
		}
		if(!isAcceptable(player.x + x, player.y + y)) {
			x = px;
			y = py;
		}
		return this;
	}

	public boolean isAcceptable(int x, int y) {
		int distance = (int)Math.sqrt(Math.pow(x - player.x, 2) + Math.pow(y - player.y, 2));
		return distance <= player.visionRadius();
	}

	public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
		for(int X = -1; X <= 1; X++) {
			for(int Y = -1; Y <= 1; Y++) {
				Creature c = world.creature(x + X, y + Y, player.z);
				if(c != null) {
					if(player.canSee(x + X, y + Y, player.z)) {
						player.commonAttack(c, player.attackValue(), "watch the %s burn for %d damage", c.name());
					} else {
						player.commonAttack(c, (int)(player.attackValue() / 2), "something burns slightly for %d damage");
					}
				}
			}
		}
		player.modifyMana(-3);
		return;
	}
}
