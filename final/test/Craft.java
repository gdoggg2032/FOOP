package com.zetcode;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Craft {


	private int dw;
	private int dh;

	private int row;
	private int col;
	public long pressTime;
	public long releaseTime;
	public boolean checkMove;
	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;

	public Craft(double dw, double dh, int row, int col) {

		this.dw = (int)dw;
		this.dh = (int)dh;
		this.row = row;
		this.col = col;
		initCraft();
		pressTime = 0;
		releaseTime = 0;
		checkMove = false;
	}

	private void initCraft() {

		ImageIcon ii = new ImageIcon("./images/craft.png");
		image = ii.getImage();

		x = 0;
		y = 0;        
	}


	public void move() {
		System.out.println("move: "+dx+" "+dy);
		
		// x += dx;
		// y += dy;
		if(x == 0)
			dx = dx>0 ? dx : 0;
		if(x == col - 1)
			dx = dx<0 ? dx : 0;
		if(y == 0)
			dy = dy>0 ? dy : 0;
		if(y == row - 1)
			dy = dy<0 ? dy : 0;
		x += dx;
		y += dy;
		checkMove = false;
		// System.out.printf("dw: %d\n",(int)dw);
		// System.out.printf("dh: %d\n",(int)dh);
	}

	public int getX() {
		return 25 + (x * dw);
	}

	public int getY() {
		return 15 + y * dh;
	}

	public Image getImage() {
		return image;
	}

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();
		checkMove = true;
		this.pressTime = System.currentTimeMillis();
		System.out.println("Press time: "+this.pressTime);
		if (key == KeyEvent.VK_LEFT) {
			dx = -1;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 1;
		}

		if (key == KeyEvent.VK_UP) {
			dy = -1;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 1;
		}
		System.out.println(x + " " + y + ", "+dx+" "+dy);
		
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();
		
		this.releaseTime = System.currentTimeMillis();
		System.out.println("Release time: "+this.releaseTime);
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

		if (key == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (key == KeyEvent.VK_DOWN) {
			dy = 0;
		}
	}
}
