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
import org.jdom.Attribute;
import org.jdom.Element;

import clavicom.tools.CKeyAction;
import clavicom.tools.TXMLNames;

public class CCode
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	int keyEvent;		// keyEvent de la touche du clavier
	CKeyAction keyAction;	// Action à effectuer (PRESSED ou RELEASED)

	//------------------------------------------------------ CONSTRUCTEURS --//	
	CCode( int myKeyEvent, CKeyAction myKeyAction )
	{
		keyEvent = myKeyEvent;
		keyAction = myKeyAction;
	}
	
	public static CCode BuildCode( Element node )
	{
		Attribute action =  node.getAttribute( TXMLNames.CS_ATTRIBUT_ACTION );
		Attribute value =  node.getAttribute( TXMLNames.CS_ATTRIBUT_VALUE );
		
		int keyEvent = LoaderTemp.GetKeyEvent( value.getValue() );
		
		// charche le keyAction
		if( action.getValue().equals( "pressed" ) )
		{
			
		}
		
		return null;
	}

	//----------------------------------------------------------- METHODES --//	
	public int GetKeyEvent(){return keyEvent;}
	public CKeyAction GetKeyAction(){return keyAction;}

	//--------------------------------------------------- METHODES PRIVEES --//
}
