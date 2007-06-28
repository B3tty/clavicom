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
import java.awt.Dimension;
import javax.swing.JPanel;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
import clavicom.gui.message.UIMessageEngine;
import clavicom.gui.utils.UITranslucentFrame;

public class testTranslucency
{
	public static void main(String[] args)
	{
		try
		{
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
			
			profil.loadProfileCommandSetName();
			profil.loadProfileLanguageUIName();
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
			
			
			
			
			
//			/*CLastWordEngine lasWordEngine = */new CLastWordEngine(keyboard,levelEngine);
//			/*CPredictionEngine predictionEngine = */new CPredictionEngine(keyboard,levelEngine,profil.getPreferedWords());
//			/*CCommandEngine commandEngine = */new CCommandEngine( keyboard, levelEngine );
			// on simule l'appuis sur une touche
//			CKeyGroup group = keyboard.getKeyGroup( 0 );
//			CKeyList list = group.getkeyList( 0 );
//			CKeyCharacter keyCharacter = (CKeyCharacter)list.getKeyKeyboard( 0 );
//			
//			
//			PanelOptionKeyCharacter panelOptionCharacter = new PanelOptionKeyCharacter( keyCharacter, CCommandSet.GetInstance()  );
//			JScrollPane sp = new JScrollPane( panelOptionCharacter );
			
			
		
			//panel.setBackground(Color.BLUE);

			
//			UIMovingPanel panel3 = new UIMovingPanel(frame);
//			panel.add(panel3);
//			panel3.setBackground(Color.BLUE);
//			panel3.setEditable(true);
			
//			UIKeyboard uiKeyboard = new UIKeyboard(frame, keyboard,levelEngine );
//			uiKeyboard.setPreferredSize(new Dimension(100,100));
//			
//			uiKeyboard.setEditable(true);
//			
//			uiKeyboard.edit();

//			panel.add(uiKeyboard);
			
			//panel.add(uiKeyboard, BorderLayout.CENTER);
			// JFrame frame = new JFrame ();
			
			System.setProperty("sun.java2d.noddraw", "true");
	        
	        UITranslucentFrame frame = new UITranslucentFrame(.5f);
	        
	        //ClockFace face = new ClockFace(new Dimension(800, 800));
	        JPanel test = new JPanel();
	        test.setBackground(Color.BLUE);
	        frame.setPreferredSize(new Dimension(400,400));
	        frame.add(test);
	        
	        frame.pack();
	        
	        frame.setLocation(400,400);

	        frame.setVisible(true);
	        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
			//frame.setFocusableWindowState(false);
			//frame.setFocusable(false);
			
			//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

