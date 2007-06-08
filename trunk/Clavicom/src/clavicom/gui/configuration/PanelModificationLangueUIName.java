/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationLangueUIName.java
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

import clavicom.core.profil.CLangueUIName;

public class PanelModificationLangueUIName extends PanelModification
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CLangueUIName langueUIName;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public PanelModificationLangueUIName(String title, CLangueUIName myLangueUIName)
	{
		super(title);
		
		langueUIName = myLangueUIName;
	}

	

	//----------------------------------------------------------- METHODES --//
	
	@Override
	public int validateDataEntry()
	{
		// Si la langue de l'UI à changé, on la change le nom du fichier UI
		
		return 0;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
