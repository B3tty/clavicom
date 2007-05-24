/*-----------------------------------------------------------------------------+

			Filename			: CKey.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core

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

package clavicom.core.keygroup;

import org.jdom.Element;

import clavicom.core.keygroup.CColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CColor normal;
	CColor clicked;
	CColor entered;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CKey( CColor myNormal, CColor myClicked, CColor myEntered )
	{
		normal = myNormal;
		clicked = myClicked;
		entered = myEntered;
	}
	
	public CKey( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) );
		}
		
		// ===============================================================
		//	Récupération de l'élément colors
		// ===============================================================
		Element colors = node.getChild( TXMLNames.CO_ELEMENT_COLORS );
		if( colors == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLORS );
		}
		
		// =================================================================
		// Récupération de la color_normal
		// =================================================================
		Element color_normal_elem = colors.getChild( TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		if( color_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		}
		
		// =================================================================
		// Récupération de la color_clicked
		// =================================================================
		Element color_clicked_elem = colors.getChild( TXMLNames.CO_ELEMENT_COLOR_CLICKED );
		if( color_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		}
		
		// =================================================================
		// Récupération de la color_entrered
		// =================================================================
		Element color_entered_elem = colors.getChild( TXMLNames.CO_ELEMENT_COLOR_ENTERED );
		if( color_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString( "EX_KEY_BUILD" ) +"] " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) + TXMLNames.CO_ELEMENT_COLOR_NORMAL );
		}
		
		// =================================================================
		// Création des couleurs
		// =================================================================
		try
		{
			normal = new CColor( color_normal_elem );
		}catch(Exception ex)
		{
			throw new Exception( "[" + TXMLNames.CO_ELEMENT_COLOR_NORMAL +"]" + ex.getMessage() );
		}
		
		try
		{
			clicked = new CColor( color_clicked_elem );
		}catch(Exception ex)
		{
			throw new Exception( "[" + TXMLNames.CO_ELEMENT_COLOR_CLICKED +"]" + ex.getMessage() );
		}
		
		try
		{
			entered = new CColor( color_entered_elem );
		}catch(Exception ex)
		{
			throw new Exception( "[" + TXMLNames.CO_ELEMENT_COLOR_ENTERED +"]" + ex.getMessage() );
		}
		
	}

	public CColor GetColorNormal(){return normal;}
	public CColor GetColorClicked(){return clicked;}
	public CColor GetColorEntered(){return entered;}
	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	
}
