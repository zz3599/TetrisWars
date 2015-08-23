import javax.swing.JPanel;

public class SaverPane extends JPanel{
	private Game game; 
	private ShapeSaver shapeSaver = new ShapeSaver();
	
	public SaverPane(Game game){
		this.game = game;
	}
	
	public SaverPane(){
		
	}
	
	public void swap(){
		if(this.game != null){
			Shape currentShape = this.game.board.getCurrentShape();
			this.shapeSaver.swapShape(currentShape);
		}
	}
	
	
}
