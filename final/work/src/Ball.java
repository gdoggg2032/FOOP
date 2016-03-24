package mrfoops;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.lang.Math;
import java.util.ArrayList;


/** ball class */
public class Ball {



	private Board board;
	private Enemy enemy;

	private BoardInfo boardInfo;

	private int dx;
	private int dy;
	private Coordinate c;
	private double px;
	private double py;

	/** v for moving amount in a timer frame*/
	private double v;

	private Image image;
	/** direction, 0 to left, 1 to right, 2 to up, 3 to down*/
	private int dir;

	private ArrayList<Coordinate> volume;


	/** 
	* @param startC initial position, block-unit
	*/
	public Ball(Coordinate startC, int dir, double v, Enemy e, Board b) { // for obstacle's test

		this.boardInfo = new BoardInfo();

		ImageIcon ii = new ImageIcon("./image/craft.png");
		this.image = ii.getImage();

		this.c = new Coordinate(startC.x, startC.y);
		this.px = (double)startC.x;
		this.py = (double)startC.y;  
		
		this.dir = dir;
		this.v = v;

		this.enemy = e;
		this.board = b;

		this.volume = new ArrayList<Coordinate>();

	}

	/** remove self from enemy and board */
	private void suicide(){

		this.enemy.balllist.remove(this);
		this.board.obstacleList.remove(this);
	}
	/** get the volume(blocks) of this obstacle */
	public ArrayList<Coordinate> getVolume(){
		this.volume.clear();
		this.volume.add(new Coordinate(this.c.x, this.c.y));
		return this.volume;
	}

	/** to move, if exceed the range, suicide */
	public void move(){
		// x += (1-this.dir/2)*((this.dir%2)*2-1);
		// y += (this.dir/2)*((this.dir%2)*2-1);
		this.px += (double)((1-this.dir/2)*((this.dir%2)*2-1)) * this.v;
		this.py += (double)((this.dir/2)*((this.dir%2)*2-1)) * this.v;
		this.c.x = (int)Math.round(this.px);
		this.c.y = (int)Math.round(this.py);


		if(this.getX()<-2 || this.getY() <-2 || this.getY() > (this.boardInfo.row+2) || this.getX() > (this.boardInfo.col+2)){

			System.out.printf(">>>>>>%d/%d, %d/%d\n", this.c.x, this.boardInfo.col, this.c.y, this.boardInfo.row);
			System.out.printf("before suicide: obstacleList.size(): %d\n", this.board.obstacleList.size());
			this.suicide();
			System.out.printf("after suicide: obstacleList.size(): %d\n", this.board.obstacleList.size());
		}
		
	}

	public int getX() {
		//return 25 + (x * dw) + this.orix;
		return this.c.x;
	}

	public int getY() {
		//return 15 + y * dh + this.oriy;
		return this.c.y;
	}

	public Coordinate getC(){
		return this.c;
	}

	public int getPX() {
		return this.boardInfo.objectShiftC.x + (int)(this.px * this.boardInfo.dw) + this.boardInfo.oriC.x;
	}

	public int getPY() {
		return this.boardInfo.objectShiftC.y + (int)(this.py * this.boardInfo.dh) + this.boardInfo.oriC.y;
	}

	public Image getImage() {
		return this.image;
	}

	public void draw(Board b, Graphics2D g2d){
		g2d.drawImage(this.getImage(), this.getPX(), this.getPY(), b); 
	}

}
