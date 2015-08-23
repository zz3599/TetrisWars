
public class ShapeSaver {
	private Shape savedShape; 
	private boolean alreadySaved; 
	
	public ShapeSaver() {
		this.alreadySaved = false;
	}
	
	public boolean saveShape(Shape s){
		if(this.alreadySaved){
			return false; 
		} 
		this.savedShape = s;
		this.alreadySaved = true;
		return true;
	}
	
	public Shape getSavedShape(){		
		this.alreadySaved = false;
		return this.savedShape;		
	}
}
