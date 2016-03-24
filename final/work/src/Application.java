package mrfoops;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
* class for app entrance
*/
public class Application extends JFrame {
	/**
	* Add Board and setSize for GUI window(5:4).
	* Notice that the size is (500, 422): (10*50, 10*40 + 22).
	* There is a 10*10 blocks window, and reserve 22 for the title. 
	*/
	public Application() {

		add(new Board());

		// 400 * 320, 50*40
		setSize(500, 422);
		setResizable(false);

		setTitle("Mr. Foops");
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
