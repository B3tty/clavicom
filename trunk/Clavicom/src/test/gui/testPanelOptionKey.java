/*-----------------------------------------------------------------------------+

			Filename			: testPanelOptionKey.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: test.gui

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

package test.gui;

import java.awt.Color;

import javax.swing.JFrame;

import clavicom.core.keygroup.CColor;
import clavicom.gui.keyboard.key.panel.PanelOptionColor;

public class testPanelOptionKey
{
	public static void main(String[] args)
	{
		CColor color = new CColor( Color.green );
		//PanelOptionColor panelOptionColor = new PanelOptionColor( color, "coucou" );
		
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		//frame.add( panelOptionColor );
		
		frame.show();
	}
}
