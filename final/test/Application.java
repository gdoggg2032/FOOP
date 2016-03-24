package com.zetcode;

import java.awt.EventQueue;
import javax.swing.JFrame;


public class Application extends JFrame {

	public Application() {

		initUI();
	}

	private void initUI() {

		add(new Board());

		add(new Man(0, 0));
		//this is not work because Board() be coverd by Man() 
		//so we may need a instance like Graph and Graph.drawMan() or Graph.drawBoard() methods

		setSize(250, 200);

		setTitle("Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}    

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Application ex = new Application();
				ex.setVisible(true);
			}
			
		});
	}
}
