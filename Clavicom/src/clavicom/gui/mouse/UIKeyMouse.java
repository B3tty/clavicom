/*-----------------------------------------------------------------------------+

			Filename			: UIKeyMouse.java
			Creation date		: 7 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.mouse

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

package clavicom.gui.mouse;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.mouse.CMouseKey;
import clavicom.gui.keyboard.key.UIKey;

public class UIKeyMouse extends UIKey
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CMouseKey mouseKey;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public UIKeyMouse( CMouseKey myMouseKey )
	{
		mouseKey = myMouseKey;
	}

	//----------------------------------------------------------- METHODES --//	
	@Override
	public CKey getCoreKey()
	{
		return mouseKey;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	@Override
	protected void addListeners()
	{
	}

	@Override
	protected String getCaption()
	{
		// TODO Auto-generated method stub
		return mouseKey.getCaption();
	}

	@Override
	protected void onBoundsChanged()
	{
		// Rien à faire
	}
}
