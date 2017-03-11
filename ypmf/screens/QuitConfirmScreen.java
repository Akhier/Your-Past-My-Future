package ypmf.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import ypmf.Creature;

public class QuitConfirmScreen implements Screen {
	protected Creature player;

	public QuitConfirmScreen(Creature player) {
		this.player = player;
		player.doAction("Press [q] if you really want to stop this game, progress is not saved");
	}

	@Override
	public void displayOutput(AsciiPanel terminal) { }

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if(key.getKeyCode() == KeyEvent.VK_Q) {
			player.modifyHp(0, "Quit Game");
			return new LoseScreen(player);
		}
		return null;
	}
}
