import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener {
	private static final int WIDTH = 10;
	private static final int HEIGHT = 22;
	private static final int SLEEP_TICK = 100;
	
	private Timer timer;
	private int boardWidth; 
	private int boardHeight; 
	private Shape[][] board; 
	private Shape currentShape; 
	private int currentShapeRow; 
	private int currentShapeColumn;
	public Game(int width, int height){
		setFocusable(true);
		addKeyListener(new InputListener(this));
		this.boardWidth = width; 
		this.boardHeight = height;
		this.board = new Shape[boardHeight][boardWidth];
		this.timer = new Timer(SLEEP_TICK, this);
		this.timer.start();		
		this.clearBoard();		
		this.currentShapeRow = 0;
		this.currentShapeColumn = boardWidth/2;
	}
	
	public synchronized Shape getCurrentShape(){
		if(this.currentShape == null){
			this.currentShape = Shape.generateRandomShape();
		}
		return this.currentShape;
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for(int i = 0; i < this.boardHeight; i++){
			for(int j = 0; j < this.boardWidth; j++){
				Shape shape = this.board[i][j];
				drawSquare(i, j, this.squareWidth(), this.squareHeight(), shape.getColor(), g);
			}
		}		
		for(int i = 0; i < getCurrentShape().getCoordinates().length; i++){
			int relativeRow = this.currentShape.getCoordinates()[i][0];
			int relativeColumn = this.currentShape.getCoordinates()[i][1];
			int ii = (relativeColumn + this.currentShapeColumn);
			int jj = (-relativeRow + this.currentShapeRow);
			drawSquare(jj, ii, this.squareWidth(), this.squareHeight(), this.currentShape.getColor(), g);			
		}		
	}	
	
	public boolean isValidShape(Shape currentShape, int row, int column){
		for(int i = 0; i < currentShape.getCoordinates().length; i++){
			int rowAdjust = -currentShape.getCoordinates()[i][0];
			int columnAdjust = currentShape.getCoordinates()[i][1];
			int rowInQuestion = row + rowAdjust;
			int columnInQuestion = column + columnAdjust;
			if ( rowInQuestion >= this.boardHeight || rowInQuestion < 0 || 
					columnInQuestion >= this.boardWidth || columnInQuestion < 0){
				return false;
			}
			if ( this.board[rowInQuestion][columnInQuestion].getShapeName() != Shape.Tetrominoes.NoShape){
				return false;
			}
		}
		return true;
	}
	
	private void drawSquare(int row, int column, int width, int height, Color color, Graphics g){
		int x = column*squareWidth();
		int y = row*squareHeight();
		g.setColor(color);
		g.fillRect(x+1, y+1, width-2, height-2);
		g.setColor(color.brighter());
		g.drawLine(x, y, x+squareWidth()-1, y+1);
		g.drawLine(x, y, x+1, y+squareHeight()-1);
		g.setColor(color.darker());
		g.drawLine(x+squareWidth()-1, y, x+squareWidth(), y+squareHeight()-1);
		g.drawLine(x, y+squareHeight()-1, x+squareWidth()-1, y+squareHeight());
	}
	
	public boolean finishCurrentShape(){
		while(isValidShape(getCurrentShape(), currentShapeRow, currentShapeColumn)){
			currentShapeRow++;
		}
		currentShapeRow--;
		for(int i = 0; i < currentShape.getCoordinates().length; i++){
			int rowAdjust = -currentShape.getCoordinates()[i][0];
			int columnAdjust = currentShape.getCoordinates()[i][1];
			int rowInQuestion = currentShapeRow + rowAdjust;
			int columnInQuestion = currentShapeColumn + columnAdjust;
			if (rowInQuestion >= this.boardHeight || rowInQuestion < 0 ||
					columnInQuestion >= this.boardWidth || columnInQuestion < 0){
				System.out.println("Cannot finish current shape?" + currentShape + ", " + currentShapeRow + "," + currentShapeColumn);
				return false;
			}
			this.board[rowInQuestion][columnInQuestion] = currentShape;
		}	
		currentShape = null;
		currentShapeRow = 0;
		currentShapeColumn = boardWidth/2;
		System.out.println("Finished current shape");
		return true;
	}
		
	public void clearBoard(){
		for (int i = 0; i < boardHeight; i++){
			for(int j = 0; j < boardWidth; j++){
				this.board[i][j] = new Shape(Shape.Tetrominoes.NoShape); 
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.currentShapeRow++;		
		if (!isValidShape(this.getCurrentShape(), this.currentShapeRow, this.currentShapeColumn)){
			//cant move anymore, make next turn generate a new shape 
			if (!this.finishCurrentShape()){
				//game is over since we can't even place our current shape
				//clearBoard(); 
				this.timer.stop();
			}		
		} else {
			//there's nothing to do, currently
			
		}		
		repaint();
	}
	
	private int squareWidth(){
		return (int)this.getSize().getWidth() / this.boardWidth;
	}
	
	private int squareHeight(){
		return (int)this.getSize().getHeight() / this.boardHeight;
	}
	
	class InputListener implements KeyListener {
		Game game; 
		public InputListener(Game game){
			this.game = game;
		}

		@Override
		public void keyTyped(KeyEvent e) {			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			switch(keyCode){
			case KeyEvent.VK_UP:
				getCurrentShape().rotateLeft();
				if(!isValidShape(currentShape, currentShapeRow, currentShapeColumn)){
					currentShape.rotateRight();
				}
				break;
			case KeyEvent.VK_LEFT:
				currentShapeColumn--;
				if(!isValidShape(currentShape, currentShapeRow, currentShapeColumn)){
					currentShapeColumn++;
				}
				break;
			case KeyEvent.VK_RIGHT:
				currentShapeColumn++;
				if(!isValidShape(currentShape, currentShapeRow, currentShapeColumn)){
					currentShapeColumn--;
				}
				break;
			case KeyEvent.VK_DOWN:
				currentShapeRow++;
				if(!isValidShape(currentShape, currentShapeRow, currentShapeColumn)){
					currentShape = null;		
					currentShapeRow = 0;
				}
				break;
			case KeyEvent.VK_SPACE:
				if(!finishCurrentShape()){
					//game is over if we can't place current shape
					//clearBoard();
					timer.stop();
				}
				break;
			}
			
			
		}

		@Override
		public void keyReleased(KeyEvent e) {			
		}		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("TetrisWars");
		frame.setSize(200, 400);
		frame.add(new Game(WIDTH, HEIGHT));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
