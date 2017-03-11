package ypmf.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ypmf.Creature;
import ypmf.World;

public class QuitConfirmScreen implements Screen {
	protected Creature player;
	protected World world;

	public QuitConfirmScreen(Creature player, World world) {
		this.player = player;
		this.world = world;
		player.doAction("Press [q] if you really want to stop this game, progress is not saved");
	}

	@Override
	public void displayOutput(AsciiPanel terminal) { }

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_Q) {
			player.modifyHp(0, "Quit Game");
			return new LoseScreen(player, world);
		}
		return null;
	}
}
