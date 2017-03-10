package ypmf.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.MapGen;
import ypmf.StuffFactory;
import ypmf.FieldOfView;
import ypmf.Tile;
import ypmf.World;

public class PlayScreen implements Screen {
	private World world;
	private Creature player;
	private int screenWidth, screenHeight;
	private List<String> messages;
	private FieldOfView fov;
	private Screen subscreen;

	public PlayScreen(int playerclass) {
		screenWidth = 80;
		screenHeight = 23;
		messages = new ArrayList<String>();
		createWorld();
		fov = new FieldOfView(world);
		StuffFactory factory = new StuffFactory(world);
		createCreatures(factory, playerclass);
	}

	private void createCreatures(StuffFactory factory, int playerclass) {
		player = factory.newPlayer(messages, fov, playerclass);
		for(int z = 0; z < world.depth(); z++) {
			for(int i = 0; i < 4; i++) {
				factory.newFungus(z);
			}
			for(int i = 0; i < 10; i++) {
				factory.newBat(z);
			}
			for(int i = 0; i < z * 2 + 1; i++) {
				factory.newZombie(z, player);
				factory.newGoblin(z, player);
			}
		}
	}

	private void createWorld() {
		world = MapGen.makeLevels(200, 32, 3, 222);
	}

	public int getScrollX() { return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth)); }
	public int getScrollY() { return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight)); }

	@Override
	public void displayOutput(AsciiPanel terminal) {
		int left = getScrollX();
		int top = getScrollY();
		displayTiles(terminal, left, top);
		displayMessages(terminal, messages);
		String stats = String.format(" %3d/%3d hp   %d/%d mana", player.hp(), player.maxHp(), player.mana(), player.maxMana());
		terminal.write(stats, 1, 23);
		if(subscreen != null) {
			subscreen.displayOutput(terminal);
		}
	}

	private void displayMessages(AsciiPanel terminal, List<String> messages) {
		int top = screenHeight - messages.size();
		for(int i = 0; i < messages.size(); i++) {
			terminal.writeCenter(messages.get(i), top + i);
		}
		if(subscreen == null) {
			messages.clear();
		}
	}

	private void displayTiles(AsciiPanel terminal, int left, int top) {
		fov.update(player.x, player.y, player.z, player.visionRadius());
		for(int x = 0; x < screenWidth; x++) {
			for(int y = 0; y < screenHeight; y++) {
				int wx = x + left;
				int wy = y + top;
				if(player.canSee(wx, wy, player.z)) {
					terminal.write(world.glyph(wx, wy, player.z), x, y, world.color(wx, wy, player.z));
				} else {
					terminal.write(fov.tile(wx, wy, player.z).glyph(), x, y, Color.darkGray);
				}
			}
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(subscreen != null) {
			subscreen = subscreen.respondToUserInput(key);
		} else {
			switch (key.getKeyCode()){
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_NUMPAD4:
				player.moveBy(-1, 0, 0);
				break;
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_NUMPAD6:
				player.moveBy( 1, 0, 0);
				break;
			case KeyEvent.VK_UP:
			case KeyEvent.VK_NUMPAD8:
				player.moveBy( 0,-1, 0);
				break;
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_NUMPAD2:
				player.moveBy( 0, 1, 0);
				break;
			case KeyEvent.VK_NUMPAD7:
				player.moveBy(-1,-1, 0);
				break;
			case KeyEvent.VK_NUMPAD9:
				player.moveBy( 1,-1, 0);
				break;
			case KeyEvent.VK_NUMPAD1:
				player.moveBy(-1, 1, 0);
				break;
			case KeyEvent.VK_NUMPAD3:
				player.moveBy( 1, 1, 0);
				break;
			case KeyEvent.VK_SEMICOLON:
				subscreen = new LookScreen(player, "Looking", player.x - getScrollX(), player.y - getScrollY());
				break;
			case KeyEvent.VK_F:
				if(player.mana() >= 1) {
					switch(player.pClass()) {
					case(0):
						subscreen = new LungeScreen(player);
						break;
					case(1):
						subscreen = new PhaseStrikeScreen(player, world);
						break;
					case(2):
						subscreen = new SparkScreen(player, player.x - getScrollX(), player.y - getScrollY());
					}
				} else {
					player.doAction("need more mana");
				}
				break;
			case KeyEvent.VK_D:
				if(player.z >= 1) {
					switch(player.pClass()) {
					case(0):
						if(player.mana() >= 4) {
						subscreen = new WideSwipeScreen(player);
						} else {
							player.doAction("need 4 mana to wide swipe");
						}
					}
				}
				break;
			case KeyEvent.VK_S:
				break;
			case KeyEvent.VK_A:
				break;
			}
			
			switch (key.getKeyChar()){
			case '<': 
				if (userIsTryingToExit()) {
					return userExits();
				} else {
					player.moveBy( 0, 0, -1);
					break;
				}
			case '>':
				if(world.tile(player.x, player.y, player.z) == Tile.STAIRS_DOWN && player.z == world.depth() - 1) {
					return new WinScreen();
				}
				player.moveBy( 0, 0, 1);
				break;
			case '?':
				subscreen = new HelpScreen();
				break;
			}
		}
		if(subscreen == null) {
			world.update();
		}
		if(player.hp() < 1) {
			return new LoseScreen(player);
		}
		return this;
	}

	private boolean userIsTryingToExit() {
		return player.z == 0 && world.tile(player.x, player.y, player.z) == Tile.STAIRS_UP;
	}

	private Screen userExits() {
		player.modifyHp(0, "Died while cowardly fleeing the caves.");
		return new LoseScreen(player);
	}
}
