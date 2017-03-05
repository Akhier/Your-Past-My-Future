import javax.swing.JFrame;
import asciiPanel.AsciiPanel;

public class YPMF extends JFrame {
	private static final long serialVersionUID = 23015753830142527L;

	private AsciiPanel terminal;

	public YPMF() {
		super();
		terminal = new AsciiPanel();
		add(terminal);
		pack();
	}

	public static void main(String[] args) {
		YPMF app = new YPMF();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.setVisible(true);
	}
}
