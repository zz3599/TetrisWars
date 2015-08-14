import java.awt.Color;
import java.util.Random;
public class Shape {
	public static enum Tetrominoes {
		NoShape, StraightShape, TShape, OShape, JShape, LShape, SShape, ZShape
	}
	
	static int[][][] COORDINATES = {
		{{0, 0}, {0, 0}, {0, 0}, {0, 0}},
		{{0, 1}, {0, 0}, {0, -1}, {0, -2}},
		{{1, 0}, {0, 0}, {-1, 0}, {0, -1}},
		{{1, -1}, {0, 0}, {0, -1}, {1, 0}},
		{{1, 0}, {0, 0}, {-1, 0}, {-1, -1}},
		{{1, 0}, {0, 0}, {-1, 0}, {-1, 1}},
		{{1, 0}, {0, 0}, {0, -1}, {1, 1}},
		{{1, 0}, {0, 0}, {0, 1}, {1, -1}},		
	};
	
	static Color[] COLORS = {
		Color.GRAY, Color.YELLOW, Color.BLUE, Color.RED, 
		Color.CYAN, Color.BLACK, Color.DARK_GRAY, Color.GREEN
	};
	
	private static final Random RANDOMGENERATOR = new Random();
	private int[][] coordinates; 
	private Tetrominoes shapeName;  
	private Color color; 
	
	public Shape(Tetrominoes shapeName){
		this.coordinates = COORDINATES[shapeName.ordinal()];
		this.shapeName = shapeName;
		this.color = COLORS[shapeName.ordinal()];
	}
	
	public void rotateLeft(){
		if (this.shapeName == Tetrominoes.OShape){
			return;
		}
		for(int i = 0; i < this.coordinates.length; i++){
			int y = this.coordinates[i][0];
			int x = this.coordinates[i][1];
			this.coordinates[i][0] = -x;
			this.coordinates[i][1] = y;
		}
	}
	
	public void rotateRight(){
		if (this.shapeName == Tetrominoes.OShape){
			return;
		}
		for(int i = 0; i < this.coordinates.length; i++){
			int y = this.coordinates[i][0];
			int x = this.coordinates[i][1];
			this.coordinates[i][0] = x;
			this.coordinates[i][1] = -y;
		}
	}
	
	public int getMinRow(){
		int minRow = Integer.MAX_VALUE;
		for(int i = 0; i < this.coordinates.length; i++){
			if(this.coordinates[i][0] < minRow){
				minRow = this.coordinates[i][0];
			}
		}
		return minRow; 
	}
	
	public int[][] getCoordinates(){
		return this.coordinates;
	}
	
	public Tetrominoes getShapeName() {
		return shapeName;
	}

	public static Shape generateRandomShape(){
		//valid shapes start at index 1
		int index = RANDOMGENERATOR.nextInt(Tetrominoes.values().length-1) + 1;
		return new Shape(Tetrominoes.values()[index]);
	}	
	
	public Color getColor() {
		return color;
	}

	/**
	 * Monte carlo method to calculate pi. We throw random darts into a quadrant of the unit circle, which has area pi/4. So, we multiple
	 * the ratio by 4 to get the sampled value of pi.
	 */
	public static void printPi(){
		Random r = new Random();
		int inCircle = 0;
		int samples = 100000000;
		for(int i = 0; i < samples; i++){
			float x = r.nextFloat();
			float y = r.nextFloat(); 
			if ((x*x + y*y) < 1){
				inCircle++;
			}
		}
		System.out.println((double)inCircle/(double)samples*4);
	}
}
