/*-----------------------------------------------------------------------------+

			Filename			: CCommandEngine.java
			Creation date		: 30 mai 07
		
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


import java.awt.AWTException;
import java.awt.Robot;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.keygroup.mouse.CMouseKeyMove;
import clavicom.core.listener.onClicMouseMoveListener;
import clavicom.core.message.CMessageEngine;
import clavicom.gui.language.UIString;

public class CMouseMoveEngine implements onClicMouseMoveListener
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//

	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CMouseMoveEngine( CMouse mouse )
	{
		
		

		// =============================================================
		// Abonnement aux listener
		// =============================================================
		// -- a faire
	}

	//----------------------------------------------------------- METHODES --//
	
	

	public void onClicMouseMove( CMouseKeyMove keyMove )
	{
		Robot robot = null;
		try
		{
			robot = new Robot();
		}
		catch (AWTException e)
		{
			CMessageEngine.newError( UIString.getUIString(""), e.getMessage());
		}
	}
	
	


	//--------------------------------------------------- METHODES PRIVEES --//
	
	

	
}
