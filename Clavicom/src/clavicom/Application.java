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

import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.message.CMessageEngine;

import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;
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
		// Gestion des messages d'erreur
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
			CMessageEngine.newError( 		"Impossible de charger le fichier \"" + 
											CFilePaths.getConfigFile()
											+ "\"",
									 		"Remplacez ce fichier ou reinstallez l'application.");
			
			// TODO : charger le profil par défaut
		}	
		
		// Création du profil
		CProfil.createInstance(CSettings.getDefaultProfilePath());
		
		// Récupération de la langue du profil
		try
		{
			CProfil.getInstance().loadProfileLanguageUIName();
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());			
		}
		
		// Chargement de la langue du profil
		try
		{
			UIString.LoadUIStringFile(  CFilePaths.getLanguagesUI() + 
										CProfil.getInstance().getLangueUI().getLanguageFileName());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());			
		}
		
		// Récupération du nom du commandset
		try
		{
			CProfil.getInstance().loadProfileCommandSetName();
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());
			
			// TODO : charger le profil par défaut
		}
		
		// Chargement du commandset
		try
		{
			CCommandSet.CreateInstance( CFilePaths.getCommandSets() +
										CProfil.getInstance().getCommandSetName().getcommandSetName());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());
			
			// TODO : charger le profil par défaut
		}
		
		// Récupération du nom du shortcutset
		try
		{
			CProfil.getInstance().loadProfileShortCutName();
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());
			
			// TODO : charger le profil par défaut
		}
		
		// Chargement du commandset
		try
		{
			CShortcutSet.CreateInstance(	CFilePaths.getShortcutSets() + 
											CProfil.getInstance().getShortcutSetName().getShortCutName());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());
			
			// TODO : charger le profil par défaut
		}
		
		// Chargement du reste du profil
		try
		{
			CProfil.getInstance().loadProfile();
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(ex.getMessage());
		}

		
		
//
//		//		 Chargement des UIString et shortcutset
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
