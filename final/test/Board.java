package com.zetcode;

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


public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private Craft craft;
	private final int DELAY = 1;
	private int count = 0;

	private double dw;
	private double dh;

	public Board() {

		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.WHITE);

		double w = 400;
		double h = 320;
		// 8*8
		this.dw = (w/8);
		this.dh = (h/8);
		System.out.printf("dw: %f\n",this.dw);
		System.out.printf("dh: %f\n",this.dh);

		craft = new Craft(this.dw, this.dh, 8, 8);

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

	private void drawBoard(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh
			= new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);
		// Dimension size = getSize();
		// double w = size.getWidth();
		// double h = size.getHeight();
		//why use this, h is not 320????
		double w = 400;
		double h = 320;

		double dw = w/8;
		double dh = h/8;
		// System.out.printf("dw: %d\n",(int)dw);
		// System.out.printf("dh: %d\n",(int)dh);
		// System.out.printf("w: %d\n",(int)w);
		// System.out.printf("h: %d\n",(int)h);
		Rectangle2D[] r = new Rectangle2D[64];
		for(int i=0; i<64; i++) {
			r[i] = new Rectangle2D.Double((i%8)*dw, (i/8)*dh, dw, dh);
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.black);
			g2d.draw(r[i]);
		}
		
		
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(craft.getImage(), craft.getX(), craft.getY(), this);        
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.count += 1;
		if(this.count>10){
			if(craft.checkMove) {
				System.out.println("Gdog is handsome. "+craft.checkMove);
				craft.move();
				repaint();
			}
				
			this.count = 0;
		}
		repaint();
	}

	private class TAdapter extends KeyAdapter {

		@Override
			public void keyReleased(KeyEvent e) {
				craft.keyReleased(e);
			}

		@Override
			public void keyPressed(KeyEvent e) {
				craft.keyPressed(e);
			}
	}
}
