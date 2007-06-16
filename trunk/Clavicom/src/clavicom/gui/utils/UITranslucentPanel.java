/*-----------------------------------------------------------------------------+

			Filename			: UITranslucentPanel.java
			Creation date		: 16 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.utils

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

package clavicom.gui.utils;

import java.awt.Graphics;

import javax.swing.JPanel;

public class UITranslucentPanel extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UITranslucentPanel()
	{	
		super();
		setOpaque(false);
	}
	//----------------------------------------------------------- METHODES --//	
	@Override
	protected void paintComponent(Graphics myGraphic)
	{
		//setBackground(new Color(0,0,0,0));
		// Appel du père
		
		super.paintComponent(myGraphic);
	}


	//--------------------------------------------------- METHODES PRIVEES --//
}
