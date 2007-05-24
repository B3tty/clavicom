/*-----------------------------------------------------------------------------+

			Filename			: CCommandSet.java
			Creation date		: 23 mai 07

			Project				: Clavicom
			Package				: clavicom.core.key.keyboard.shortcutset

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

package clavicom.core.key.keyboard.command.shortcutSet;

import java.io.File;
import java.util.HashMap;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.core.key.keyboard.command.CCommand;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CShortcutSet 
{
	//--------------------------------------------------------- CONSTANTES --//
	private static CShortcutSet shortcutSet;
	
	//---------------------------------------------------------- VARIABLES --//	
	static HashMap<String, CCommand> commandMap;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	private CShortcutSet( String myShortcutSetPath) throws Exception
	{
		commandMap = new HashMap<String, CCommand>();
		
		// Chargement du fichier XML
		LoadShortcutSetFile (myShortcutSetPath);
	}

	//----------------------------------------------------------- METHODES --//
	public static CShortcutSet GetInstance() 
	{
		return shortcutSet;
	}
	
	public static void CreateInstance(String myShortcutSetPath) throws Exception
	{
		shortcutSet = new CShortcutSet (myShortcutSetPath);
	}
	
	public static CCommand GetCommand( String name )
	{
		return commandMap.get( name );
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	private static void LoadShortcutSetFile ( String myShortcutsetfilePath ) throws Exception
	{
		// =======================================================
		//	Chargement du fichier XML
		// =======================================================

		SAXBuilder sxb = new SAXBuilder();
		Document document = null;
		try
		{
			document = sxb.build(new File( myShortcutsetfilePath ));
		}
		catch(Exception e)
		{
			throw new Exception(	UIString.getUIString("EX_SHORTCUTSET_BAD_PATH_1") +  
									myShortcutsetfilePath +
									UIString.getUIString("EX_SHORTCUTSET_BAD_PATH_2") + 
									e.getMessage());
		}

		// On initialise un nouvel élément racine avec l'élément racine du document.
		Element racine = document.getRootElement();
		
		// Vérification du nom de la racine
		if (!racine.getName().equals(TXMLNames.SS_ROOT_NAME))
		{
			throw new Exception(UIString.getUIString("EX_SHORTCUTSET_BAD_ROOT_1") + 
								TXMLNames.SS_ROOT_NAME + 
								UIString.getUIString("EX_SHORTCUTSET_BAD_ROOT_2") + 
								racine.getName() + 
								UIString.getUIString("EX_SHORTCUTSET_BAD_ROOT_3"));
		}
		
		for( Object object : racine.getChildren() )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				CCommand command = CCommand.BuildCommand(element);
								
				try
				{
					commandMap.put(command.GetCaption(),command);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception(	UIString.getUIString("EX_SHORTCUTSET_BAD_COMMAND") + 
											command.GetCaption());				
				}
			}
		}
	}

	
}
