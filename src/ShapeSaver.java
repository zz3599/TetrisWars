import java.util.HashSet;
import java.util.Set;

public class ShapeSaver {
  private Set<Shape> seenShapes;
  private Shape savedShape;

  public ShapeSaver() {
    this.seenShapes = new HashSet<Shape>();
    this.savedShape = null;
  }

  public synchronized Shape swapShape(Shape s) {
    if (this.seenShapes.contains(s)) {
      System.out.println("Unable to swap shape");
      return s;
    }
    Shape swappedShape = this.savedShape;
    this.savedShape = s;
    this.seenShapes.add(s);
    System.out.println("Swapped shape " + s + " for " + swappedShape);
    return swappedShape;
  }

  public Shape getSavedShape() {
    return this.savedShape;
  }
}
