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

public class Man extends JPanel {

	private int x = 0;
	private int y = 0;

	public Man(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			drawMan(g);
		}

	private void drawMan(Graphics g) {

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

		Ellipse2D e = new Ellipse2D.Double(0, 0, 80, 80);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.gray);
		g2d.draw(e);

		Rectangle2D r = new Rectangle2D.Double(40, 80, 0, 40);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.gray);
		g2d.draw(r);
		
		
		Rectangle2D h1 = new Rectangle2D.Double(0, 0, 0, 40);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.gray);
		AffineTransform at1 = AffineTransform.getTranslateInstance(40, 80);
		at1.rotate(Math.toRadians(45));
		AffineTransform at2 = AffineTransform.getTranslateInstance(40, 80);
		at2.rotate(Math.toRadians(315));
		g2d.draw(at1.createTransformedShape(h1));	
		g2d.draw(at2.createTransformedShape(h1));
		AffineTransform at3 = AffineTransform.getTranslateInstance(40, 120);
		at3.rotate(Math.toRadians(45));
		AffineTransform at4 = AffineTransform.getTranslateInstance(40, 120);
		at4.rotate(Math.toRadians(315));
		g2d.draw(at3.createTransformedShape(h1));	
		g2d.draw(at4.createTransformedShape(h1));
		/*for (double deg = 0; deg < 360; deg += 5) {
			AffineTransform at
				= AffineTransform.getTranslateInstance(w/2, h/2);
			at.rotate(Math.toRadians(deg));
			g2d.draw(at.createTransformedShape(e));
		}*/
	}
}

