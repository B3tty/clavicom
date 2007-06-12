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

public class CShortCutSetName
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String shortcutSetName;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CShortCutSetName( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[Chargement du nom du fichier de racourcis] : Impossible de trouver le noeud XML");
		}
		
		shortcutSetName = node.getText();
		
		if( shortcutSetName.equals( "" ))
		{
			throw new Exception( "[Chargement du nom du fichier de racourcis] : Nom de fichier de raccourcis absent" + UIString.getUIString("EX_PROFIL_COMMANDSET_NAME_MISSING") );
		}
	}

	//----------------------------------------------------------- METHODES --//
	
	public String getShortCutName()
	{
		return shortcutSetName;
	}

	public void setShortCutName(String shortCutName)
	{
		this.shortcutSetName = shortCutName;
	}
	
	
	public Element buildNode()
	{
		Element shortCutName_elem = new Element( TXMLNames.PR_ELEMENT_SHORTCUTSET_NAME );
		
		shortCutName_elem.setText( shortcutSetName );
		
		return shortCutName_elem;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
