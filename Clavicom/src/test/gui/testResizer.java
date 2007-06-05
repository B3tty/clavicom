package test.gui;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import clavicom.gui.resizer.UIJResizer;

/**
 * @author Santhosh Kumar T
 */
public class testResizer
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(null);
		
		// Création du bouton
		JButton bt = new JButton();
		bt.setSize(new Dimension(100,200));
		bt.setText("rien");
		
		Dimension bounds = bt.getPreferredSize();
		UIJResizer resizer = new UIJResizer(bt);
		resizer.setBounds(0,0, bounds.width,bounds.height);
		
		panel.add(resizer);
		frame.getContentPane().add(panel);
		
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
