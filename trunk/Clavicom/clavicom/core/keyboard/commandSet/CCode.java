/*-----------------------------------------------------------------------------+

			Filename			: CCode.java
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

import java.awt.event.KeyEvent;

import clavicom.tools.CKeyAction;

public class CCode
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	KeyEvent keyEvent;		//keyEvent de la touche du clavier
	CKeyAction keyAction;	// Action à effectuer (PRESSED ou RELEASED)

	//------------------------------------------------------ CONSTRUCTEURS --//	
	CCode( KeyEvent myKeyEvent, CKeyAction myKeyAction )
	{
		keyEvent = myKeyEvent;
		keyAction = myKeyAction;
	}

	//----------------------------------------------------------- METHODES --//	
	public KeyEvent GetKeyEvent(){return keyEvent;}
	public CKeyAction GetKeyAction(){return keyAction;}

	//--------------------------------------------------- METHODES PRIVEES --//
}
