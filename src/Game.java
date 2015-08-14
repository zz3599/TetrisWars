import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		this.boardWidth = width; 
		this.boardHeight = height;
		this.board = new Shape[boardHeight][boardWidth];
		this.timer = new Timer(SLEEP_TICK, this);
		this.timer.start();		
		for (int i = 0; i < boardHeight; i++){
			for(int j = 0; j < boardWidth; j++){
				this.board[i][j] = new Shape(Shape.Tetrominoes.NoShape); 
			}
		}
	}
	
	public synchronized Shape getCurrentShape(){
		if(this.currentShape == null){
			this.currentShape = Shape.generateRandomShape();
			this.currentShapeRow = 0;
			this.currentShapeColumn = this.boardWidth/2;
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

	@Override
	public void actionPerformed(ActionEvent e) {
		Shape currentShape = this.getCurrentShape();
		if (-currentShape.getMinRow() + this.currentShapeRow + 1 >= this.boardHeight){
			//cant move anymore, make next turn generate a new shape 
			this.currentShape = null;				
		} else {
			for(int i = 0; i < this.currentShape.getCoordinates().length; i++){
				int relativeRow = this.currentShape.getCoordinates()[i][0];
				int relativeColumn = this.currentShape.getCoordinates()[i][1];
				//this.board[-relativeRow + this.currentShapeRow+1][relativeColumn + this.currentShapeColumn]
				//		= this.currentShape;				
			}
			this.currentShapeRow++;
		}		
		repaint();
	}
	
	private int squareWidth(){
		return (int)this.getSize().getWidth() / this.boardWidth;
	}
	
	private int squareHeight(){
		return (int)this.getSize().getHeight() / this.boardHeight;
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("TetrisWars");
		frame.setSize(200, 400);
		frame.add(new Game(WIDTH, HEIGHT));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
