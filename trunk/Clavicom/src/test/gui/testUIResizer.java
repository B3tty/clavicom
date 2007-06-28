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

import java.awt.Color;

import javax.swing.JFrame;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.gui.utils.UIMovingPanel;

public class testUIResizer
{
	public static void main(String[] args)
	{
		try
		{
			
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
			
			profil.loadProfileLanguageUIName();
			profil.loadProfileCommandSetName();
			profil.loadProfileShortCutName();
			profil.loadProfile();
			
			
			
			// Chargement du commandEngine
//			CLevelEngine levelEngine = new CLevelEngine( keyboard );
			
			try
			{
				//dictionary = new CDictionary(profil.getDictionnaryName(),profil.getPreferedWords());
			}
			catch (Exception ex)
			{
				System.out.println("argh1 !!!");
				Thread.sleep( 3000 );
				
				System.out.println("argh2 !!!");
				ex.printStackTrace();
			}
			
			
//			
//			/*CLastWordEngine lasWordEngine = */new CLastWordEngine(keyboard,levelEngine);
//			/*CPredictionEngine predictionEngine = */new CPredictionEngine(keyboard,levelEngine,profil.getPreferedWords());
//			/*CCommandEngine commandEngine = */new CCommandEngine( keyboard, levelEngine );
			
			JFrame frame = new JFrame();
			frame.setSize(900,400);
			
			UIMovingPanel panel = new UIMovingPanel(frame);
			panel.setEditable(true);
			panel.setSize(500, 500);
			panel.setBackground(Color.BLUE);

			frame.add( panel);
			
			frame.setVisible(true);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
