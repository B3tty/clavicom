/*-----------------------------------------------------------------------------+

			Filename			: RepositoryPaths.java
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

public class RepositoryPaths
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	private static String ressources = "Ressources\\"; 
	private static String application = ressources + "Application\\";
	
	private static String commandSets = application + "CommandSet\\";
	private static String defaultProfile = application + "DefaultProfiles\\";
	private static String dictionaries = application + "Dictionaries\\";
	private static String keyboardModels = application + "KeybordModels\\";
	private static String languagesUI = application + "Languages\\";
	private static String shortcutSets = application + "ShortcutSets\\";
	private static String configFile = application + "config.xml";
	
	private static String profils = ressources + "Profiles\\";
	
	
	// ========================================================================
	// Getters
	// ========================================================================

	
	public static String getCommandSets()
	{
		return commandSets;
	}

	public static String getConfigFile()
	{
		return configFile;
	}

	public static String getDefaultProfile()
	{
		return defaultProfile;
	}

	public static String getDictionaries()
	{
		return dictionaries;
	}

	public static String getKeyboardModels()
	{
		return keyboardModels;
	}

	public static String getLanguagesUI()
	{
		return languagesUI;
	}

	public static String getProfils()
	{
		return profils;
	}

	public static String getShortcutSets()
	{
		return shortcutSets;
	}

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
