/*-----------------------------------------------------------------------------+

			Filename			: TPicturePanel.java
			Creation date		: 29 juin 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TPicturePanel extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	ImageIcon image;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public TPicturePanel( String imagePath )
	{
		image = TSwingUtils.getImage( imagePath );
		
	}

	//----------------------------------------------------------- METHODES --//

	
	@Override
	public void paintComponent(Graphics g)
	{
		g.drawImage( TSwingUtils.scaleImage(image, getWidth(), -1).getImage(), 0, 0, this);
	}


	//--------------------------------------------------- METHODES PRIVEES --//
}
