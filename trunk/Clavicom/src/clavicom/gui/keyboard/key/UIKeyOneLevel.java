/*-----------------------------------------------------------------------------+

			Filename			: UIKeyOneLevel.java
			Creation date		: 30 mai 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key

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

package clavicom.gui.keyboard.key;

import java.io.File;

import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class UIKeyOneLevel extends UIKey
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	static org.jdom.Document document;
	static Element racine;	
	static CKeyString key;
	//----------------------------------------------------------- METHODES --//	
	protected CKey getCoreKey()
	{
//<TEMPORAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! >
		if (key != null)
		{
			return key;
		}
		
		try
		{
//			// Chargement des UIString et shortcutset
//			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
//			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
//			
//			// On crée une instance de SAXBuilder
//			SAXBuilder sxb = new SAXBuilder();
//	
//			// On crée un nouveau document JDOM avec en argument le fichier XML
//			// Le parsing est terminé ;)
//			document = sxb.build(new File("Ressources\\Temp\\key_string.xml"));
//	
//			// On initialise un nouvel élément racine avec l'élément racine du
//			// document.
//			racine = document.getRootElement();
//	
//			Element uneTouche = racine.getChild("keystring");
//	
//			// Construction de la touche
//			key = new CKeyString(uneTouche);
			
			key = (CKeyString) CProfil.getInstance().getKeyboard().getKeyGroup(0).GetkeyList(0).GetKeyboardKey(2);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
		}
		System.out.println("ok");
		return key;
		
//</TEMPORAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! >
	}
	
	protected String getCaption()
	{
		return key.getCaption();
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
