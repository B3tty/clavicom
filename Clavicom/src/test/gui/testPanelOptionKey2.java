/*-----------------------------------------------------------------------------+

			Filename			: testPanelOptionKey.java
			Creation date		: 5 juin 07
		
			Project				: Clavicom
			Package				: test.gui

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

package test.gui;


import javax.swing.JFrame;

import clavicom.core.engine.CCommandEngine;
import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.edition.key.UIPanelOptionKey;
import clavicom.gui.language.UIString;

public class testPanelOptionKey2
{
	public static void main(String[] args)
	{
		try
		{
		//		 Chargement des UIString et shortcutset
		UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
		CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
		
		// Chemins
		String input = "Ressources\\Temp\\profil.xml";
		
		// Chargement du profil
		CProfil.createInstance(input);
		CProfil profil = CProfil.getInstance();
		
		CKeyboard keyboard = profil.getKeyboard();
		
		// Chargement du commandEngine
		CLevelEngine levelEngine = new CLevelEngine( keyboard );
		/*CCommandEngine commandEngine =*/ new CCommandEngine( keyboard, levelEngine );
		
		// on simule l'appuis sur une touche
		CKeyGroup group = keyboard.getKeyGroup( 0 );
		CKeyList list = group.getkeyList( 0 );
		CKeyCharacter keyCharacter = (CKeyCharacter)list.getKeyKeyboard( 0 );
		
		
		UIPanelOptionKey panelOptionColor = new UIPanelOptionKey( keyCharacter );
		
		JFrame frame = new JFrame();
		frame.setSize(500,500);
		frame.add( panelOptionColor );
		
		frame.setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
