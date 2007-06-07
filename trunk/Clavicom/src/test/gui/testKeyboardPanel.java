package test.gui;
/*-----------------------------------------------------------------------------+

			Filename			: testKeyboardPanel.java
			Creation date		: 30 mai 07
		
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

import javax.swing.JFrame;

import clavicom.core.engine.CCommandEngine;
import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public final class testKeyboardPanel extends JFrame {

	private testKeyboardPanel() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        //getContentPane().add((new UIKeyboardPanel()));
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) throws Exception {
    	
		// Chargement des UIString et shortcutset
		UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
		CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
		CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
		
		
		// Chemins
		String input = "Ressources\\Temp\\profil.xml";
		
		// Chargement du profil
		try
		{
			CProfil.createInstance(input);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		CProfil profil = CProfil.getInstance();
		
		CKeyboard keyboard = profil.getKeyboard();
		
		// Chargement du commandEngine
		/*CCommandEngine commandEngine = */new CCommandEngine( keyboard, new CLevelEngine(keyboard));
		
    	testKeyboardPanel application =  new testKeyboardPanel();
    	application.setVisible(true);
    	application.setFocusableWindowState(false);
    	application.setAlwaysOnTop(true);
    }
}
