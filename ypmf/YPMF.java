package ypmf;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import ypmf.screens.Screen;
import ypmf.screens.StartScreen;
/**
 * @author Akhier Dragonheart
 * @version 1.0
 */
public class YPMF extends JFrame implements KeyListener{
	private static final long serialVersionUID = 23015753830142527L;

	private AsciiPanel terminal;
	private Screen screen;

	public YPMF() {
		super();
		terminal = new AsciiPanel();
		add(terminal);
		pack();
		screen = new StartScreen();
		addKeyListener(this);
		repaint();
	}

	@Override
	public void repaint() {
		terminal.clear();
		screen.displayOutput(terminal);
		super.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		screen = screen.respondToUserInput(e);
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) { }

	@Override
	public void keyTyped(KeyEvent e) { }

	public static void main(String[] args) {
		YPMF app = new YPMF();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
