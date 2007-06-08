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
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import clavicom.core.engine.CCommandEngine;
import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.edition.key.PanelOptionKeyLauncher;
import clavicom.gui.language.UIString;


public class testPanelOptionKeyLauncher
{
	public static void main(String[] args)
	{
		try
		{
			
			UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"  );
			
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
			
			// Chemins
			String input = "Ressources\\Temp\\profil.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			CKeyboard keyboard = profil.getKeyboard();
			
			// Chargement du commandEngine
			CLevelEngine levelEngine = new CLevelEngine( keyboard );
			/*CCommandEngine commandEngine = */new CCommandEngine( keyboard, levelEngine );
			
			// on simule l'appuis sur une touche
			CKeyGroup group = keyboard.getKeyGroup( 0 );
			CKeyList list = group.getkeyList( 0 );
			CKeyLauncher keyLauncher = (CKeyLauncher)list.getKeyKeyboard( 3 );
			
			
			PanelOptionKeyLauncher panelOptionclavicom = new PanelOptionKeyLauncher( keyLauncher );
			JScrollPane sp = new JScrollPane( panelOptionclavicom );
			
			JFrame frame = new JFrame();
			frame.setSize(900,400);
			frame.add( sp );
			
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
