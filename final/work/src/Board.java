package mrfoops;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/** board class*/
public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private Man man;
	/** store obstacles */
	protected ArrayList<Ball> obstacleList;
	private Enemy enemy;
	/** delay for timer checking*/
	private final int DELAY = 1;
	/** counting for man moving time */
	private long count; //for man

	private BoardInfo boardInfo;
	/** whether hit or not */
	private boolean hit;

	public Board() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.CYAN);

		this.count = 0;

		this.boardInfo = new BoardInfo();

		this.hit = false;

		man = new Man(this);

		obstacleList = new ArrayList<Ball>();

		enemy = new Enemy(this, 250);

		timer = new Timer(DELAY, this);
		timer.start();        
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBoard(g);
		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	/** draw the board, including background and blocks and show whether hit or not */
	private void drawBoard(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh
			= new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);
		g2d.setColor(Color.WHITE);
		
		//draw Board
		Rectangle2D[] r = new Rectangle2D[64];
		for(int i=0; i<64; i++) {
			r[i] = new Rectangle2D.Double((double)this.boardInfo.oriC.x + (double)(i%8)*(double)this.boardInfo.dw, 
				(double)this.boardInfo.oriC.y + (double)(i/8)*(double)this.boardInfo.dh, 
				(double)this.boardInfo.dw, (double)this.boardInfo.dh);
			g2d.setStroke(new BasicStroke(2));

			g2d.setColor(Color.WHITE);
			// fill = fill in white
			g2d.fill(r[i]);

			g2d.setColor(Color.BLACK);
			// draw = only rectangles
			g2d.draw(r[i]);
		}

		// show if hit
		if(hit){
			g2d.setColor(Color.red);
		    g2d.drawString("HIT!!", 30, 30);

		}
		
		
	}
	/** draw the objects, including man and obstacles*/
	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		// g2d.drawImage(man.getImage(), man.getX(), man.getY(), this); 
		// draw = concrete x, y
		man.draw(this, g2d);
		for(int i = 0; i < obstacleList.size(); i++){
			// testdraw = draw by pixel, not concrete
			obstacleList.get(i).draw(this, g2d);
		}       
	}
	/** 
	* check if man hit an obstacle. 
	* @return true if a hit happens; otherwise false.
	*/
	private boolean checkHit(){
		// int posx = man.getX();
		// int posy = man.getY();
		Coordinate posC = man.getC();

		for(int i = 0; i < obstacleList.size(); i++){
			// int obsx = obstacleList.get(i).getX();
			// int obsy = obstacleList.get(i).getY();
			ArrayList<Coordinate> obsVols = obstacleList.get(i).getVolume();
			for(int j = 0; j < obsVols.size(); j++){
				if(posC.equals(obsVols.get(j))){
					System.out.printf("HIT!!!!!");
					return true;
				}
			}
			// if(posx == obsx && posy == obsy){
			// 	System.out.printf("HIT!!!!!");
			// 	return true;
			// }
		}
		return false;
	}
	/** 
	* timer call this.
	* count is the timer frame counts
	* for man's moving, each 10 count moves 1 step, and erase the hit! record.
	* for obstacle, each count move v, v is defined in obstacle class
	* for each count, check if hit.
	* for each count, call enemy to throw ball, the enemy accumulate the count until f(defined in enemy class) 
	*/
	@Override
	public void actionPerformed(ActionEvent e) {
		this.count += 1;

		if(this.count>10){
			if(man.checkMove) {
				man.move();
				// tmp: if move again, as a new game
				this.hit = false;
				repaint();
			}
				
			this.count = 0;
		}

		// refresh and move obstacles by timer frequency
		for(int i = 0; i < obstacleList.size(); i++){
				obstacleList.get(i).move();
		}
		if(checkHit()){
			this.hit = true;
		}
		obstacleList.addAll(enemy.throwBalls());
		
		repaint();
	}

	private class TAdapter extends KeyAdapter {

		@Override
			public void keyReleased(KeyEvent e) {
				man.keyReleased(e);
			}

		@Override
			public void keyPressed(KeyEvent e) {
				man.keyPressed(e);
			}
	}
}
