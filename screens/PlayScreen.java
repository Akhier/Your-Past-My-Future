package screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;
import systems.MapGen;
import components.MapLevel;

public class PlayScreen implements Screen {
	@Override
	public void displayOutput(AsciiPanel terminal) {
		MapLevel testMap = MapGen.makeMap(80, 20, 222);
		for(int x = 0; x < testMap.width(); x++) {
			for(int y = 0; y < testMap.height(); y++) {
				char tile = testMap.tileWalkable(x, y) ? '.' : '#';
				terminal.write(tile, x, y);
			}
		}
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch (key.getKeyCode()){
		case KeyEvent.VK_ESCAPE: return new LoseScreen();
		case KeyEvent.VK_ENTER: return new WinScreen();
		default: return null;
		}
	}
}
