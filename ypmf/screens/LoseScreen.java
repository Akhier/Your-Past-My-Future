package ypmf.screens;

import java.awt.event.KeyEvent;
import ypmf.Creature;
import ypmf.World;
import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {
	protected Creature player;
	protected World world;

	public LoseScreen(Creature player, World world) {
		this.player = player;
		this.world = world;
	}

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("R.I.P. on Turn " + world.turn(), 3);
		terminal.writeCenter(player.causeOfDeath(), 5);
		terminal.writeCenter("-- press [enter] to restart --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen() : this;
	}
}
