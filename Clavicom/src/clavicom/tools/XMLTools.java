/*-----------------------------------------------------------------------------+

			Filename			: XMLTools.java
			Creation date		: 5 juil. 08
		
			Project				: Clavicom
			Package				: clavicom.tools

			Developed by		: Thomas DEVAUX & Guillaume REBESCHE
			Copyright (C)		: (2008) Centre ICOM'

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

package clavicom.tools;

import java.io.File;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XMLTools
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	
	static public Element openFile( String profilFilePath ) throws Exception
	{
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File( profilFilePath ));
		}
		catch(Exception e)
		{
			throw new Exception("[Chargement de fichier] : Impossible d'ouvrir le fichier " + "\n" + e.getMessage());
		}

		//On initialise un nouvel élément racine avec l'élément racine du document.
		return document.getRootElement();
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
