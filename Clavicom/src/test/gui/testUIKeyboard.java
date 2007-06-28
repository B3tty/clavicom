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

import clavicom.core.engine.dictionary.CDictionary;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.gui.message.UIMessageEngine;

public class testUIKeyboard
{
	public static void main(String[] args)
	{
		try
		{
			System.setProperty("sun.java2d.noddraw", "true");
			
			CMessageEngine.createInstance();
			/*UIMessageEngine test = */new UIMessageEngine();
			
			//UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"  );
			
			//		 Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
			
			// Chemins
			String input = "Ressources\\Temp\\profil2.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			profil.loadProfileLanguageUIName();
			profil.loadProfileCommandSetName();
			profil.loadProfileShortCutName();
			profil.loadProfile();
			
			
			// Chargement du commandEngine
//			CLevelEngine levelEngine = new CLevelEngine( keyboard );
			
			try
			{
				CDictionary.createInstance( profil.getDictionnaryName(),profil.getPreferedWords());
			}
			catch (Exception ex)
			{
				System.out.println("argh1 !!!");
			}
			
//			/*CLastWordEngine lasWordEngine = */new CLastWordEngine(keyboard,levelEngine);
//			/*CPredictionEngine predictionEngine = */new CPredictionEngine(keyboard,levelEngine,profil.getPreferedWords());
//			/*CCommandEngine commandEngine = */new CCommandEngine( keyboard, levelEngine );
//
//			UIKeyCreationEngine.createInstance();
//			
//			
//			UIKeyboard uiKeyboard = new UIKeyboard(keyboard,levelEngine );
//			uiKeyboard.setPreferredSize(new Dimension(100,100));

			
			
			
//			UIKeyboardFrame frame = new UIKeyboardFrame(uiKeyboard);
//			frame.setVisible(true);
//			frame.setSize(900,400);
//			frame.edit(true);
			
			
			
//			System.out.println("ici");
//			
//			UITranslucentFrame frame = new UITranslucentFrame (.5f);
//			UIMovingPanel panel = new UIMovingPanel(frame);
//			panel.setEditable(true);
//			
//			panel.setLayout(new BorderLayout());
//			panel.add(uiKeyboard, BorderLayout.CENTER);
//			
//			frame.setSize(900,400);
//			frame.add( panel );
//			
//			frame.setAlwaysOnTop(true);
//
//			frame.setFocusableWindowState(false);
//			//frame.setFocusable(false);
//			
//			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			frame.setVisible(true);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
