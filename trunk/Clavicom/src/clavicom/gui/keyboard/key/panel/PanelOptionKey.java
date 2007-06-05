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

package clavicom.gui.keyboard.key.panel;


import javax.swing.JPanel;
import clavicom.core.keygroup.CKey;
import clavicom.gui.language.UIString;

public class PanelOptionKey extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CKey key;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelOptionKey( CKey myKey )
	{
		key = myKey;
		
		
		// création des trois panels des couleurs et ajout
		PanelOptionColor panelColorClicked = new PanelOptionColor( key.GetColorClicked(), UIString.getUIString("LB_COLOR_CLICKED") );
		PanelOptionColor panelColorEntered = new PanelOptionColor( key.GetColorEntered(), UIString.getUIString("LB_COLOR_ENTERED") );
		PanelOptionColor panelColorNormal = new PanelOptionColor( key.GetColorNormal(), UIString.getUIString("LB_COLOR_NORMAL") );
		
		add( panelColorNormal );
		add( panelColorEntered );
		add( panelColorClicked );
	}

	//----------------------------------------------------------- METHODES --//
	
	

	//--------------------------------------------------- METHODES PRIVEES --//
}
