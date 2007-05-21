/*-----------------------------------------------------------------------------+

			Filename			: CCommandSet.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.commandSet

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

package clavicom.core.keyboard.commandSet;

import java.util.HashMap;

public class CCommandSet 
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	HashMap< String, CSection > sectionsList;	// liste des sections des commandSet

	//------------------------------------------------------ CONSTRUCTEURS --//	
	CCommandSet( String CommandSetfilePath )
	{
		// Initialisation des attributs
		sectionsList = new HashMap<String, CSection>();
		
		// Chargement du fichier XML
		LoadCommandSetFile ( CommandSetfilePath );
	}

	//----------------------------------------------------------- METHODES --//	
	public CSection GetSection( String name )
	{
		return sectionsList.get( name );
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	private void LoadCommandSetFile ( String CommandSetfilePath )
	{
		// =======================================================
		//	Chargement du fichier XML
		// =======================================================
	}
	
	private void AddSection( CSection section )
	{
		sectionsList.put( section.name, section);
	}
}
