/*-----------------------------------------------------------------------------+

			Filename			: PanelOptionKey.java
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


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;
import clavicom.tools.TColorKeyEnum;

public class UIPanelOptionKey extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKey key;
	UIPanelOptionColor panelColorClicked;
	UIPanelOptionColor panelColorEntered;
	UIPanelOptionColor panelColorNormal;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIPanelOptionKey( )
	{
		setLayout( new BorderLayout() );
		
		JPanel panelGlobal = new JPanel();
		
		JPanel colors = new JPanel();
		
		// création des trois panels des couleurs et ajout
		panelColorClicked = new UIPanelOptionColor();
		panelColorEntered = new UIPanelOptionColor();
		panelColorNormal = new UIPanelOptionColor();
		
		colors.add( panelColorNormal );
		colors.add( panelColorEntered );
		colors.add( panelColorClicked );
		
		
		
		colors.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder( Color.BLACK ), 
				UIString.getUIString("LB_COLOR_COLOR_MANAGEMENT")) );
		
		panelGlobal.add(colors);

		add( panelGlobal, BorderLayout.NORTH );
	}

	//----------------------------------------------------------- METHODES --//
	public void setValuesKey( CKey myKey)
	{
		// Affectation de la key
		key = myKey;		
		
		panelColorClicked.setValues( key, TColorKeyEnum.PRESSED );
		panelColorEntered.setValues( key, TColorKeyEnum.ENTERED );
		panelColorNormal.setValues( key, TColorKeyEnum.NORMAL );
	}
	

	//--------------------------------------------------- METHODES PRIVEES --//
}