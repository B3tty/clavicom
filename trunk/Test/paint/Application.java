/*-----------------------------------------------------------------------------+

			Filename			: Application.java
			Creation date		: 4 juil. 07
		
			Project				: Test
			Package				: paint

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2007) Centre ICOM'

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

package paint;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Application
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		JFrame mainFrame = new JFrame();
		
		PanelParent panelParent = new PanelParent();		
		panelParent.setBackground(Color.RED);
		
		
		PanelTest panel1 = new PanelTest();
		panel1.setPreferredSize(new Dimension(50,50));
		
		panelParent.add(panel1);		
		mainFrame.add(panelParent);
		
		mainFrame.setSize(new Dimension(200,200));
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainFrame.setVisible(true);
	}

}
