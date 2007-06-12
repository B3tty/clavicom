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

public class CLangueUIName
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String languageFileName;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CLangueUIName( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[Chargement du nom du profil] : Noeud XML non-existant");
		}
		
		languageFileName = node.getText();
		
		if( languageFileName == "" )
		{
			throw new Exception( "[Chargement du nom du profil] : Noeud XML Vide") ;
		}
	}

	
	
	public void setLanguageFileName(String languageFileName)
	{
		this.languageFileName = languageFileName;
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
