/*-----------------------------------------------------------------------------+

			Filename			: PanelModification.java
			Creation date		: 8 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.configuration

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

package clavicom.gui.configuration;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public abstract class PanelModificationProfil extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//

	//------------------------------------------------------ CONSTRUCTEURS --//
	public PanelModificationProfil( String title )
	{
		setBorder( 
				BorderFactory.createTitledBorder( 
						BorderFactory.createLineBorder( Color.BLACK ), 
						title ) );
	}

	//----------------------------------------------------------- METHODES --//	
	
	/**
	 * mettre les données des composants dans les variable du profil
	 * Retour : 
	 * 		* 0 si rien n'a été changé
	 * 		* 1 si quelque chose a changé
	 */
	public abstract int validateDataEntry();

	//--------------------------------------------------- METHODES PRIVEES --//
}
