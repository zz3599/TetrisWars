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
    this.saverPane = new SaverPane();
    saverPane.setPreferredSize(new Dimension(100, 100));
    this.setLayout(new FlowLayout());
    this.add(saverPane);
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
    if (keyCode == KeyEvent.VK_SHIFT) {
      saverPane.swap(this);
    } else if (keyCode == KeyEvent.VK_SPACE) {
      board.space();
    } else if (KeyEventMapping.KEY_EVENTS.contains(keyCode)) {
      board.setKeyEvent(keyCode);
    }
    board.repaint();

  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (KeyEventMapping.KEY_EVENTS.contains(e.getKeyCode())) {
      board.setKeyEvent(0);
    }
  }

  public static void main(String[] args) {
    new Game();
  }

}
