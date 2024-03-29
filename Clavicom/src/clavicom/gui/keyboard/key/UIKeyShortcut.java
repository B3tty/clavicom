/*-----------------------------------------------------------------------------+

			Filename			: UIKeyKeyboardKey.java
			Creation date		: 6 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key

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

package clavicom.gui.keyboard.key;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;

public class UIKeyShortcut extends UIKeyOneLevel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	CKeyShortcut coreKey;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyShortcut(CKeyShortcut myCoreKey)
	{
		// Appel à la mère
		super();
		
		coreKey = myCoreKey;
		addListeners();
	}
	
	//----------------------------------------------------------- METHODES --//	
	@Override
	public CKey getCoreKey()
	{
		return coreKey;
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
