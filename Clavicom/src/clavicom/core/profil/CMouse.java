/*-----------------------------------------------------------------------------+

			Filename			: CMouse.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.mouse

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

package clavicom.core.profil;

import java.util.ArrayList;
import java.util.List;

import clavicom.core.keygroup.mouse.CMouseKey;


public class CMouse
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	List<CMouseKey> mouseKeyList;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouse()
	{
		mouseKeyList = new ArrayList<CMouseKey>();
	}

	//----------------------------------------------------------- METHODES --//
	public void AddMouseKey( CMouseKey mouseKey )
	{
		mouseKeyList.add( mouseKey );
	}
	
	public CMouseKey GetMouseKey( int index )
	{
		return mouseKeyList.get( index );
	}
	
	public int size(){return mouseKeyList.size();}
	
	
	public static CMouse CreateMouse()
	{
		// Construction d'un cmouse avec toutes les touches et 
		// tous les cliques
		
		
		return new CMouse();
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
