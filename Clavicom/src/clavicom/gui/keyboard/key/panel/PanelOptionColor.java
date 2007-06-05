/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionColor.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key.option

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

package clavicom.gui.keyboard.key.panel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clavicom.core.keygroup.CColor;

public class PanelOptionColor extends JPanel implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CColor ccolor;
	JButton colorButton;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelOptionColor( CColor myColor, String caption )
	{
		ccolor = myColor;
		
		add( new JLabel(caption) );
		
		colorButton = new JButton();
		colorButton.setBackground( ccolor.GetColor() );
		
		add( colorButton );
		
		colorButton.addActionListener( this );
	
	}

	public void actionPerformed(ActionEvent arg0)
	{
		Color newColor = JColorChooser.showDialog( this, "Choix de la couleur", ccolor.GetColor() );
		
		if( newColor != null )
		{
			if( newColor != ccolor.GetColor() )
			{
				// la couleur à changé
				ccolor.setColor( newColor );
				
				colorButton.setBackground( newColor );
			}
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
