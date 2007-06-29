/*-----------------------------------------------------------------------------+

			Filename			: TColorPanel.java
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


import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class TColorPanel extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public TColorPanel()
	{
		setOpaque(false);
		
		setPreferredSize( new Dimension( 25, 25 ) );
		
		setBorder(BorderFactory.createLineBorder( Color.BLACK ));
		
		setCursor( new Cursor( Cursor.HAND_CURSOR ) );
	}

	

	//----------------------------------------------------------- METHODES --//
	
	public void paintComponent(Graphics myGraphic)
	{
		
		// Appel du père
		super.paintComponent(myGraphic);

		// Récupération du Graphics2D
		Graphics2D g2 = (Graphics2D) myGraphic;
		
		g2.setColor( getBackground() );
		g2.fillRect(0, 0, getWidth(), getHeight());

	}


	//--------------------------------------------------- METHODES PRIVEES --//
}
