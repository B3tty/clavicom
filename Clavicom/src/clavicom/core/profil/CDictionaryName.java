/*-----------------------------------------------------------------------------+

			Filename			: CDictionnaryName.java
			Creation date		: 28 mai 07
		
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

public class CDictionaryName
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String dictionaryName;

	public String getDictionaryName()
	{
		return dictionaryName;
	}

	public void setDictionaryName(String dictionaryName)
	{
		this.dictionaryName = dictionaryName;
	}

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CDictionaryName( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_DICTIONARY_NAME") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		dictionaryName = node.getText();
		
		if( dictionaryName.equals( "" ))
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_DICTIONARY_NAME") + "] : " + UIString.getUIString("EX_PROFIL_DICTIONARY_NAME_MISSING") );
		}
	}

	//----------------------------------------------------------- METHODES --//	
	public Element buildNode()
	{
		Element dictionaryName_elem = new Element( TXMLNames.PR_ELEMENT_DICTIONARY_NAME );
		
		dictionaryName_elem.setText( dictionaryName );
		
		return dictionaryName_elem;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
