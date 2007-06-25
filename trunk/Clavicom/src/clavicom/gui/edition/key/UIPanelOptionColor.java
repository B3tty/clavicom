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

package clavicom.gui.edition.key;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorKeyEnum;

public class UIPanelOptionColor extends JPanel implements ActionListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CKey key;
	JButton colorButton;
	JLabel lColor;
	TColorKeyEnum colorEnum;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIPanelOptionColor(  )
	{
		lColor = new JLabel();
		add(lColor);
		
		colorButton = new JButton();
		colorButton.setPreferredSize( new Dimension(30,20) );		
		add( colorButton );
		
		colorButton.addActionListener( this );
	}
	
	public void setValues(CKey myKey, TColorKeyEnum myColorEnum)
	{
		key = myKey;
		colorEnum = myColorEnum;	
		lColor.setText(colorEnum.toString());
		
		colorButton.setBackground( key.getColor( colorEnum ) );
	}

	public void actionPerformed(ActionEvent arg0)
	{			
		Color newColor = JColorChooser.showDialog( UIPanelOptionColor.this, UIString.getUIString("LB_CHOOSE_COLOR"), key.getColor( colorEnum ) );

		if( newColor !=  null )
		{
			if( newColor != key.getColor( colorEnum ) )
			{
				// la couleur à changé
				key.setColor( newColor, colorEnum );

				colorButton.setBackground( newColor );
			}
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
