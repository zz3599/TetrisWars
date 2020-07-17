import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

public class Shape {
  // TODO: better hashcoding?
  private static int SHAPE_ID = 0;

  public static enum Tetrominoes {
    NoShape, StraightShape, TShape, OShape, JShape, LShape, SShape, ZShape
  }

  public static final Shape NO_SHAPE = new Shape(Shape.Tetrominoes.NoShape);

  static int[][][] COORDINATES = { { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 } },
      { { 0, 1 }, { 0, 0 }, { 0, -1 }, { 0, -2 } }, { { 1, 0 }, { 0, 0 }, { -1, 0 }, { 0, -1 } },
      { { 1, -1 }, { 0, 0 }, { 0, -1 }, { 1, 0 } }, { { 1, 0 }, { 0, 0 }, { -1, 0 }, { -1, -1 } },
      { { 1, 0 }, { 0, 0 }, { -1, 0 }, { -1, 1 } }, { { 1, 0 }, { 0, 0 }, { 0, -1 }, { 1, 1 } },
      { { 1, 0 }, { 0, 0 }, { 0, 1 }, { 1, -1 } }, };

  static Color[] COLORS = { Color.GRAY, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.ORANGE, Color.GREEN,
      Color.RED };

  private static final Random RANDOMGENERATOR = new Random();
  private int[][] coordinates;
  private Tetrominoes shapeName;
  private Color color;
  private int shapeId = SHAPE_ID++;

  public Shape(Tetrominoes shapeName) {
    if (shapeName != Tetrominoes.NoShape) {
      this.coordinates = new int[COORDINATES[shapeName.ordinal()].length][2];
      for (int i = 0; i < COORDINATES[shapeName.ordinal()].length; i++) {
        this.coordinates[i][0] = COORDINATES[shapeName.ordinal()][i][0];
        this.coordinates[i][1] = COORDINATES[shapeName.ordinal()][i][1];
      }
    }
    this.shapeName = shapeName;
    this.color = COLORS[shapeName.ordinal()];
  }

  public void rotateLeft() {
    if (this.shapeName == Tetrominoes.OShape) {
      return;
    }
    for (int i = 0; i < this.coordinates.length; i++) {
      int y = this.coordinates[i][0];
      int x = this.coordinates[i][1];
      this.coordinates[i][0] = -x;
      this.coordinates[i][1] = y;
    }
  }

  public void rotateRight() {
    if (this.shapeName == Tetrominoes.OShape) {
      return;
    }
    for (int i = 0; i < this.coordinates.length; i++) {
      int y = this.coordinates[i][0];
      int x = this.coordinates[i][1];
      this.coordinates[i][0] = x;
      this.coordinates[i][1] = -y;
    }
  }

  public int getMinRow() {
    int minRow = Integer.MAX_VALUE;
    for (int i = 0; i < this.coordinates.length; i++) {
      if (this.coordinates[i][0] < minRow) {
        minRow = this.coordinates[i][0];
      }
    }
    return minRow;
  }

  public int[][] getCoordinates() {
    return this.coordinates;
  }

  public Tetrominoes getShapeName() {
    return shapeName;
  }

  public static Shape generateRandomShape() {
    // valid shapes start at index 1
    int index = RANDOMGENERATOR.nextInt(Tetrominoes.values().length - 1) + 1;
    return new Shape(Tetrominoes.values()[index]);
    // return new Shape(Tetrominoes.StraightShape);
  }

  public Color getColor() {
    return color;
  }

  public String toString() {
    return this.shapeName.toString() + ": " + Arrays.deepToString(this.coordinates) + ", id=" + this.shapeId;
  }

  public int hashCode() {
    return this.shapeId;
  }

}
