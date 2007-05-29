/*-----------------------------------------------------------------------------+

			Filename			: CTransparency.java
			Creation date		: 25 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.profil

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

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CTransparency
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	private int KeyboardTrancparencyPourcent;
	private int KeyTrancparencyPourcent;
	
	public int getKeyboardTrancparencyPourcent()
	{
		return KeyboardTrancparencyPourcent;
	}
	public void setKeyboardTrancparencyPourcent(int keyboardTrancparencyPourcent)
	{
		KeyboardTrancparencyPourcent = keyboardTrancparencyPourcent;
	}
	public int getKeyTrancparencyPourcent()
	{
		return KeyTrancparencyPourcent;
	}
	public void setKeyTrancparencyPourcent(int keyTrancparencyPourcent)
	{
		KeyTrancparencyPourcent = keyTrancparencyPourcent;
	}

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CTransparency( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_TRANSPARENCY") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		// ==================================================================
		// Récupération de la trensparence du clavier
		// ==================================================================
		String s_keyboardTransparency = node.getChildText( TXMLNames.PR_ELEMENT_TRANSPARENCY_KEYBOARD );
		if( s_keyboardTransparency == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_TRANSPARENCY") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.PR_ELEMENT_TRANSPARENCY_KEYBOARD );
		}
		
		try
		{
			KeyboardTrancparencyPourcent = Integer.parseInt( s_keyboardTransparency );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_TRANSPARENCY" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_keyboardTransparency + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ));
		}
		
		// ==================================================================
		// Récupération de la trensparence des touches
		// ==================================================================
		String s_keyTransparency = node.getChildText( TXMLNames.PR_ELEMENT_TRANSPARENCY_KEY );
		if( s_keyTransparency == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_TRANSPARENCY") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.PR_ELEMENT_TRANSPARENCY_KEY );
		}
		
		try
		{
			KeyTrancparencyPourcent = Integer.parseInt( s_keyTransparency );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_PROFIL_BUILD_TRANSPARENCY" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_keyTransparency + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ));
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	public Element buildNode()
	{
		Element transparencyElement = new Element( TXMLNames.PR_ELEMENT_TRANSPARENCY );
		
		// ajout de la transparence du clavier
		Element keyboardTransparency_elem = new Element( TXMLNames.PR_ELEMENT_TRANSPARENCY_KEYBOARD );
		keyboardTransparency_elem.setText( String.valueOf( KeyboardTrancparencyPourcent ) );
		transparencyElement.addContent( keyboardTransparency_elem );
		
		// ajout de la transparence des touches
		Element keyTransparency_elem = new Element( TXMLNames.PR_ELEMENT_TRANSPARENCY_KEY );
		keyTransparency_elem.setText( String.valueOf( KeyTrancparencyPourcent ) );
		transparencyElement.addContent( keyTransparency_elem );
		
		return transparencyElement;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
