/*-----------------------------------------------------------------------------+

			Filename			: CCode.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.command

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

package clavicom.core.keyboard.command;

import org.jdom.Element;
import clavicom.tools.CKeyAction;
import clavicom.tools.CKeyEventTools;
import clavicom.tools.TXMLNames;

public class CCode
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	int keyEvent;		// keyEvent de la touche du clavier
	CKeyAction keyAction;	// Action à effectuer (PRESSED ou RELEASED)

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CCode( int myKeyEvent, CKeyAction myKeyAction )
	{
		keyEvent = myKeyEvent;
		keyAction = myKeyAction;
	}

	//----------------------------------------------------------- METHODES --//	
	public int GetKeyEvent(){return keyEvent;}
	public CKeyAction GetKeyAction(){return keyAction;}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	//---------------------------------------------------------------- XML --//

	public static CCode BuildCode( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception("[Construction d'un code de clavier] : Impossible de trouver le noeud XML");
		}
		
		String action =  node.getAttributeValue( TXMLNames.CS_ATTRIBUT_ACTION );
		String value =  node.getAttributeValue( TXMLNames.CS_ATTRIBUT_VALUE );
		
		if( action == null )
		{
			throw new Exception("[Construction d'un code de clavier] : Impossible de trouver l'attribut :" + TXMLNames.CS_ATTRIBUT_ACTION);
		}
		if( value == null )
		{
			throw new Exception("[Construction d'un code de clavier] : Impossible de trouver l'attribut :" + TXMLNames.CS_ATTRIBUT_VALUE);
		}
		
		int keyEvent = CKeyEventTools.GetKeyEvent( value );
		
		// Si le keyEvent n'est pas bon
		if( keyEvent == 0 )
		{
			throw new Exception("[Construction d'un code de clavier] : Impossible de trouver le code caractere correspondant à :" + value);
		}
		
		// recherche le keyAction
		CKeyAction keyAction;
		if( action.equals( CKeyAction.CS_STRING_ENUM_PRESSED ) )
		{
			keyAction = CKeyAction.PRESSED;
		}
		else if( action.equals( CKeyAction.CS_STRING_ENUM_RELEASED ) )
		{
			keyAction = CKeyAction.RELEASED;
		}
		else
		{
			// pas de bon keyAction
			throw new Exception("[Construction d'un code de clavier] : Impossible de trouver la bonne action correspondante à :" + action);
		}
		
		return new CCode( keyEvent, keyAction );

	}
}
