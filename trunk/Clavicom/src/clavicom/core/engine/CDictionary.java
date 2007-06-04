/*-----------------------------------------------------------------------------+

			Filename			: CDictionary.java
			Creation date		: 4 juin 07
		
			Project				: Clavicom
			Package				: clavicom.core.engine

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

package clavicom.core.engine;

import java.util.HashMap;

import clavicom.core.profil.CDictionaryName;

public class CDictionary
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	HashMap<String, Integer> wordMap;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CDictionary( CDictionaryName dictionaryName )
	{
		wordMap = new HashMap<String, Integer>();
		
		// =====================================================================
		// Chargement du fichier de dictionaire
		// =====================================================================
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
