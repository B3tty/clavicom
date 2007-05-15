package TestRobot;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class mainTestRobot 
{

	private static Robot robot;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
	
		try
		{
			robot = new Robot();
		}
		catch ( AWTException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JFrame frame = new JFrame();

		// pour ne pas prendre le focus
		frame.setFocusableWindowState(false);
		// ===================================
		
		
		frame.setSize(100,100);
		JButton button = new JButton("Action");
		button.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				// mouvement souris
				//robot.mouseMove(0, 0);
				// =======================
				
				// simulation touche
				robot.keyPress( KeyEvent.VK_B );
				// =========================
			}
			
		});

		JPanel panel = new JPanel(new GridLayout());
		panel.add( new JTextField() );
		panel.add( button );
		
		frame.add(panel);
		frame.show();
		
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	}

}
