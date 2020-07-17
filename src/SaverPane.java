import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

/**
 * TODO: this class needs to be refactored to be related to BoardPane
 **/
public class SaverPane extends JPanel {
  private Game game;
  private ShapeSaver shapeSaver = new ShapeSaver();
  static final int WIDTH = 4;
  static final int HEIGHT = 4;
  Shape[][] board;

  public SaverPane(Game game) {
    this.game = game;
    this.board = new Shape[HEIGHT][WIDTH];
    this.clearBoard();
  }

  private void clearBoard() {
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        this.board[i][j] = new Shape(Shape.Tetrominoes.NoShape);
      }
    }
  }

  private void updatePane() {
    this.clearBoard();
    Shape savedShape = this.shapeSaver.getSavedShape();
    if (savedShape != null) {
      int[][] coordinates = savedShape.getCoordinates();
      for (int i = 0; i < coordinates.length; i++) {
        int xPosition = -coordinates[i][0] + 2;
        int yPosition = coordinates[i][1] + 2;
        this.board[xPosition][yPosition] = savedShape;
      }
    }
  }

  public void paintComponent(Graphics g) {
    updatePane();
    for (int i = 0; i < HEIGHT; i++) {
      for (int j = 0; j < WIDTH; j++) {
        this.drawSquare(i, j, squareWidth(), squareHeight(), this.board[i][j].getColor(), g);
      }
    }

  }

  private void drawSquare(int row, int column, int width, int height, Color color, Graphics g) {
    int x = column * squareWidth();
    int y = row * squareHeight();
    g.setColor(color);
    g.fillRect(x + 1, y + 1, width - 2, height - 2);
    g.setColor(color.brighter());
    g.drawLine(x, y, x + squareWidth() - 1, y);
    g.drawLine(x, y, x, y + squareHeight() - 1);
    g.setColor(color.darker());
    g.drawLine(x + squareWidth() - 1, y, x + squareWidth() - 1, y + squareHeight() - 1);
    g.drawLine(x, y + squareHeight() - 1, x + squareWidth() - 1, y + squareHeight() - 1);
  }

  public int squareWidth() {
    return (int) this.getSize().getWidth() / WIDTH;
  }

  public int squareHeight() {
    return (int) this.getSize().getHeight() / HEIGHT;
  }

  public void swap() {
    if (this.game != null) {
      Shape currentShape = this.game.board.getCurrentShape();
      Shape swappedShape = this.shapeSaver.swapShape(currentShape);
      if (swappedShape != currentShape) {
        this.game.board.setCurrentShape(swappedShape);
        this.game.board.resetCurrentshaperow();
      }
      this.game.repaint();
    }
  }

}
