/*-----------------------------------------------------------------------------+

			Filename			: CCommandSet.java
			Creation date		: 21 mai 07

			Project				: Clavicom
			Package				: clavicom.core.key.keyboard.commandset.command

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

package clavicom.core.keygroup.keyboard.command.commandSet;

import java.io.File;
import java.util.HashMap;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CCommandSet 
{
	//--------------------------------------------------------- CONSTANTES --//
	private static CCommandSet commandSet;
	
	//---------------------------------------------------------- VARIABLES --//	
	HashMap< String, CSection > sectionsList;	// liste des sections des commandSet
	HashMap< Integer, CCommand> indexedCommandList;
	HashMap< String, CCommand> indexedStringCommandList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	private CCommandSet( String CommandSetfilePath ) throws Exception
	{
		// Initialisation des attributs
		sectionsList = new HashMap<String, CSection>();
		indexedCommandList = new HashMap<Integer, CCommand>();
		indexedStringCommandList = new HashMap<String, CCommand>();

		// Chargement du fichier XML
		LoadCommandSetFile ( CommandSetfilePath );
		
		// indexation des commandes
		IndexCommandes();
	}

	//----------------------------------------------------------- METHODES --//	
	public static CCommandSet GetInstance() 
	{
		return commandSet;
	}
	
	public static void CreateInstance(String CommandSetfilePath) throws Exception
	{
		commandSet = new CCommandSet (CommandSetfilePath);
	}

	public CSection GetSection( String name )
	{
		return sectionsList.get( name );
	}
	
	public CCommand GetCommande( int id )
	{
		return indexedCommandList.get( id );
	}
	
	public CCommand GetCommande( String mySearchString )
	{
		return indexedStringCommandList.get( mySearchString );
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	private void AddSection( CSection section )
	{
		sectionsList.put( section.name, section);
	}
	
	private void IndexCommandes()
	{
		for( CSection section : sectionsList.values() )
		{
			for ( CCommand commande : section.GetCommandMap().values() )
			{
				indexedCommandList.put( commande.GetID() , commande );
				indexedStringCommandList.put( commande.GetSearchString() , commande );
			}
		}
	}
	
	//---------------------------------------------------------------- XML --//
	
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
			throw new Exception("[" + UIString.getUIString( "EX_COMMANDSET_BUILD_COMMANDESET" )+ "] : " + UIString.getUIString( "EX_COMMANDSET_OPEN_FILE" ) + CommandSetfilePath + "\n" + e.getMessage());
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
					throw new Exception("[" + UIString.getUIString( "EX_COMMANDSET_BUILD_COMMANDESET" )+ "] : " + UIString.getUIString( "EX_COMMANDSET_ERR_ADD_SECTION" ) + section.GetName());
				}
			}
		}
	}

	
}