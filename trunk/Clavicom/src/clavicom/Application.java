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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import clavicom.core.engine.CCommandEngine;
import clavicom.core.engine.CLastWordEngine;
import clavicom.core.engine.CLauncherEngine;
import clavicom.core.engine.CLevelEngine;
import clavicom.core.engine.CPredictionEngine;
import clavicom.core.engine.CStringsEngine;
import clavicom.core.engine.dictionary.CDictionary;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.command.shortcutSet.CShortcutSet;
import clavicom.core.message.CMessageEngine;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.DefilementEngine;
import clavicom.gui.engine.DefilementKeyEngine;
import clavicom.gui.engine.UIKeyCreationEngine;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.message.UIMessageEngine;
import clavicom.gui.splashscreen.UISplashScreen;
import clavicom.gui.windows.UIKeyboardFrame;

public class Application
{

	//--------------------------------------------------------- CONSTANTES --//
	private static final int SPLASH_WAIT = 100;	// Temps d'attente entre deux messages (en ms)
	private static final int SPLASH_WAIT_TOTAL_MIN = 2000;	// Temps d'attente minimum total
	//---------------------------------------------------------- VARIABLES --//

	//----------------------------------------------------------------
	// Outils
	//----------------------------------------------------------------
	
	//----------------------------------------------------------------
	// GUI
	//----------------------------------------------------------------	
	// Outils
	@SuppressWarnings("unused")
	private static UIMessageEngine 		toolMessageEngine;		// Moteur de messages
	
	@SuppressWarnings("unused")
	private static CLevelEngine			toolLevelEngine;		// Moteur de niveau
	
	@SuppressWarnings("unused")
	private static CCommandEngine		toolCommandEngine;		// Moteur de commandes
	
	@SuppressWarnings("unused")
	private static CLastWordEngine		toolLastWordEngine;		// Moteur de derniers mots
	
	@SuppressWarnings("unused")
	private static CLauncherEngine		toolLauncherEngine;		// Moteur de lancement d'applications
	
	@SuppressWarnings("unused")
	private static CPredictionEngine	toolPredictionEngine;	// Moteur de prediction de mots
	
	@SuppressWarnings("unused")
	private static ClickEngine	toolClickEngine;				// Moteur des clicks
	
	@SuppressWarnings("unused")
	private static CStringsEngine	toolStringEngine;			// Moteur de chaines
	
	@SuppressWarnings("unused")
	private static DefilementEngine toolDefilEngine;			// Moteur de Defilement
	
	@SuppressWarnings("unused")
	private static UIKeyboard uiKeyboard;						// panel d'interface du clavier
	
	@SuppressWarnings("unused")
	private static DefilementKeyEngine toolKeyboardDefilEngine;	// defilement du keyboard
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	
	//----------------------------------------------------------- METHODES --//	
	/**
	 * Point d'entrée de l'application
	 */
	public static void main(String[] args)
	{
		try
		{	
			LaunchApplication();
		}
		catch (Exception e)
		{
			if(toolClickEngine != null)
			{
				toolClickEngine.FinishMouseHook();
			}
			e.printStackTrace();
		}
	}
		
	public static void LaunchApplication()
	{
		// Initialisation des propriétés graphiques
		initGraphicProperties();
		
		// Chargement du moteur de messages
		// -> pas de splash screen car on en a besoin en cas d'erreur
		loadMessageEngine();
		
		// Chargement du splashscreen
		UISplashScreen splash = new UISplashScreen(CFilePaths.getSplashScreenFile(), SPLASH_WAIT, SPLASH_WAIT_TOTAL_MIN);
		
		// Chargement du gestionnaires de paramètres
		splash.newStep("Loading parameters...");
		loadSettings();
		
		// Chargement du profil
		splash.newStep("Loading profile...");
		loadAnyProfile();
		
		// -> ICI, un profil est chargé (le dernier ou celui par défaut)
		
		// Chargement du dictionnaire
		splash.newStep("Loading dictionnary...");
		loadDictionnary();
		
		// Chargement du moteur de niveaux
		splash.newStep("Loading level engine...");
		loadLevelEngine();
		
		// Chargement du moteur de lancements d'applications
		splash.newStep("Loading launcher engine...");
		loadLauncherEngine();
		
		// Chargement du moteur de commandes
		splash.newStep("Loading command engine...");
		loadCommandEngine();
		
		// Chargment du moteur de derniers mots
		splash.newStep("Loading last word engine...");
		loadLastWordEngine();

		// Chargement du moteur de prédiction
		splash.newStep("Loading prediction engine...");
		loadPredictionEngine();

		// Chargement du moteur de prédiction
		splash.newStep("Loading key creation engine...");
		loadKeyCreationEngine();
		
		// Chargement du moteur de click
		splash.newStep("Loading click engine...");
		loadClickEngine();
		
		// Chargement du moteur de defilement
		splash.newStep("Loading defilement engine...");
		loadDefilementEngine();
		
		
		
		
		
		// <TEMPORAIRE>
		// TODO --> Création des fenetres,...
		uiKeyboard = new UIKeyboard(CProfil.getInstance().getKeyboard(), toolLevelEngine);
		
		// Chargement du moteur de defilement du keyboard
		// Il a besoin que uiKeyboard soit construit
		splash.newStep("Loading keyboard defilement engine...");
		loadKeyboardDefilementEngine();
		
		// Création des fenêtres
		splash.newStep("Creating windows...");
		
		UIKeyboardFrame mainFrame = new UIKeyboardFrame(uiKeyboard);
		
		mainFrame.setSize(800,400);
		mainFrame.edit(true);
		
		// listener pour supprimer le hook a la fermeture de l'application
		mainFrame.addWindowListener(new WindowListener(){

			public void windowActivated(WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			public void windowClosed(WindowEvent e)
			{
				
			}

			public void windowClosing(WindowEvent e)
			{
				if(toolClickEngine != null)
				{
					toolClickEngine.FinishMouseHook();
				}
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
		
		splash.newStep("Load complete !");
		splash.close();
		
		mainFrame.setVisible(true);
		
		// </TEMPORAIRE>
	}
	
	

	//--------------------------------------------------- METHODES PRIVEES --//
	//-----------------------------------------------------------------------
	// Chargement
	//-----------------------------------------------------------------------
	
	
	

	/**
	 * Initialise les propriétés graphiques
	 */
	private static void initGraphicProperties()
	{
		System.setProperty("sun.java2d.noddraw", "true");
	}
	
	/**
	 * Charge le moteur d'affichage de messages
	 */
	private static void loadMessageEngine()
	{
		CMessageEngine.createInstance();
		toolMessageEngine = new UIMessageEngine();
	}
	
	private static void loadSettings()
	{
		try
		{
			CSettings.loadSettings(CFilePaths.getConfigFileFolder());
		}
		catch (Exception ex)
		{
			CMessageEngine.newFatalError( 		"Erreur lors du chargement du fichier \"" + 
												CFilePaths.getConfigFileFolder()
												+ "\"",
									 			"Remplacez ce fichier ou réinstallez l'application.");
		}	
	}
	
	/**
	 * Charge un profile (celui qui aura servi à créer l'instance du profil)
	 * @throws Exception
	 */
	private static void loadProfile() throws Exception
	{		
		// Récupération de la langue du profil
		CProfil.getInstance().loadProfileLanguageUIName();

		
		// Chargement de la langue du profil
		UIString.LoadUIStringFile(  CFilePaths.getLanguagesUIFolder() + 
										CProfil.getInstance().getLangueUI().getLanguageFileName());
		
		// Récupération du nom du commandset
		CProfil.getInstance().loadProfileCommandSetName();
		
		// Chargement du commandset
		CCommandSet.CreateInstance( CFilePaths.getCommandSetsFolder() +
										CProfil.getInstance().getCommandSetName().getcommandSetName());
		
		// Récupération du nom du shortcutset
		CProfil.getInstance().loadProfileShortCutName();
		
		// Chargement du commandset
		CShortcutSet.CreateInstance(	CFilePaths.getShortcutSetsFolder() + 
											CProfil.getInstance().getShortcutSetName().getShortCutName());
		
		// Chargement du reste du profil
		CProfil.getInstance().loadProfile();
	}
	
	/**
	 * Charge un profil. Essaye de charger le dernier profil, et en cas d'echec le 
	 * profil par défaut.
	 *
	 */
	private static void loadAnyProfile()
	{
		// On tente de charger le dernier profil
		CProfil.createInstance(CSettings.getLastProfilePath());
		
		try
		{
			loadProfile();
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	"Impossible de charger le dernier profil : le profil par défaut va être chargé.",
										ex.getMessage());
			
			try
			{
				CProfil.createInstance( CFilePaths.getDefaultProfileFolder() +
										CSettings.getDefaultProfileName());
			}
			catch (Exception ex2)
			{
				CMessageEngine.newFatalError(	"Impossible de charger le profil par défaut",
												ex2.getMessage());				
			}
		}
	}
	
	/**
	 * Charge le dictionnaire du profil
	 *
	 */
	private static void loadDictionnary()
	{
		try
		{
			CDictionary.createInstance( CProfil.getInstance().getDictionnaryName(),
														CProfil.getInstance().getPreferedWords());
		}
		catch (Exception ex)
		{
			CMessageEngine.newFatalError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_DICTIONNARY_1") +
											CProfil.getInstance().getDictionnaryName().getDictionaryName() +
											UIString.getUIString("MSG_MAIN_CANT_LOAD_DICTIONNARY_2"),
											ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de niveau
	 */
	private static void loadLevelEngine()
	{
		try
		{
			toolLevelEngine  = new CLevelEngine(CProfil.getInstance().getKeyboard());
		}
		catch (Exception ex)
		{
			CMessageEngine.newFatalError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_LEVEL_ENGINE_1"),
											ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de commandes
	 */
	private static void loadCommandEngine()
	{
		try
		{
			toolCommandEngine  = new CCommandEngine(	CProfil.getInstance().getKeyboard(),
														toolLevelEngine);
		}
		catch (Exception ex)
		{
			CMessageEngine.newFatalError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_COMMAND_ENGINE_1"),
											ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de derniers mots
	 */
	private static void loadLastWordEngine()
	{
		try
		{
			toolLastWordEngine = new CLastWordEngine(	CProfil.getInstance().getKeyboard(),
														toolLevelEngine);
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_LAST_WORD_ENGINE_1"),
										ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de lancement d'application
	 */
	private static void loadLauncherEngine()
	{
		try
		{
			toolLauncherEngine = new CLauncherEngine(CProfil.getInstance().getKeyboard());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_LAUNCHER_ENGINE_1"),
										ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de lancement d'application
	 */
	private static void loadPredictionEngine()
	{
		try
		{
			toolPredictionEngine = new CPredictionEngine(	CProfil.getInstance().getKeyboard(),
															toolLevelEngine,
															CProfil.getInstance().getPreferedWords());
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_PREDICTION_ENGINE_1"),
										ex.getMessage());
		}
	}
	
	private static void loadKeyCreationEngine()
	{
		UIKeyCreationEngine.createInstance();
	}
	
	/**
	 * Chargement du moteur de click
	 */
	private static void loadClickEngine()
	{
		try
		{
			toolClickEngine = new ClickEngine( CFilePaths.getToolDllMouseHookPath() );
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_CLICK_ENGINE_1"),
										ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de defilement
	 */
	private static void loadDefilementEngine()
	{
		try
		{
			toolDefilEngine = new DefilementEngine( toolClickEngine );
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_DEFIL_ENGINE_1"),
										ex.getMessage());
		}
	}
	
	/**
	 * Chargement du moteur de defilement du keyboard
	 */
	private static void loadKeyboardDefilementEngine()
	{
		try
		{
			toolKeyboardDefilEngine = new DefilementKeyEngine( uiKeyboard, toolClickEngine, toolDefilEngine, false );
		}
		catch (Exception ex)
		{
			CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_KEYBOARD_DEFIL_ENGINE_1"),
										ex.getMessage());
		}
	}

}
