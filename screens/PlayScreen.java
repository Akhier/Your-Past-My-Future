package screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import systems.MapGen;
import components.Level;

public class PlayScreen implements Screen {
	@Override
	public void displayOutput(AsciiPanel terminal) {
		Level testMap = MapGen.makeMap(80, 24, 222);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}
}
