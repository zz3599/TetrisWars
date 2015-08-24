import javax.swing.JPanel;
import java.awt.Graphics;

public class SaverPane extends JPanel{
	private Game game; 
	private ShapeSaver shapeSaver = new ShapeSaver();
	static final int WIDTH = 4;
	static final int HEIGHT = 4;
	
	public SaverPane(Game game){
		this.game = game;
	}
	
	public void paintComponent(Graphics g){
		for(int i = 0; i < WIDTH; i++){
			
		}
	}
	
	public int squareWidth(){
		return (int)this.getSize().getWidth()/WIDTH;
	}
	
	public int squareHeight(){
		return (int)this.getSize().getHeight()/HEIGHT;
	}
	
	public void swap(){
		if(this.game != null){
			Shape currentShape = this.game.board.getCurrentShape();
			this.game.board.setCurrentShape(this.shapeSaver.swapShape(currentShape));
			this.game.repaint();
		}
	}
	
	
}

