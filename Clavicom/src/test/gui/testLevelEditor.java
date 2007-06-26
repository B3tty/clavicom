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

//import java.awt.Dimension;
//import java.awt.GridLayout;

//import javax.swing.JFrame;
//import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.UIKeyCreationEngine;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.levelmanager.UILevelManagerFrame;
import clavicom.gui.message.UIMessageEngine;

public class testLevelEditor
{
	public static void main(String[] args)
	{
		try
		{
			System.setProperty("sun.java2d.noddraw", "true");
			
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel"  );
			
			CMessageEngine.createInstance();
			new UIMessageEngine();
			
			//UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"  );
			
			//		 Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");

			UIKeyCreationEngine.createInstance();
			
			// Chemins
			String input = "Ressources\\Temp\\profil3.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			
			profil.loadProfileLanguageUIName();
			profil.loadProfileCommandSetName();
			profil.loadProfileShortCutName();
			profil.loadProfile();
			
			UIKeyboard uiKeyboard = new UIKeyboard(profil.getKeyboard(),new CLevelEngine(profil.getKeyboard()));
			

			UILevelManagerFrame temp = new UILevelManagerFrame();
			temp.setUIKeyboard(uiKeyboard);
			
			temp.setVisible(true);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
