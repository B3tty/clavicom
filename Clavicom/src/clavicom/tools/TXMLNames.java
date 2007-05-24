/*-----------------------------------------------------------------------------+

			Filename			: CXMLNames.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.tools

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

package clavicom.tools;

public class TXMLNames
{
	//--------------------------------------------------------- CONSTANTES --//
	// CPoint
	public static String PT_ELEMENT_POINT 				= "point";
	public static String PT_ELEMENT_X					= "x";
	public static String PT_ELEMENT_Y					= "y";
	
	// Key
	public static String KY_ELEMENT_COORDINATES 		= "coordinates";
	public static String KY_ATTRIBUTE_COORDINATE_POS 	= "position";
	public static String KY_ATTRIBUTE_COORDINATE_POS_MIN= "min";
	public static String KY_ATTRIBUTE_COORDINATE_POS_MAX= "max";
	public static String KY_ATTRIBUTE_ORDER 			= "order";
	
	// CKeyClavicom
	public static String KY_ELEMENT_CLAVICOM		 	= "keyclavicom";
	public static String KY_ELEMENT_CLAVICOM_ACTION 	= "action";
	
	// CKeyString
	public static String KY_ELEMENT_STRING		 		= "keystring";
	
	// CKeyCharacter
	public static String KY_ELEMENT_CHARACTER		 	= "keycharacter";
	public static String KY_ELEMENT_CHARACTER_COMMANDS	= "commands";
	public static String KY_ELEMENT_CHARACTER_COMMAND_NORMAL	= "command_normal";
	public static String KY_ELEMENT_CHARACTER_COMMAND_SHIFT		= "command_shift";
	public static String KY_ELEMENT_CHARACTER_COMMAND_ALTGR		= "command_altgr";
	
	public static String KY_ATTRIBUTE_CHARACTER_COMMAND_CAPTION	= "caption";
	
	// CKeyShortcut
	public static String KY_ELEMENT_SHORTCUT		 	= "keyshortcut";

	// CKeyLauncher
	public static String KY_ELEMENT_LAUNCHER		 	= "keylauncher";
	public static String KY_ELEMENT_LAUNCHER_PATH		= "applicationpath";
	
	// CKeyCommand
	public static String KY_ELEMENT_COMMAND_COMMAND	 	= "command";
	public static String KY_ATTRIBUTE_COMMAND_CAPTION 	= "caption";

	// command
	public static String CM_ATTRIBUTE_ACTION			= "action";
	public static String CM_ATTRIBUTE_VALUE				= "value";
	public static String CM_ATTRIBUTE_ORDER				= "order";
	public static String CM_ATTRIBUTE_CAPTION			= "caption";
	public static String CM_ATTRIBUTE_ID				= "id";
	
	// commandSet
	public static String CS_ELEMENT_CODE				= "code";
	public static String CS_ELEMENT_COMMAND				= "command";
	public static String CS_ELEMENT_SECTION				= "section";
	public static String CS_ELEMENT_COMMANDSET			= "commandeset";
	public static String CS_ATTRIBUTE_NAME				= "name";

	// shorcutSet
	public static String SS_ROOT_NAME					= "shortcuts";
	
	// gestion des blocs
	public static String BL_ATTRIBUTE_CAPTION			= "caption";
	public static String BL_ATTRIBUTE_VISIBLE			= "visible";
	public static String BL_ELEMENT_KEY_GROUP			= "keygroupe";
	public static String BL_ELEMENT_KEY_LIST			= "keylist";
	public static String BL_ATTRIBUTE_ORDER				= "order";
	
	// uiString
	public static String UI_ELEMENT_MESSAGE				= "message";
	public static String UI_ATTRIBUTE_ID				= "id";
	
	// colors
	public static String CO_ELEMENT_COLORS				= "colors";
	public static String CO_ELEMENT_COLOR				= "color";
	public static String CO_ELEMENT_COLOR_NORMAL		= "color_normal";
	public static String CO_ELEMENT_COLOR_CLICKED		= "color_clicked";
	public static String CO_ELEMENT_COLOR_ENTERED		= "color_entered";
	public static String CO_ELEMENT_COLOR_R 			= "red";
	public static String CO_ELEMENT_COLOR_G 			= "green";
	public static String CO_ELEMENT_COLOR_B 			= "blue";
	
	// Settings
	public static String SE_ELEMENT_DEFAULT_PROFIL		= "defaultProfilsPath";
	public static String SE_ELEMENT_SETTINGS			= "settings";
	
}
