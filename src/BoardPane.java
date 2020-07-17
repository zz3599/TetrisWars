import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * @author brookz
 *
 */
public class BoardPane extends JPanel implements ActionListener {
  private static final int SLEEP_TICK = 500;

  private Timer timer;
  private int boardWidth;
  private int boardHeight;
  private Shape[][] board;
  private Shape currentShape;
  private int currentShapeRow;
  private int currentShapeColumn;
  private boolean ended = false;

  public BoardPane(int width, int height) {
    setFocusable(true);
    this.boardWidth = width;
    this.boardHeight = height;
    this.board = new Shape[boardHeight][boardWidth];
    this.timer = new Timer(SLEEP_TICK, this);
    this.timer.start();
    this.clearBoard();
    this.resetCurrentshaperow();
    this.currentShapeColumn = boardWidth / 2;
  }

  public void resetCurrentshaperow() {
    this.currentShapeRow = 0;
  }

  public synchronized Shape getCurrentShape() {
    if (this.currentShape == null) {
      this.currentShape = Shape.generateRandomShape();
    }
    return this.currentShape;
  }

  public synchronized void setCurrentShape(Shape s) {
    this.currentShape = s;
    this.repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int i = 0; i < this.boardHeight; i++) {
      for (int j = 0; j < this.boardWidth; j++) {
        Shape shape = this.board[i][j];
        drawSquare(i, j, this.squareWidth(), this.squareHeight(), shape.getColor(), g);
      }
    }
    for (int i = 0; i < getCurrentShape().getCoordinates().length; i++) {
      int relativeRow = this.currentShape.getCoordinates()[i][0];
      int relativeColumn = this.currentShape.getCoordinates()[i][1];
      int ii = (relativeColumn + this.currentShapeColumn);
      int jj = (-relativeRow + this.currentShapeRow);
      drawSquare(jj, ii, this.squareWidth(), this.squareHeight(), this.currentShape.getColor(), g);
    }
  }

  private boolean isValidShape(Shape currentShape, int row, int column) {
    if (currentShape == null) {
      return false;
    }
    for (int i = 0; i < currentShape.getCoordinates().length; i++) {
      int rowAdjust = -currentShape.getCoordinates()[i][0];
      int columnAdjust = currentShape.getCoordinates()[i][1];
      int rowInQuestion = row + rowAdjust;
      int columnInQuestion = column + columnAdjust;
      if (rowInQuestion >= this.boardHeight || // if shapes are above the ceiling, it's still valid
          columnInQuestion >= this.boardWidth || columnInQuestion < 0) {
        return false;
      }
      if (rowInQuestion >= 0
          && this.board[rowInQuestion][columnInQuestion].getShapeName() != Shape.Tetrominoes.NoShape) {
        return false;
      }
    }
    return true;
  }

  public boolean isValidCurrentShape() {
    return isValidShape(this.getCurrentShape(), this.currentShapeRow, this.currentShapeColumn);
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

  public boolean finishCurrentShape() {
    boolean canFinish = false;
    while (isValidShape(getCurrentShape(), currentShapeRow, currentShapeColumn)) {
      canFinish = true;
      currentShapeRow++;
    }
    if (canFinish) {
      currentShapeRow--;
      for (int i = 0; i < getCurrentShape().getCoordinates().length; i++) {
        int rowAdjust = -currentShape.getCoordinates()[i][0];
        int columnAdjust = currentShape.getCoordinates()[i][1];
        int rowInQuestion = currentShapeRow + rowAdjust;
        int columnInQuestion = currentShapeColumn + columnAdjust;
        if (rowInQuestion >= this.boardHeight || rowInQuestion < 0 || columnInQuestion >= this.boardWidth
            || columnInQuestion < 0) {
          System.out.println(
              "Cannot finish current shape?" + currentShape + ", " + currentShapeRow + "," + currentShapeColumn);
          return false;
        }
        this.board[rowInQuestion][columnInQuestion] = currentShape;
      }
      System.out
          .println("Finished current shape: " + getCurrentShape() + ", " + currentShapeRow + "," + currentShapeColumn);
      currentShape = null;
      currentShapeRow = 0;
      currentShapeColumn = boardWidth / 2;
      return true;
    } else {
      System.out
          .println("Cannot finish current shape2?" + currentShape + ", " + currentShapeRow + "," + currentShapeColumn);
      return false;
    }

  }

  public boolean isCompleteRow(int rowNumber) {
    for (int i = 0; i < this.boardWidth; i++) {
      if (this.board[rowNumber][i].getShapeName() == Shape.Tetrominoes.NoShape) {
        return false;
      }
    }
    return true;
  }

  public void clearRow(int rowNumber) {
    for (int i = 0; i < this.boardWidth; i++) {
      this.board[rowNumber][i] = Shape.NO_SHAPE;
    }
  }

  public void clearLines() {
    int firstRow = -1;
    for (int i = 0; i < this.boardHeight; i++) {
      if (isCompleteRow(i)) {
        clearRow(i);
        if (firstRow < 0) {
          firstRow = i;
        }
        for (int j = i - 1; j >= 0; j--) {
          for (int k = 0; k < this.boardWidth; k++) {
            this.board[j + 1][k] = this.board[j][k];
          }
        }
        if (Math.abs(firstRow - i) >= 3) {
          System.out.println("TETRIS");
          break;
        }

      }
    }

  }

  public void clearBoard() {
    for (int i = 0; i < boardHeight; i++) {
      for (int j = 0; j < boardWidth; j++) {
        this.board[i][j] = Shape.NO_SHAPE;
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (!isValidShape(this.getCurrentShape(), this.currentShapeRow + 1, this.currentShapeColumn)) {
      // cant move anymore, make next turn generate a new shape
      if (!this.finishCurrentShape()) {
        // game is over since we can't even place our current shape
        // clearBoard();
        currentShape = null;
        ended = true;
        this.timer.stop();
      }
    }
    this.currentShapeRow++;
    clearLines();
    repaint();
  }

  private int squareWidth() {
    return (int) this.getSize().getWidth() / this.boardWidth;
  }

  private int squareHeight() {
    return (int) this.getSize().getHeight() / this.boardHeight;
  }

  public boolean isEnded() {
    return ended;
  }

  public void setEnded(boolean ended) {
    this.ended = ended;
  }

  public void up() {
    this.getCurrentShape().rotateLeft();
    if (!this.isValidCurrentShape()) {
      this.getCurrentShape().rotateRight();
    }
  }

  public void left() {
    currentShapeColumn--;
    if (!isValidShape(currentShape, currentShapeRow, currentShapeColumn)) {
      currentShapeColumn++;
    }
  }

  public void right() {
    currentShapeColumn++;
    if (!isValidShape(currentShape, currentShapeRow, currentShapeColumn)) {
      currentShapeColumn--;
    }
  }

  public void down() {
    currentShapeRow++;
    if (!isValidShape(currentShape, currentShapeRow, currentShapeColumn)) {
      currentShapeRow--;
    }
  }

  public void space() {
    if (!this.finishCurrentShape()) {
      // game is over if we can't place current shape
      // clearBoard();
      currentShape = null;
      timer.stop();
      ended = true;
    }
  }
}
