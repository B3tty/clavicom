package touches;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class Raccourci 
{
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
	
		try
		{
			new Robot();
		}
		catch ( AWTException e )
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		JFrame frame = new JFrame();

		// pour ne pas prendre le focus
		// ===================================
		
		
		frame.setSize(100,100);
		JButton button = new JButton("Action");
		
	    // Create some keystrokes and bind them all to the same action
	    
	    // Add the action to the component
		button.getInputMap().put(KeyStroke.getKeyStroke('5'), "doAnAction");
		Action doAnAction = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
			System.out.println("salut");
			// call PanelA's methodA or something like that
		    }
		};
		button.getActionMap().put("doAnAction", doAnAction);
				

		JPanel panel = new JPanel(new GridLayout());
		
		panel.add( new JTextField() );
		panel.add( button );
		
		frame.add(panel);
		frame.setVisible( true );

	}

}
