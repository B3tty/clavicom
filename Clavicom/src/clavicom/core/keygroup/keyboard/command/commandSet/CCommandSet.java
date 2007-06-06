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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CCommandSet
{
	// --------------------------------------------------------- CONSTANTES --//
	private static CCommandSet commandSet;

	// ---------------------------------------------------------- VARIABLES --//
	HashMap<String, CSection> sectionsList; // liste des sections des commandSet

	HashMap<Integer, CCommand> indexedCommandList;

	HashMap<String, CCommand> indexedStringCommandList;

	List<String> endOfWordCharacterList;

	// ------------------------------------------------------ CONSTRUCTEURS --//
	private CCommandSet(String CommandSetfilePath) throws Exception
	{
		// Initialisation des attributs
		sectionsList = new HashMap<String, CSection>();
		indexedCommandList = new HashMap<Integer, CCommand>();
		indexedStringCommandList = new HashMap<String, CCommand>();
		endOfWordCharacterList = new ArrayList<String>();

		// Chargement du fichier XML
		LoadCommandSetFile(CommandSetfilePath);

		// indexation des commandes
		IndexCommandes();
	}

	// ----------------------------------------------------------- METHODES --//
	public static CCommandSet GetInstance()
	{
		return commandSet;
	}

	public static void CreateInstance(String CommandSetfilePath)
			throws Exception
	{
		commandSet = new CCommandSet(CommandSetfilePath);
	}

	public CSection GetSection(String name)
	{
		return sectionsList.get(name);
	}

	public CCommand GetCommand(int id)
	{
		return indexedCommandList.get(id);
	}

	public CCommand GetCommand(String mySearchString)
	{
		return indexedStringCommandList.get(mySearchString);
	}
	
	public String GetEndOfWordCharacter( int order )
	{
		return endOfWordCharacterList.get( order );
	}
	
	public int EndOfWordCharacterListSize() {return endOfWordCharacterList.size();}

	public CSection getSectionCommand( CCommand command )
	{
		// on recherche la section qui contien la commande suivante...
		for( CSection section : sectionsList.values() )
		{
			if( section.GetCommandMap().containsValue( command ) )
			{
				return section;
			}
		}
		
		return null;
	}
	// --------------------------------------------------- METHODES PRIVEES --//

	private void AddSection(CSection section)
	{
		sectionsList.put(section.name, section);
	}

	private void IndexCommandes()
	{
		for ( CSection section : sectionsList.values() )
		{
			for ( CCommand commande : section.GetCommandMap().values() )
			{
				indexedCommandList.put(commande.GetID(), commande);
				indexedStringCommandList.put(commande.GetSearchString(),
						commande);
			}
		}
	}

	// ---------------------------------------------------------------- XML --//

	private void LoadCommandSetFile(String CommandSetfilePath) throws Exception
	{
		// =======================================================
		// Chargement du fichier XML
		// =======================================================

		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File(CommandSetfilePath));
		}
		catch ( Exception e )
		{
			throw new Exception("["
					+ UIString.getUIString("EX_COMMANDSET_BUILD_COMMANDESET")
					+ "] : " + UIString.getUIString("EX_COMMANDSET_OPEN_FILE")
					+ CommandSetfilePath + "\n" + e.getMessage());
		}

		// On initialise un nouvel élément racine avec l'élément racine du
		// document.
		Element racine = document.getRootElement();

		// pour toutes les sections
		for ( Object object : racine.getChildren(TXMLNames.CS_ELEMENT_SECTION) )
		{
			if ( object instanceof Element )
			{
				Element element = (Element) object;

				CSection section = null;
				try
				{
					section = CSection.BuildSection(element);
				}
				catch ( Exception ex )
				{
					throw new Exception(
							"["
									+ UIString
											.getUIString("EX_COMMANDSET_BUILD_COMMANDESET")
									+ "]" + ex.getMessage());
				}

				try
				{
					AddSection(section);
				}
				catch ( Exception ex )
				{
					throw new Exception(
							"["
									+ UIString
											.getUIString("EX_COMMANDSET_BUILD_COMMANDESET")
									+ "] : "
									+ UIString
											.getUIString("EX_COMMANDSET_ERR_ADD_SECTION")
									+ section.GetName());
				}
			}
		}

		// pour tous les end_of_word_characters
		for ( Object object : racine.getChildren(TXMLNames.CS_EOW_CHARACTERS) )
		{
			if ( object instanceof Element )
			{
				Element element = (Element) object;
				
				if( element != null )
				{
				// pour tous les end_of_word_character
				for ( Object object2 : element.getChildren(TXMLNames.CS_EOW_CHARACTER) )
				{
					if ( object2 instanceof Element )
					{
						Element element2 = (Element) object2;
						if( element2 != null )
						{
							// on récupère la string
							String char_string = element2.getAttributeValue(TXMLNames.CS_EOW_CHARACTER_STRING );
							if( char_string == null )
							{
								throw new Exception("["
										+ UIString.getUIString("EX_COMMANDSET_BUILD_COMMANDESET")
										+ "] : "
										+ UIString.getUIString("EX_KEYGROUP_NOT_FIND_ATTRIBUTE")
										+ TXMLNames.CS_EOW_CHARACTER_STRING );
							}
							
							
							if( ! char_string.equals( "" ) )
							{
								endOfWordCharacterList.add( char_string );
							}
						}
					}
				}
				}
			}
		}
	}

	public HashMap<String, CSection> getSectionsList()
	{
		return sectionsList;
	}
}
