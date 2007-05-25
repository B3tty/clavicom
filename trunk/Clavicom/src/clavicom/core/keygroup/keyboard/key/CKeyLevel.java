/*-----------------------------------------------------------------------------+

			Filename			: CKeyLevel.java
			Creation date		: 25 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keygroup.keyboard.key

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

package clavicom.core.keygroup.keyboard.key;

import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TPoint;
import clavicom.tools.TXMLNames;

public class CKeyLevel extends CKeyboardKey
{

	
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	
	int level;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyLevel(
			CColor myColorNormal, 
			CColor myColorClicked, 
			CColor myColorEntered, 
			TPoint myPointMin, 
			TPoint myPointMax,
			int myLevel)
	{
		super(myColorNormal, myColorClicked, myColorEntered, myPointMin, myPointMax);


		level = myLevel;
	}
	
	public CKeyLevel( Element node ) throws Exception
	{
		super( node );
		
		// Récupération du level
		String s_level = node.getChildText( TXMLNames.KY_ELEMENT_LEVEL );
		if( s_level == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYLEVEL_BUILD" )+ "] : " + UIString.getUIString( "EX_KEYLEVEL_BUILD" ) + TXMLNames.CM_ATTRIBUTE_ID);
		}
		try
		{
			level = Integer.parseInt( s_level );
		}
		catch(Exception ex)
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYLEVEL_BUILD" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_level + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ) );
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	public int GetLevel(){ return level; }
	
	@Override
	public void completeNodeSpecific(Element eltKeyNode) throws Exception
	{
		Element level_elem = new Element( TXMLNames.KY_ELEMENT_LEVEL );
		level_elem.setText( String.valueOf( level ) );
		
		eltKeyNode.addContent( level_elem );
	}
	
	@Override
	public String getElementName()
	{
		return TXMLNames.KY_ELEMENT_KEYLEVEL;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	
}
