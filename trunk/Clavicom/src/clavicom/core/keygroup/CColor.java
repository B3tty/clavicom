/*-----------------------------------------------------------------------------+

			Filename			: CColor.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.key

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

import java.awt.Color;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CColor
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	Color color;		// Couleur de la touche

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CColor( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) );
		}
		
		// Récupération de la couleur R
		Element eltR = node.getChild(TXMLNames.CO_ELEMENT_COLOR_R);
		if(eltR == null)
		{
			throw new Exception (	UIString.getUIString("EX_KEY_MISSING_COLOR_R_1") +
									TXMLNames.CO_ELEMENT_COLOR_R +
									UIString.getUIString("EX_KEY_MISSING_COLOR_R_2"));				
		}	
		
		int R = 0;
		try
		{
			R = Integer.parseInt(eltR.getText());
		}
		catch (Exception E)
		{
			throw new Exception (	UIString.getUIString("EX_KEY_BAD_COLOR_R_1") + 
									TXMLNames.CO_ELEMENT_COLOR_R +
									UIString.getUIString("EX_KEY_BAD_COLOR_R_2")) ;					
		}
		
		// Récupération de la couleur G
		Element eltG = node.getChild(TXMLNames.CO_ELEMENT_COLOR_G);
		if(eltG == null)
		{
			throw new Exception (	UIString.getUIString("EX_KEY_MISSING_COLOR_G_1") +
									TXMLNames.CO_ELEMENT_COLOR_G +
									UIString.getUIString("EX_KEY_MISSING_COLOR_G_2"));			
		}	
		
		int G = 0;
		try
		{
			G = Integer.parseInt(eltG.getText());
		}
		catch (Exception E)
		{
			throw new Exception (	UIString.getUIString("EX_KEY_BAD_COLOR_G_1") + 
									TXMLNames.CO_ELEMENT_COLOR_G + 
									UIString.getUIString("EX_KEY_BAD_COLOR_G_2")) ;						
		}
		
		// Récupération de la couleur B
		Element eltB = node.getChild(TXMLNames.CO_ELEMENT_COLOR_B);
		if(eltB == null)
		{
			throw new Exception (	UIString.getUIString("EX_KEY_MISSING_COLOR_B_1") +
									TXMLNames.CO_ELEMENT_COLOR_B +
									UIString.getUIString("EX_KEY_MISSING_COLOR_B_2"));			
		}	
		
		int B = 0;
		try
		{
			B = Integer.parseInt(eltB.getText());
		}
		catch (Exception E)
		{
			throw new Exception (	UIString.getUIString("EX_KEY_BAD_COLOR_B_1") + 
									TXMLNames.CO_ELEMENT_COLOR_B + 
									UIString.getUIString("EX_KEY_BAD_COLOR_B_2")) ;						
		}
		
		color = new Color(R,G,B);
	}

	//----------------------------------------------------------- METHODES --//
	
	public Color GetColor () {return color;};
	
	public Element BuildNode ( )
	{
		Element color_elem = new Element( TXMLNames.CO_ELEMENT_COLOR );
		
		Element red = new Element( TXMLNames.CO_ELEMENT_COLOR_R );
		red.setText( String.valueOf( color.getRed() ) );
		color_elem.addContent( red );
		
		Element green = new Element( TXMLNames.CO_ELEMENT_COLOR_G );
		green.setText( String.valueOf( color.getGreen() ) );
		color_elem.addContent( green );
		
		Element blue = new Element( TXMLNames.CO_ELEMENT_COLOR_B );
		blue.setText( String.valueOf( color.getBlue() ) );
		color_elem.addContent( blue );
		
		return color_elem;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
