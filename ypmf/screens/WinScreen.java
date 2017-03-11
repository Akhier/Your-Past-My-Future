package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import ypmf.World;

public class WinScreen implements Screen {
	protected World world;

	public WinScreen(World world) {
		this.world = world;
	}
	
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("You won on Turn " + world.turn() + ".", 1);
		terminal.writeCenter("-- press [enter] to restart --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return key.getKeyCode() == KeyEvent.VK_ENTER ? new StartScreen() : this;
	}
}
