package fenetres;

import javax.swing.JFrame;

public class alwaysVisible
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setSize(200,200);
		frame.show();

	}

}
