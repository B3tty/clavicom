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

import clavicom.core.engine.CMouseEngine;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.language.UIString;
import clavicom.gui.message.UIMessageEngine;
import clavicom.gui.mouse.UIMouse;

public class testUIMouse
{
	static JFrame frame;


	public static void main(String[] args)
	{
		try
		{
			
			CMessageEngine.createInstance();
			new UIMessageEngine();
			
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
			
			ClickEngine clickEngine = new ClickEngine("clavicom_gui_engine_click_ClickEngine");
			
			clickEngine.startHook();
			clickEngine.mouseHookPause();
			clickEngine.mouseHookResume();
			
			
			
			CMouse mouse = CMouse.CreateMouse(
					profil.getDefaultColor().getDefaultKeyNormal().getColor(), 
					profil.getDefaultColor().getDefaultKeyClicked().getColor(), 
					profil.getDefaultColor().getDefaultKeyEntered().getColor());
			
			
			
			UIMouse uimouse = new UIMouse( mouse, clickEngine );
			
			new CMouseEngine( mouse, clickEngine );


			
			frame = new JFrame();
			
			
			frame.setFocusableWindowState(true);
			frame.setAlwaysOnTop(true);
			frame.setSize(200,400);
			
			frame.add( uimouse );
	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
