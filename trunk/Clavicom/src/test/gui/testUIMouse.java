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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import clavicom.CFilePaths;
import clavicom.core.engine.CMouseEngine;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.keygroup.mouse.CMouse;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.DefilementEngine;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.language.UIString;
import clavicom.gui.message.UIMessageEngine;
import clavicom.gui.mouse.UIMouse;
import clavicom.gui.utils.UITranslucentFrame;

public class testUIMouse
{
	static UITranslucentFrame frame;

	static ClickEngine clickEngine = null;

	public static void main(String[] args)
	{
		
		
		try
		{
			System.setProperty("sun.java2d.noddraw", "true");
			
			CMessageEngine.createInstance();
			new UIMessageEngine(null);
			
			// UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlackStarLookAndFeel"  );
			
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
			
			
			
			// Chemins
			String input = "Ressources\\Temp\\profil3.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			
			profil.loadProfileLanguageUIName();
			profil.loadProfileCommandSetName();
			profil.loadProfileShortCutName();
			profil.loadProfile();
			
			ClickEngine.createInstance("clavicom_gui_engine_click_ClickEngine");
			clickEngine = ClickEngine.getInstance();
			
			
			DefilementEngine.createInstance();
			//DefilementEngine defilementEngine = DefilementEngine.getInstance();
			
			CMouse mouse = CMouse.CreateMouse(
					profil.getDefaultColor().getDefaultKeyNormal().getColor(), 
					profil.getDefaultColor().getDefaultKeyClicked().getColor(), 
					profil.getDefaultColor().getDefaultKeyEntered().getColor());
			

			
			frame = new UITranslucentFrame( /*profil.getTransparency().getKeyboardTransparency()*/0 );
			
			UIMouse uimouse = new UIMouse( mouse );
			
			
			
			ClickEngine.createInstance( CFilePaths.getToolDllMouseHookPath() );
			DefilementEngine.createInstance();
			CMouseEngine.createInstance( mouse );
			
			frame.setFocusableWindowState(true);
			frame.setAlwaysOnTop(true);
			frame.setSize(400,400);
			
			frame.add( uimouse );
			frame.addWindowListener(new WindowListener(){

				public void windowActivated(WindowEvent e)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowClosed(WindowEvent e)
				{
					clickEngine.stopMouseHook();
				}

				public void windowClosing(WindowEvent e)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowDeactivated(WindowEvent e)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowDeiconified(WindowEvent e)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowIconified(WindowEvent e)
				{
					// TODO Auto-generated method stub
					
				}

				public void windowOpened(WindowEvent e)
				{
					// TODO Auto-generated method stub
					
				}
				
			});
	
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
			if( clickEngine != null )
			{
				clickEngine.stopMouseHook();				
			}
		}
	}
}
