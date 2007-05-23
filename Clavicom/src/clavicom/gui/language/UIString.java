/*-----------------------------------------------------------------------------+

			Filename			: IUString.java
			Creation date		: 23 mai 07
		
			Project				: Clavicom
			Package				: clavicom.gui.language

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

package clavicom.gui.language;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.tools.TXMLNames;

public class UIString
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	static HashMap<String, String> uiStringMap;

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//
	public static String getUIString( String ID )
	{
		if( uiStringMap != null )
		{
			return uiStringMap.get( ID );
		}
		
		return "";
	}
	
	public static void LoadUIStringFile( String path ) throws Exception
	{
		uiStringMap = new HashMap<String, String>();
		
		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File( path ));
		}
		catch(Exception e)
		{
			throw new Exception("[Construction du UIString] : Erreur lors de l'ouverture du fichier " + path + "\n" + e.getMessage());
		}

		//On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		
		// ================================================================
		//	Chargement de tous les messages
		// ================================================================
		List messageList = racine.getChildren( TXMLNames.UI_ELEMENT_MESSAGE );
		for( Object object : messageList )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					// ================================================================
					//	Récupération de l'ID
					// ================================================================
					String id = element.getAttributeValue( TXMLNames.UI_ATTRIBUTE_ID );
					if( id == null )
					{
						throw new Exception("[Construction du UIString] : Erreur lors de la récupération de l'ID d'un message");
					}
					
					// ================================================================
					//	Récupération du message
					// ================================================================
					String message = element.getText();
					
					
					uiStringMap.put( id , message );

				}
			}
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
