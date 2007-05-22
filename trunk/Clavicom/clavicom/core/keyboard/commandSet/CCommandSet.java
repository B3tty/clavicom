/*-----------------------------------------------------------------------------+

			Filename			: CCommandSet.java
			Creation date		: 21 mai 07

			Project				: Clavicom
			Package				: clavicom.core.keyboard.commandSet

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

package clavicom.core.keyboard.commandSet;

import java.io.File;
import java.util.HashMap;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import clavicom.tools.TXMLNames;

public class CCommandSet 
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	HashMap< String, CSection > sectionsList;	// liste des sections des commandSet

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CCommandSet( String CommandSetfilePath ) throws Exception
	{
		// Initialisation des attributs
		sectionsList = new HashMap<String, CSection>();

		// Chargement du fichier XML
		LoadCommandSetFile ( CommandSetfilePath );
	}


	//----------------------------------------------------------- METHODES --//	
	public CSection GetSection( String name )
	{
		return sectionsList.get( name );
	}

	//--------------------------------------------------- METHODES PRIVEES --//
	private void LoadCommandSetFile ( String CommandSetfilePath ) throws Exception
	{
		// =======================================================
		//	Chargement du fichier XML
		// =======================================================

		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File( CommandSetfilePath ));
		}
		catch(Exception e)
		{
			throw new Exception("[Construction du CommandSet du clavier] : Erreur lors de l'ouverture du fichier " + CommandSetfilePath + "\n" + e.getMessage());
		}

		//On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		
		for( Object object : racine.getChildren( TXMLNames.CS_ELEMENT_SECTION ) )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				CSection section = CSection.BuildSection( element );
				
				try
				{
					AddSection( section );
				}
				catch(Exception ex)
				{
					throw new Exception("[Construction du CommandSet du clavier] : Erreur lors de l'ajout de la section " + section.GetName());
				}
			}
		}
	}

	private void AddSection( CSection section )
	{
		sectionsList.put( section.name, section);
	}
}
