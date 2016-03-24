package mrfoops;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
* this class for man(player)
*/
public class Man {

	/** the BoardInfo object */
	private BoardInfo boardInfo;
	/** record the time pressing a key */
	public long pressTime;
	/** record the time releasing a key */
	public long releaseTime;
	/** record whether to move */
	public boolean checkMove;
	/** dx = the vector for moving x-axis*/
	private int dx;
	/** dy = the vector for moving y-axis*/
	private int dy;
	/** c = the position, and use a block as an unit */
	private Coordinate c;
	/** the image used for showing(drawing) the player object */
	private Image image;

	/** px = the position for x-axis, and use a pixel as an unit */
	private double px;
	/** py = the position for y-axis, and use a pixel as an unit */
	private double py;
	/** thr reference of Board */
	private Board board;

	public Man(Board b) {

		this.boardInfo = new BoardInfo();

		ImageIcon ii = new ImageIcon("./image/craft.png");
		this.image = ii.getImage();
		this.c = new Coordinate(0, 0);
		this.px = 0.0;
		this.py = 0.0;

		pressTime = 0;
		releaseTime = 0;
		checkMove = false;
		this.board = b;
	}




	/** move a step, and check if can move or not(obstacle or board bound) */
	public void move() {
		System.out.println("move: "+dx+" "+dy);

		/**  direction, default is false, 0:up, 1:left, 2:down, 3:right */
		boolean[] dir = new boolean[4];

		
		for(int i = 0; i < this.board.obstacleList.size(); i++){
			ArrayList<Coordinate> obsVols = this.board.obstacleList.get(i).getVolume();
			for(int j = 0; j < obsVols.size(); j++){
				int obsx = obsVols.get(j).x;
				int obsy = obsVols.get(j).y;
				dir[0] = dir[0]==false ? (obsx==this.c.x) & (obsy==this.c.y-1) : true;
				dir[1] = dir[1]==false ? (obsx==this.c.x-1) & (obsy==this.c.y) : true;
				dir[2] = dir[2]==false ? (obsx==this.c.x) & (obsy==this.c.y+1) : true;
				dir[3] = dir[3]==false ? (obsx==this.c.x+1) & (obsy==this.c.y) : true;
			}
			
			// int obsx = this.board.obstacleList.get(i).getX();
			// int obsy = this.board.obstacleList.get(i).getY();
			// dir[0] = dir[0]==false ? (obsx==this.c.x) & (obsy==this.c.y-1) : true;
			// dir[1] = dir[1]==false ? (obsx==this.c.x-1) & (obsy==this.c.y) : true;
			// dir[2] = dir[2]==false ? (obsx==this.c.x) & (obsy==this.c.y+1) : true;
			// dir[3] = dir[3]==false ? (obsx==this.c.x+1) & (obsy==this.c.y) : true;

		}
		System.out.println(dir[0]);
		System.out.println(dir[1]);
		System.out.println(dir[2]);
		System.out.println(dir[3]);

		
		// x += dx;
		// y += dy;
		if(this.c.x == 0 || dir[1])
			this.dx = this.dx>0 ? this.dx : 0;
		if(this.c.x == this.boardInfo.col - 1 || dir[3])
			this.dx = this.dx<0 ? this.dx : 0;
		if(this.c.y == 0 || dir[0])
			this.dy = this.dy>0 ? this.dy : 0;
		if(this.c.y == this.boardInfo.row - 1 || dir[2])
			this.dy = this.dy<0 ? this.dy : 0;
		this.c.x += this.dx;
		this.c.y += this.dy;
		checkMove = false;
		// System.out.printf("dw: %d\n",(int)dw);
		// System.out.printf("dh: %d\n",(int)dh);
	}


	public int getX() {
		return this.c.x;
	}

	public int getY() {
		return this.c.y;
	}

	public Coordinate getC(){
		return this.c;
	}

	public int getPX() {
		return this.boardInfo.objectShiftC.x + this.c.x * this.boardInfo.dw + this.boardInfo.oriC.x;
	}

	public int getPY() {
		return this.boardInfo.objectShiftC.y + this.c.y * this.boardInfo.dh + this.boardInfo.oriC.y;
	}

	public Image getImage() {
		return this.image;
	}

	/** print the object */
	public void draw(Board b, Graphics2D g2d){
		g2d.drawImage(this.getImage(), this.getPX(), this.getPY(), b); 
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		this.checkMove = true;
		this.pressTime = System.currentTimeMillis();
		System.out.println("Press time: "+this.pressTime);
		if (key == KeyEvent.VK_LEFT) {
			this.dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			this.dx = 1;
		}

		if (key == KeyEvent.VK_UP) {
			this.dy = -1;
		}

		if (key == KeyEvent.VK_DOWN) {
			this.dy = 1;
		}
		System.out.println(this.c.x + " " + this.c.y + ", "+this.dx+" "+this.dy);
		
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		
		this.releaseTime = System.currentTimeMillis();
		System.out.println("Release time: "+this.releaseTime);
		if (key == KeyEvent.VK_LEFT) {
			this.dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			this.dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			this.dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			this.dy = 0;
		}
	}
}
