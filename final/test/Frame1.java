package proba;

import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import javax.swing.JButton; 
import javax.swing.JFrame;

public class Frame1 extends JFrame{
	JFrame Frame = new JFrame();
	JButton Button1 = new JButton();


	public Frame1()
	{
	    super("The title 1");


	    Frame = new JFrame();
	    Button1 = new JButton();
	    Frame.add(Button1);

	    thehandler handler = new thehandler();
	    Button1.addActionListener(handler);


	}


	private class thehandler implements ActionListener
	{

	    public void actionPerformed(ActionEvent event)
	    {
	        if(event.getSource()==Button1)
	        {
	            Frejm2 frejm = new Frejm2();
	            frejm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            frejm.setVisible(true);
	        }
	    }


	}
}