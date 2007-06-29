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
	private static String sounds = application + "Sounds\\";
	
	private static String userPictures = profils + "Pictures\\";
	
	private static String configFile = application + "config.xml";
	private static String splashScreenFile = pictures + "splash.png";
	
	private static String toolDllMouseHookPath = "clavicom_gui_engine_click_ClickEngine";
	
	// ------- IMAGES DE TOUCHES (CKeyXXX) -------------------
	private static String toolKeyCharacterPicture = "character.png";
	private static String toolKeyLauncherPicture = "launch.png";
	private static String toolKeyLastWordPicture = "last_word.png";
	private static String toolKeyPredictionPicture = "prediction.png";
	private static String toolKeyShortCutPicture = "shortcut.png";
	private static String toolKeyStringPicture = "string.png";
	private static String toolKeyLevelSHIFTPicture = "shift.png";
	private static String toolKeyLevelALTGRPicture = "altgr.png";
	private static String toolKeyClavicomClosePicture = "close.png";
	private static String toolKeyClavicomConfigurationPicture = "configuration.png";
	private static String toolKeyClavicomSwitchSouricomPicture = "mouse.png";
	
	// mouse picture
	private static String toolKeyMouseArrowUp = "mouse_arrow_up.png";
	private static String toolKeyMouseArrowDown = "mouse_arrow_down.png";
	private static String toolKeyMouseArrowLeft = "mouse_arrow_left.png";
	private static String toolKeyMouseArrowRight = "mouse_arrow_right.png";
	private static String toolKeyMouseToClavicom = "mouse_to_clavicom.png";
	
	private static String toolKeyMouseLeft = "mouse_left.png";
	private static String toolKeyMouseDoubleLeft = "mouse_double_left.png";
	private static String toolKeyMouseRight = "mouse_right.png";
	private static String toolKeyMouseDrag = "mouse_drag.png";
	private static String toolKeyMouseDrop = "mouse_drop.png";
	private static String toolKeyMouseClick = "mouse_click.png";
	private static String toolKeyMouseMove = "mouse_move.png";
	
	
	// ------- IMAGES DE COMPOSANTS AUTRES (JButton,...) ---------
	private static String levelEditorAdd = pictures + "add.png";
	private static String levelEditorRemove = pictures + "remove.png";
	
	private static String levelEditorUp = pictures + "up.png";
	private static String levelEditorDown = pictures + "down.png";	

	private static String levelEditorClass = pictures + "class.png";
	private static String levelEditorClassAutomatic = pictures + "class_automatic.png";
	
	private static String defaultPicture = pictures + "default.png";
	
	// ABOUT
	private static String aboutPicture = pictures + "about.png";

	
	// Sound
	private static String scrollSoundFilePath = sounds + "scroll.wav";
	private static String enteredSoundFilePath = sounds + "entered.wav";
	private static String clickSoundFilePath = sounds + "click.wav";
	

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


	public static String getToolKeyMouseLeft()
	{
		return toolKeyMouseLeft;
	}

	public static String getToolKeyMouseRight()
	{
		return toolKeyMouseRight;
	}

	public static String getToolDllMouseHookPath()
	{
		return toolDllMouseHookPath;
	}
	
	
	public static String getUserPicturesFolder()
	{
		return userPictures;
	}

	public static String getDefaultPicturePath()
	{
		return defaultPicture;
	}

	public static String getLevelEditorAdd()
	{
		return levelEditorAdd;
	}

	public static String getLevelEditorRemove()
	{
		return levelEditorRemove;
	}

	public static String getLevelEditorDown()
	{
		return levelEditorDown;
	}

	public static String getLevelEditorUp()
	{
		return levelEditorUp;
	}

	public static String getLevelEditorClass()
	{
		return levelEditorClass;
	}

	public static String getLevelEditorClassAutomatic()
	{
		return levelEditorClassAutomatic;
	}

	public static String getClickSoundFilePath()
	{
		return clickSoundFilePath;
	}

	public static String getEnteredSoundFilePath()
	{
		return enteredSoundFilePath;
	}

	public static String getScrollSoundFilePath()
	{
		return scrollSoundFilePath;
	}

	public static String getToolKeyMouseArrowDown()
	{
		return toolKeyMouseArrowDown;
	}

	public static String getToolKeyMouseArrowLeft()
	{
		return toolKeyMouseArrowLeft;
	}

	public static String getToolKeyMouseArrowRight()
	{
		return toolKeyMouseArrowRight;
	}

	public static String getToolKeyMouseArrowUp()
	{
		return toolKeyMouseArrowUp;
	}

	public static String getToolKeyMouseClick()
	{
		return toolKeyMouseClick;
	}

	public static String getToolKeyMouseDoubleLeft()
	{
		return toolKeyMouseDoubleLeft;
	}

	public static String getToolKeyMouseDrag()
	{
		return toolKeyMouseDrag;
	}

	public static String getToolKeyMouseDrop()
	{
		return toolKeyMouseDrop;
	}

	public static String getToolKeyMouseMove()
	{
		return toolKeyMouseMove;
	}

	public static String getToolKeyMouseToClavicom()
	{
		return toolKeyMouseToClavicom;
	}

	public static String getAboutPicture()
	{
		return aboutPicture;
	}
	
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
}
