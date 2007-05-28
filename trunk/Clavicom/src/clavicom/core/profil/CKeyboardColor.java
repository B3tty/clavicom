/*-----------------------------------------------------------------------------+

			Filename			: CKeyboardColor.java
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

import clavicom.core.keygroup.CColor;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CKeyboardColor
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	CColor backColor;
	
	CColor defaultKeyNormal;
	CColor defaultKeyClicked;
	CColor defaultKeyEntered;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyboardColor( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		// ================================================================================================
		// back color
		// ================================================================================================
		Element back_color_elem = node.getChild( TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_BACK );
		if( back_color_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_BACK );
		}
		try
		{
			backColor = new CColor( back_color_elem );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "][" + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_BACK + "] : " + ex.getMessage() );
		}
		
		// ================================================================================================
		// default normal
		// ================================================================================================
		Element default_key_normal_elem = node.getChild( TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_NORMAL );
		if( default_key_normal_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_NORMAL );
		}
		try
		{
			defaultKeyNormal = new CColor( default_key_normal_elem );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "][" + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_NORMAL + "] : " + ex.getMessage() );
		}
		
		// ================================================================================================
		// default clicked
		// ================================================================================================
		Element default_key_clicked_elem = node.getChild( TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_CLICKED );
		if( default_key_clicked_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_CLICKED );
		}
		try
		{
			defaultKeyClicked = new CColor( default_key_clicked_elem );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "][" + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_CLICKED + "] : " + ex.getMessage() );
		}
		
		// ================================================================================================
		// default entered
		// ================================================================================================
		Element default_key_entered_elem = node.getChild( TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_ENTERED );
		if( default_key_entered_elem == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_ENTERED );
		}
		try
		{
			defaultKeyEntered = new CColor( default_key_entered_elem );
		}
		catch (Exception ex)
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_COLOR_BUILD") + "][" + TXMLNames.PR_ELEMENT_KEYBOARD_COLOR_DEFAULT_ENTERED + "] : " + ex.getMessage() );
		}
		
	}

	//----------------------------------------------------------- METHODES --//	
	
	public CColor getBackColor()
	{
		return backColor;
	}

	public void setBackColor(CColor backColor)
	{
		this.backColor = backColor;
	}

	public CColor getDefaultKeyClicked()
	{
		return defaultKeyClicked;
	}

	public void setDefaultKeyClicked(CColor defaultKeyClicked)
	{
		this.defaultKeyClicked = defaultKeyClicked;
	}

	public CColor getDefaultKeyEntered()
	{
		return defaultKeyEntered;
	}

	public void setDefaultKeyEntered(CColor defaultKeyEntered)
	{
		this.defaultKeyEntered = defaultKeyEntered;
	}

	public CColor getDefaultKeyNormal()
	{
		return defaultKeyNormal;
	}

	public void setDefaultKeyNormal(CColor defaultKeyNormal)
	{
		this.defaultKeyNormal = defaultKeyNormal;
	}

	public Element buildNode()
	{
		Element keyboard_color = new Element( TXMLNames.PR_ELEMENT_KEYBOARD_COLOR );

		// back color
		keyboard_color.addContent( backColor.BuildNode() );
		
		// normal
		keyboard_color.addContent(  defaultKeyNormal.BuildNode() );
		
		// clicked
		keyboard_color.addContent( defaultKeyClicked.BuildNode() );
		
		// entered
		keyboard_color.addContent(  defaultKeyEntered.BuildNode() );
		
		
		return keyboard_color;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
