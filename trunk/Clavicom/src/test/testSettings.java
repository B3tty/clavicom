/*-----------------------------------------------------------------------------+

			Filename			: testSettings.java
			Creation date		: 24 mai 07
		
			Project				: Clavicom
			Package				: test

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

package test;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import clavicom.CSettings;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.gui.language.UIString;

public class testSettings
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	

	//----------------------------------------------------------- METHODES --//

	public static void main(String[] args)
	{
		try
		{
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("src\\clavicom\\Ressources\\Application\\LanguagesUI\\francais.clg");
			CShortcutSet.CreateInstance("src\\clavicom\\Ressources\\Application\\ShortcutSets\\default.css");
			
			String s = "src\\clavicom\\Ressources\\Application\\config.xml";
			String s_out = "src\\clavicom\\Ressources\\Application\\config_out.xml";
			
			CSettings.LoadSettings( s );
			
			System.out.println( CSettings.getDefaultProfilsPath() );
			
			CSettings.SaveSettings( s_out );
	
			
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
