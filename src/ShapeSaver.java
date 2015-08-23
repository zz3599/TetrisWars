import java.util.HashSet;
import java.util.Set;

public class ShapeSaver {
	private Set<Shape> seenShapes; 
	private Shape savedShape; 
	
	
	public ShapeSaver() {
		this.seenShapes = new HashSet<Shape>();
		this.savedShape = null;
	}
	
	public synchronized Shape swapShape(Shape s){
		if(this.seenShapes.contains(s)){
			return s; 
		}
		Shape swappedShape = this.savedShape; 
		this.savedShape = s; 
		this.seenShapes.add(s);
		return swappedShape;
	}
}
