/*-----------------------------------------------------------------------------+

			Filename			: PanelModificationTransparency.java
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

import clavicom.core.profil.CTransparency;

public class PanelModificationProfilTransparency extends PanelModificationProfil
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CTransparency transparency;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	public PanelModificationProfilTransparency(String title, CTransparency myTransparency)
	{
		super(title);
		
		transparency = myTransparency;
	}

	

	//----------------------------------------------------------- METHODES --//	
	
	@Override
	public int validateDataEntry()
	{
		// Si la gestion de la transparence a changé, on la change dans le profil
		
		return 0;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
