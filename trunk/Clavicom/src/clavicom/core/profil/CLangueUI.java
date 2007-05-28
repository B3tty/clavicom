/*-----------------------------------------------------------------------------+

			Filename			: CLangueUI.java
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

public class CLangueUI
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String languageFileName;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CLangueUI( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_LANGUAGE_UI_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		languageFileName = node.getText();
		
		if( languageFileName == "" )
		{
			throw new Exception( "[" + UIString.getUIString("EX_LANGUAGE_UI_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
	}

	
	
	//----------------------------------------------------------- METHODES --//
	public String getLanguageFileName()
	{
		return languageFileName;
	}
	
	public Element buildNode()
	{
		Element languageUI = new Element( TXMLNames.PR_ELEMENT_LANGUAGE_UI );
		
		languageUI.setText( languageFileName );
		
		return languageUI;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}