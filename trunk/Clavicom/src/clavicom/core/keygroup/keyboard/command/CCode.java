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

package clavicom.core.keygroup.keyboard.command;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TKeyAction;
import clavicom.tools.TKeyEventTools;
import clavicom.tools.TXMLNames;

public class CCode
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	int keyEvent;		// keyEvent de la touche du clavier
	TKeyAction keyAction;	// Action à effectuer (PRESSED ou RELEASED)

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CCode( int myKeyEvent, TKeyAction myKeyAction )
	{
		keyEvent = myKeyEvent;
		keyAction = myKeyAction;
	}

	//----------------------------------------------------------- METHODES --//	
	public int GetKeyEvent(){return keyEvent;}
	public TKeyAction GetKeyAction(){return keyAction;}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	//---------------------------------------------------------------- XML --//

	public static CCode BuildCode( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_CODE_BUILD_CODE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ));
		}
		
		String action =  node.getAttributeValue( TXMLNames.CM_ATTRIBUTE_ACTION );
		String value =  node.getAttributeValue( TXMLNames.CM_ATTRIBUTE_VALUE );
		
		if( action == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_CODE_BUILD_CODE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.CM_ATTRIBUTE_ACTION);
		}
		if( value == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_CODE_BUILD_CODE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.CM_ATTRIBUTE_VALUE);
		}
		
		int keyEvent = TKeyEventTools.GetKeyEvent( value );
		
		// Si le keyEvent n'est pas bon
		if( keyEvent == 0 )
		{
			throw new Exception("[" + UIString.getUIString( "EX_CODE_BUILD_CODE" )+ "] : " + UIString.getUIString( "EX_CODE_CAN_NOT_FIND_KEY_CODE" ) + value);
		}
		
		// recherche le keyAction
		TKeyAction keyAction;
		if( action.equals( TKeyAction.CM_STRING_ENUM_PRESSED ) )
		{
			keyAction = TKeyAction.PRESSED;
		}
		else if( action.equals( TKeyAction.CM_STRING_ENUM_RELEASED ) )
		{
			keyAction = TKeyAction.RELEASED;
		}
		else
		{
			// pas de bon keyAction
			throw new Exception("[" + UIString.getUIString( "EX_CODE_BUILD_CODE" )+ "] : " + UIString.getUIString( "EX_CODE_CAN_NOT_FIND_KEY_ACTION" ) + action);
		}
		
		return new CCode( keyEvent, keyAction );

	}
}
