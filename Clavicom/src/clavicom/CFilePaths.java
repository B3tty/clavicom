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

package clavicom;

public class CFilePaths
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	private static String ressources = "Ressources\\"; 
	private static String application = ressources + "Application\\";
	
	private static String commandSets = application + "CommandSets\\";
	private static String defaultProfile = application + "DefaultProfiles\\";
	private static String dictionaries = application + "Dictionaries\\";
	private static String keyboardModels = application + "KeybordModels\\";
	private static String languagesUI = application + "LanguagesUI\\";
	private static String shortcutSets = application + "ShortcutSets\\";
	private static String pictures = application + "Pictures\\";
	private static String profils = ressources + "Profiles\\";
	
	private static String userPictures = profils + "Pictures\\";
	
	private static String configFile = application + "config.xml";
	private static String splashScreenFile = pictures + "splash.png";
	
	private static String toolKeyCharacterPicture = pictures + "character.png";
	private static String toolKeyLauncherPicture = pictures + "launch.png";
	private static String toolKeyLastWordPicture = pictures + "last_word.png";
	private static String toolKeyPredictionPicture = pictures + "prediction.png";
	private static String toolKeyShortCutPicture = pictures + "shortcut.png";
	private static String toolKeyStringPicture = pictures + "string.png";
	private static String toolKeyLevelSHIFTPicture = pictures + "shift.png";
	private static String toolKeyLevelALTGRPicture = pictures + "altgr.png";
	private static String toolKeyClavicomClosePicture = pictures + "close.png";
	private static String toolKeyClavicomConfigurationPicture = pictures + "configuration.png";
	private static String toolKeyClavicomSwitchSouricomPicture = pictures + "mouse.png";
	
	private static String defaultPicture = pictures + "default.png";

	// ========================================================================
	// Getters
	// ========================================================================

	
	public static String getCommandSetsFolder()
	{
		return commandSets;
	}

	public static String getConfigFileFolder()
	{
		return configFile;
	}

	public static String getDefaultProfileFolder()
	{
		return defaultProfile;
	}

	public static String getDictionariesFolder()
	{
		return dictionaries;
	}

	public static String getKeyboardModelsFolder()
	{
		return keyboardModels;
	}

	public static String getLanguagesUIFolder()
	{
		return languagesUI;
	}

	public static String getProfilsFolder()
	{
		return profils;
	}

	public static String getShortcutSetsFolder()
	{
		return shortcutSets;
	}

	public static String getPictures()
	{
		return pictures;
	}

	public static void setPicturesFolder(String pictures)
	{
		CFilePaths.pictures = pictures;
	}

	public static String getSplashScreenFile()
	{
		return splashScreenFile;
	}

	public static String getToolKeyCharacterPicture()
	{
		return toolKeyCharacterPicture;
	}

	public static String getToolKeyClavicomClosePicture()
	{
		return toolKeyClavicomClosePicture;
	}

	public static String getToolKeyClavicomConfigurationPicture()
	{
		return toolKeyClavicomConfigurationPicture;
	}

	public static String getToolKeyClavicomSwitchSouricomPicture()
	{
		return toolKeyClavicomSwitchSouricomPicture;
	}

	public static String getToolKeyLauncherPicture()
	{
		return toolKeyLauncherPicture;
	}

	public static String getToolKeyLevelALTGRPicture()
	{
		return toolKeyLevelALTGRPicture;
	}

	public static String getToolKeyLevelSHIFTPicture()
	{
		return toolKeyLevelSHIFTPicture;
	}

	public static String getToolKeyPredictionPicture()
	{
		return toolKeyPredictionPicture;
	}

	public static String getToolKeyShortCutPicture()
	{
		return toolKeyShortCutPicture;
	}

	public static String getToolKeyStringPicture()
	{
		return toolKeyStringPicture;
	}
	
	public static String getToolKeyLastWordPicture()
	{
		return toolKeyLastWordPicture;
	}

	public static String getUserPicturesFolder()
	{
		return userPictures;
	}

	public static String getDefaultPicturePath()
	{
		return defaultPicture;
	}
	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
