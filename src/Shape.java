
public class Shape {
	enum Tetrominoes {
		StraightShape, OShape, TShape, JShape, LShape, SShape, ZShape
	}
	
	static int[][][] COORDINATES = {
		{{0, 1}, {0, 0}, {0, -1}, {0, -2}},
		{{1, 0}, {0, 0}, {-1, 0}, {0, -1}},
		{{0, 1}, {0, 0}, {0, -1}, {-1, 0}},
		{{1, 0}, {0, 0}, {-1, 0}, {-1, -1}},
		{{1, 0}, {0, 0}, {-1, 0}, {1, -1}},
		{{1, 0}, {0, 0}, {-1, 0}, {-1, -1}},
		{{1, 0}, {0, 0}, {0, 1}, {0, -1}},		
	};
	
	private int[][] coordinates; 
	private Tetrominoes shapeName;  
	public Shape(int[][] coordinates, Tetrominoes shapeName){
		this.coordinates = coordinates;
		this.shapeName = shapeName;
	}
	
	public void rotateLeft(){
		if (this.shapeName == Tetrominoes.OShape){
			return;
		}
		for(int i = 0; i < this.coordinates.length; i++){
			int x = this.coordinates[i][0];
			int y = this.coordinates[i][1];
			this.coordinates[i][0] = -y;
			this.coordinates[i][1] = x;
		}
	}
	
	public void rotateRight(){
		if (this.shapeName == Tetrominoes.OShape){
			return;
		}
		for(int i = 0; i < this.coordinates.length; i++){
			int x = this.coordinates[i][0];
			int y = this.coordinates[i][1];
			this.coordinates[i][0] = y;
			this.coordinates[i][1] = -x;
		}
	}
}
