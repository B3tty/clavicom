package touches;

import java.awt.AWTException;
import java.awt.AWTKeyStroke;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Keymap;

public class robotEtFocus 
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
		
		frame.setAlwaysOnTop(true);
		
		
		frame.setSize(200,200);
		JButton button = new JButton("C");
		button.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent arg0)
			{
				// mouvement souris
				//robot.mouseMove(0, 0);
				// =======================
				
				// simulation touche
//				KeyStroke s1 = KeyStroke.getKeyStroke(KeyEvent.VK_CIRCUMFLEX, KeyEvent.SHIFT_DOWN_MASK, false);
//
//				System.out.println( KeyEvent.VK_CIRCUMFLEX ); 
//				
//
				int e1 = KeyEvent.VK_2;
				int e2 = KeyEvent.VK_CONTROL;
				int e3 = KeyEvent.VK_ALT;
				
				//KeyStroke s1 = KeyStroke.getKeyStroke(KeyEvent.VK_4, KeyEvent.ALT_GRAPH_DOWN_MASK, false);
//				robot.keyPress( e2 );
//				robot.keyPress( e3 );
				robot.keyPress( e1 );
				robot.keyRelease( e1 );
//				robot.keyRelease( e2 );
//				robot.keyRelease( e3 );
				
				// =========================
				
				
//				StateMachine.State.KeyPress('é');
			}
			
		});
		
		frame.addKeyListener(new KeyListener(){

			public void keyPressed(KeyEvent arg0)
			{
				System.out.println(arg0);
				
			}

			public void keyReleased(KeyEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			public void keyTyped(KeyEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}
			
		
		});
		
//		JButton button2 = new JButton("A");
//		button2.addActionListener(new ActionListener()
//		{
//
//			public void actionPerformed(ActionEvent arg0)
//			{
//				// mouvement souris
//				//robot.mouseMove(0, 0);
//				// =======================
//				
//				// simulation touche
//				robot.keyPress( KeyEvent.VK_CAPS_LOCK );
//				// =========================
//			}
//			aaaaaaaaaaaaaaaqqqqqqqaaaaaa
//		});

		JPanel panel = new JPanel(new GridLayout());
		panel.add( new JTextField() );
		panel.add( button );
//		panel.add( button2 );
		
		frame.add(panel);
		frame.show();
		
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();

	}

}
