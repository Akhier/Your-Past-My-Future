package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.writeCenter("Your Past, My Future", 1);
		terminal.writeCenter("-- press [t] to play as a Pikeman --", 20);
		terminal.writeCenter("-- press [g] to play as an Assasin --", 21);
		terminal.writeCenter("-- press [b] to play as a Fire Magus --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyChar()) {
		case 't': return new PlayScreen(0);
		case 'g': return new PlayScreen(1);
		case 'b': return new PlayScreen(2);
		}
		return this;
	}

}
