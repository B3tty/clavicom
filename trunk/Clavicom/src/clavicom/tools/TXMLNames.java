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
	public static String KY_ELEMENT_COLOR 				= "color";
	public static String KY_ELEMENT_COLOR_R 			= "red";
	public static String KY_ELEMENT_COLOR_G 			= "green";
	public static String KY_ELEMENT_COLOR_B 			= "blue";
	public static String KY_ATTRIBUT_ORDER 				= "order";
	
	// Key clavicom
	public static String KY_CLAVICOM_ELEMENT		 		= "keyclavicom";
	public static String KY_CLAVICOM_ELEMENT_ACTION 		= "action";
	
	// CKeyString
	public static String KY_STRING_ELEMENT		 		= "keystring";
	
	// CKeyCharacter
	public static String KY_CHARACTER_ELEMENT		 		= "keycharacter";

	// CKeyShortcut
	public static String KY_SHORTCUT_ELEMENT		 		= "keyshortcut";

	// CKeyLuncher
	public static String KY_LUNCHER_ELEMENT		 		= "keyluncher";
	
	// commandSet
	public static String CS_ATTRIBUT_ACTION				= "action";
	public static String CS_ATTRIBUT_VALUE				= "value";
	public static String CS_ELEMENT_CODE				= "code";
	public static String CS_ELEMENT_COMMAND				= "command";
	public static String CS_ELEMENT_SECTION				= "section";
	public static String CS_ELEMENT_COMMANDSET			= "commandeset";
	public static String CS_ATTRIBUT_NAME				= "name";
	public static String CS_ATTRIBUT_ORDER				= "order";
	public static String CS_ATTRIBUT_CAPTION			= "caption";
	
	// gestion des blocs
	public static String BL_CAPTION						= "caption";
	public static String BL_VISIBLE						= "visible";
	public static String BL_ELEMENT_BLOC_N2				= "bloc_n2";
	
	
}
