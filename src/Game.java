import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class Game extends JFrame implements KeyListener {
	private static final int WIDTH = 10;
	private static final int HEIGHT = 22;

	public SaverPane saverPane;
	public BoardPane board = new BoardPane(WIDTH, HEIGHT);

	public Game() {
		super("TetrisWars");
		this.setSize(200, 600);

		board.setPreferredSize(new Dimension(200, 400));
		this.saverPane = new SaverPane(this);
		saverPane.setPreferredSize(new Dimension(200, 200));
		this.setLayout(new FlowLayout());
		this.add(board);

		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setFocusable(true);
		
		this.addKeyListener(this);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (board.isEnded()) {
			return;
		}
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_UP:
			board.up();				
			break;
		case KeyEvent.VK_LEFT:
			board.left();
			break;
		case KeyEvent.VK_RIGHT:
			board.right();				
			break;
		case KeyEvent.VK_DOWN:
			board.down();
			break;
		case KeyEvent.VK_SPACE:
			board.space();
			break;
		case KeyEvent.VK_SHIFT:
			saverPane.swap();
			break;
		}
		board.repaint();

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}


	public static void main(String[] args) {
		Game game = new Game();
	}

}
