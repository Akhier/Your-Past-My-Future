package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen{

	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("roguelike help", 1);
		terminal.write("Descend the Caves Of Slight Danger, find the lost Teddy Bear, and return to", 1, 3);
		terminal.write("the surface to win. Use what you find to avoid dying.", 1, 4);
		int y = 6;
		terminal.write("[?] for help", 2, y++);
		terminal.write("[;] to look around", 2, y++);
		terminal.write("[f] for first ability", 2, y++);
		terminal.write("[d] for second ability", 2, y++);
		terminal.write("[s] for last ability", 2, y++);
		terminal.write("[a] for hereditary ability", 2, y++);
		terminal.writeCenter("-- press any key to continue --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		return null;
	}
}
