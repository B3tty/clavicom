/*-----------------------------------------------------------------------------+

			Filename			: Application.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom

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

package clavicom;

import javax.swing.JFrame;

import clavicom.core.message.CMessageEngine;

import clavicom.core.profil.CProfil;
import clavicom.gui.message.UIMessageEngine;

public class Application
{

	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	//----------------------------------------------------------------
	// Outils
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	// GUI
	//----------------------------------------------------------------
	private static UIMessageEngine uiMessageEngine;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	//----------------------------------------------------------- METHODES --//	
	public static void main(String[] args)
	{
		//----------------------------------------------------------------
		// Gestion des messages
		//----------------------------------------------------------------
		CMessageEngine.createInstance();
		uiMessageEngine = new UIMessageEngine();
		
		//----------------------------------------------------------------
		// Chargement des différents outils
		//----------------------------------------------------------------
		
		// Gestionnaire des paramètres de l'application		
		try
		{
			CSettings.loadSettings(CFilePaths.getConfigFile());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError( 	"Can't load config file " + 
										CFilePaths.getConfigFile(),
									 	"Try to find this file or reinstall application.");
		}
		
		// Chargement du profil
		try
		{
			CProfil.createInstance(CSettings.getDefaultProfilePath());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());
		}
		
		
//		// Chargement des chaînes de l'application
//
//		//		 Chargement des UIString et shortcutset
//		UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
//		CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
//		CShortcutSet.CreateInstance("Ressources\\Application\\ShortcutSets\\default.css");
//		
//		// Chemins
//		String input = "Ressources\\Temp\\profil2.xml";
//		
//		// Chargement du profil
//		CProfil.createInstance(input);
//		CProfil profil = CProfil.getInstance();
//		
//		CKeyboard keyboard = profil.getKeyboard();
//		
//		// Chargement du commandEngine
//		CLevelEngine levelEngine = new CLevelEngine( keyboard );
//		
//		CDictionary dictionary = null;
//		try
//		{
//			//dictionary = new CDictionary(profil.getDictionnaryName(),profil.getPreferedWords());
//		}
//		catch (Exception ex)
//		{
//			System.out.println("argh1 !!!");
//			Thread.sleep( 3000 );
//			
//			System.out.println("argh2 !!!");
//			ex.printStackTrace();
//		}
//		
//		
//		
//		/*CLastWordEngine lasWordEngine = */new CLastWordEngine(keyboard,levelEngine);
//		/*CPredictionEngine predictionEngine = */new CPredictionEngine(keyboard,levelEngine,dictionary,profil.getPreferedWords());
//		/*CCommandEngine commandEngine = */new CCommandEngine( keyboard, levelEngine );
//		
//		// on simule l'appuis sur une touche
////			CKeyGroup group = keyboard.getKeyGroup( 0 );
////			CKeyList list = group.getkeyList( 0 );
////			CKeyCharacter keyCharacter = (CKeyCharacter)list.getKeyKeyboard( 0 );
////			
////			
////			PanelOptionKeyCharacter panelOptionCharacter = new PanelOptionKeyCharacter( keyCharacter, CCommandSet.GetInstance()  );
////			JScrollPane sp = new JScrollPane( panelOptionCharacter );
//		
//		
//		
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		
//		UIKeyboard uiKeyboard = new UIKeyboard(keyboard);
//		uiKeyboard.setPreferredSize(new Dimension(100,100));
//		
//		uiKeyboard.edit();
//		
//		panel.add(uiKeyboard, BorderLayout.CENTER);
		
		JFrame frame = new JFrame ();
		frame.setSize(900,400);
		
		frame.setAlwaysOnTop(true);
		//frame.setFocusableWindowState(false);
		frame.setFocusable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
