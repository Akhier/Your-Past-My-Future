package ypmf.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {
	@Override
	public void displayOutput(AsciiPanel terminal) {
		terminal.clear();
		terminal.writeCenter("Your Past, My Future", 1);
		int y = 4;
		terminal.writeCenter("Welcome to the Game!", y++);
		terminal.writeCenter("There are 3 classes to choose from, each with 3 unique abilities.", y++);
		terminal.writeCenter("Each time you go down to the next level one of them will be unlocked.", y++);
		terminal.writeCenter("Luckily your previous attempt can help your present situation.", y++);
		terminal.writeCenter("As long as your last game had a different class from the one you choose now", y++);
		terminal.writeCenter("you get whatever the strongest power you had as a hereditary ability.", y++);
		terminal.writeCenter("This ability is even unlocked from the start!", y++);
		y++;
		terminal.writeCenter("The goal of the game is to make it through all 3 dungeon levels for you", y++);
		terminal.writeCenter("are a simple adventurer. You are only attempting to use this dungeon as a", y++);
		terminal.writeCenter("shortcut through a mountain to shave a month off your journey", y++);
		y++;
		terminal.writeCenter("While in game press [?] to bring up the help screen", y++);
		terminal.writeCenter("-- press [t] to play as a Pikeman --", 20);
		terminal.writeCenter("-- press [g] to play as an Assasin --", 21);
		terminal.writeCenter("-- press [b] to play as a Fire Magus --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		switch(key.getKeyCode()) {
		case KeyEvent.VK_T: return new PlayScreen(0);
		case KeyEvent.VK_G: return new PlayScreen(1);
		case KeyEvent.VK_B: return new PlayScreen(2);
		}
		return this;
	}
}
