package mrfoops;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

/** the enemy class, throwing obstacle to the board */
public class Enemy{


	private Board board;

	protected ArrayList<Ball> balllist;
	private Random ran = new Random();

	private BoardInfo boardInfo;
	
	/** frequency for throw balls */
	private double f; // v = frequecy of throw ball
	/** count of timer frame */
	private double count;


	public Enemy(Board b, double f){
		this.balllist = new ArrayList<Ball>();
		this.boardInfo = new BoardInfo();
		this.board = b;
		this.f = f;
		this.count = 0;
	}
	/**
	* throw balls(or other obstacles)
	* enemy accumulate counts
	* if no enough counts, not to throw balls.
	* @return balls arraylist
	*/
	public ArrayList<Ball> throwBalls(){
		
		ArrayList<Ball> newBalls = new ArrayList<Ball>();
		this.count += 1;
		if(count%f!=0)
			return newBalls;

		int dir = ran.nextInt(4); //0, 1, 2, 3

		int startx;
		int starty;
		if(dir==0 || dir==1){
			//dir: -1, 0 or 1, 0
			startx = 9 - 10*dir; // -1 or 9
			starty = ran.nextInt(8);
			newBalls.add(new Ball(new Coordinate(startx, starty), dir, 1.0/50, this, this.board));
			starty = ran.nextInt(8);
			newBalls.add(new Ball(new Coordinate(startx, starty), dir, 1.0/50, this, this.board));
			starty = ran.nextInt(8);
			newBalls.add(new Ball(new Coordinate(startx, starty), dir, 1.0/50, this, this.board));
		}else{
			starty = 9 - 10*(dir%2);
			startx = ran.nextInt(8);
			newBalls.add(new Ball(new Coordinate(startx, starty), dir, 1.0/50, this, this.board));
			startx = ran.nextInt(8);
			newBalls.add(new Ball(new Coordinate(startx, starty), dir, 1.0/50, this, this.board));
			startx = ran.nextInt(8);
			newBalls.add(new Ball(new Coordinate(startx, starty), dir, 1.0/50, this, this.board));
		}
		this.balllist.addAll(newBalls);
		return newBalls;
	}
}