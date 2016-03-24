package com.zetcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Board extends JPanel {


	private int haha = 0;
	public Board(){
		this.haha = 1;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawBoard(g);
	}
	

	private void drawBoard(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh
			= new RenderingHints(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g2d.setRenderingHints(rh);
		Dimension size = getSize();
		double w = size.getWidth();
		double h = size.getHeight();

		Rectangle2D[] r = new Rectangle2D[64];
		for(int i=0; i<64; i++) {
			r[i] = new Rectangle2D.Double((i%8)*w/8, (i/8)*h/8, w/8, h/8);
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.black);
			g2d.draw(r[i]);
		}
		
	}
}

