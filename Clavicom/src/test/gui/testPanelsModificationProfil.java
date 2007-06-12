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
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.configuration.PanelModificationProfilCommandSetName;
import clavicom.gui.configuration.PanelModificationProfilDictionaryName;
import clavicom.gui.configuration.PanelModificationProfilFont;
import clavicom.gui.configuration.PanelModificationProfilKeyboardColor;
import clavicom.gui.configuration.PanelModificationProfilNavigation;
import clavicom.gui.configuration.PanelModificationProfilPreferedWords;
import clavicom.gui.configuration.PanelModificationProfilTransparency;
import clavicom.gui.language.UIString;
import clavicom.gui.message.UIMessageEngine;

public class testPanelsModificationProfil
{
	public static void main(String[] args)
	{
		try
		{
			CMessageEngine.createInstance();
			UIMessageEngine messageEngine = new UIMessageEngine();
			
			//UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"  );
			
			//		 Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
			
			// Chemins
			String input = "Ressources\\Temp\\profil.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			

			// PanelModificationProfilCommandSetName p = new PanelModificationProfilCommandSetName( profil.getCommandSetName() );
			// PanelModificationProfilDictionaryName p = new PanelModificationProfilDictionaryName( profil.getDictionnaryName() );
			PanelModificationProfilFont p = new PanelModificationProfilFont( profil.getKeyboardFont() );
			// PanelModificationProfilKeyboardColor p = new PanelModificationProfilKeyboardColor( profil.getDefaultColor() );
			// PanelModificationProfilLangueUIName p = new PanelModificationProfilLangueUIName( profil.getLangueUI() );
			// PanelModificationProfilNavigation p = new PanelModificationProfilNavigation( profil.getNavigation() );
			// PanelModificationProfilPreferedWords p = new PanelModificationProfilPreferedWords( profil.getPreferedWords() );			
			// PanelModificationProfilSound p = new PanelModificationProfilSound( profil.getSound() );
			// PanelModificationProfilTransparency p = new PanelModificationProfilTransparency( profil.getTransparency() );
			
			JFrame frame = new JFrame();
			frame.setSize(500,800);
			frame.add( p );
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
