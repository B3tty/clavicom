/*-----------------------------------------------------------------------------+

			Filename			: TestKeyEvent.java
			Creation date		: 01-Jun-08
		
			Project				: Clavicom
			Package				: test.core

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2008) Centre ICOM'

							-------------------------

	This program is free software. You can redistribute it and/or modify it 
 	under the terms of the GNU Lesser General Public License as published by 
	the Free Software Foundation. Either version 2.1 of the License, or (at your 
    option) any later version.

	This program is distributed in the hope that it will be useful, but WITHOUT 
	ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
	FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
    more details.

+-----------------------------------------------------------------------------*/

package test.core;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

public class TestKeyEvent
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Robot r = null;
		try
		{
			r = new Robot();
		}
		catch (AWTException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		r.keyPress(KeyEvent.VK_SHIFT);
		r.keyPress(KeyEvent.VK_BACK_SLASH);
		r.keyRelease(KeyEvent.VK_BACK_SLASH);
		r.keyRelease(KeyEvent.VK_SHIFT);
		
//		JFrame frame = new JFrame();
//		frame.setVisible(true);
//		
//		frame.addKeyListener(new KeyListener()
//		{
//			public void keyPressed(KeyEvent arg0) {
//				System.out.println(arg0);
//			}
//
//			@Override
//			public void keyReleased(KeyEvent arg0)
//			{
//				// TODO Auto-generated method stub
//				
//			}
//
//			@Override
//			public void keyTyped(KeyEvent arg0)
//			{
//				
//			};
//		}
//		);
		
		
		
	}
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
