/*-----------------------------------------------------------------------------+

			Filename			: UIKeyboardFrame.java
			Creation date		: 15 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.windows

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

package clavicom.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeySound;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.profil.CProfil;
import clavicom.gui.configuration.UIFrameModificationProfil;
import clavicom.gui.edition.key.UIPanelOptionKeyCharacter;
import clavicom.gui.edition.key.UIPanelOptionKeyLauncher;
import clavicom.gui.edition.key.UIPanelOptionKeyShortCut;
import clavicom.gui.edition.key.UIPanelOptionKeySound;
import clavicom.gui.edition.key.UIPanelOptionKeyString;
import clavicom.gui.edition.key.UIPanelOptionKeyboardKey;
import clavicom.gui.edition.key.UIPanelOptionOneLevelKey;
import clavicom.gui.edition.keyboard.UIKeyCreationToolbar;
import clavicom.gui.engine.ClickTemporiseEngine;
import clavicom.gui.engine.DefilementEngine;
import clavicom.gui.engine.DefilementKeyEngine;
import clavicom.gui.engine.click.ClickEngine;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIGridModifier;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.keyboard.keyboard.UIToolbarPanel;
import clavicom.gui.language.UIString;
import clavicom.gui.levelmanager.UILevelManagerFrame;
import clavicom.gui.listener.UIGridChangedListener;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentDialog;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TNavigationType;

//import com.sun.jna.examples.WindowUtils;


public class UIToolbarFrame extends UITranslucentDialog 
implements
UIGridChangedListener
{


	//--------------------------------------------------------- CONSTANTES --//
	private final int PANEL_TOOLBAR_RIGHT_SPACE = 5;
	private final int PANEL_TOOLBAR_LEFT_SPACE = 5;
	private final int PANEL_TOOLBAR_BOTTOM_SPACE = 5;
	private final int PANEL_TOOLBAR_UP_SPACE = 5;
	
	private final int PREFERED_WIDTH = 680;		// Largeur de la barre
	private final int PREFERED_HEIGHT = 110;	// Hauteur de la barre
	
	private final int PREFERED_SPACE_WITH_KEYBOARD = 5;	// Espacement avec le keyboard
	
	private final int PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS = 5;
	
	private final  String[] UNCLASSED_KEY_CHOICES = {	UIString.getUIString("LB_EDITION_KEY_UNCLASSED_CLASS"),		// Trier
														UIString.getUIString("LB_EDITION_KEY_UNCLASSED_IGNORE"), 	// Effacer
														UIString.getUIString("LB_EDITION_KEY_UNCLASSED_CANCEL"),};	// Annuler


	//---------------------------------------------------------- VARIABLES --//
	@SuppressWarnings("unused")
	private boolean isEdited; 	// Indique si on est en édition
	
	// Frame de modification de touche
	UIModificationKeyDialog frameOptionKeyOneLevel;
	UIModificationKeyDialog frameOptionKeyKeyboard;
	UIModificationKeyDialog frameOptionKeyCharacter;
	UIModificationKeyDialog frameOptionKeyLauncher;
	UIModificationKeyDialog frameOptionKeySound;
	UIModificationKeyDialog frameOptionKeyShortcut;
	UIModificationKeyDialog frameOptionKeyString;
	
	// Frame d'options de l'application
	UIFrameModificationProfil frameOptionApplication;
	
	// Frame d'edition des niveaux
	UILevelManagerFrame frameLevelManager;
	
	// Panel principal
	UIMovingPanel mainPanel;
	UIToolbarPanel mainPanelBg;
	
	// Sous panels
	JPanel panelBoutons;	// Contient le bouton fermer mode édition et paramètres
	
	// Sous panels
	UIKeyboard panelKeyboard;			// Panel contenant le clavier
	UIKeyboardFrame frameKeyboard;		// Fenêtre contenant le clavier
	UIKeyCreationToolbar panelToolbar;	// Panel contenant les touches d'edition
	
	// Panels de modification de touche
	UIPanelOptionOneLevelKey panelOptionKeyOneLevel;
	UIPanelOptionKeyboardKey panelOptionKeyKeyboard;
	UIPanelOptionKeyCharacter panelOptionKeyCharacter;
	UIPanelOptionKeyLauncher panelOptionKeyLauncher;
	UIPanelOptionKeySound panelOptionKeySound;
	UIPanelOptionKeyShortCut panelOptionKeyShortcut;
	UIPanelOptionKeyString panelOptionKeyString;
	
	// Panel de modification de grid
	UIGridModifier gridModifier;
	
	// Engine
	DefilementKeyEngine defilEngine;
	
	// Boutons
	JButton btFermerModeEdition, btOptionsApplication, btEditionKey, btOpenLevelManager;
	
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIToolbarFrame(UIKeyboardFrame frameKeyboard)
	{
		super();
		
		// Recopie des attributs
		this.frameKeyboard = frameKeyboard;
		this.panelKeyboard = frameKeyboard.getUIKeyboard();
		
		// Création des objets
		createObjects();
		
		// Application des layouts
		setAllLayouts();		
		
		// Mise en place des panels
		setPanels();
		
		// Initialisation des objets
		initFrame();
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	/**
	 * Spécifie tous les layouts de l'application
	 */
	private void setAllLayouts()
	{
		// ------------- Layout du panel de bouton ----------------------------
		panelBoutons.setLayout(new GridLayout(4,1));
		
		mainPanelBg.setLayout(new GridLayout());
		// -------------- Layout du panel principal ----------------------------
		GridBagLayout gbLayoutMain = new GridBagLayout();
		
		mainPanel.setLayout(gbLayoutMain);
		
		// Contraintes du panel avec la liste d'outils
		GridBagConstraints gbConstToolbar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            70,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(PANEL_TOOLBAR_UP_SPACE, PANEL_TOOLBAR_LEFT_SPACE, PANEL_TOOLBAR_BOTTOM_SPACE, PANEL_TOOLBAR_RIGHT_SPACE),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelToolbar, gbConstToolbar);

		// Contraintes du panel de grid modifier
		GridBagConstraints gbConstGrid = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            10,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(PANEL_TOOLBAR_UP_SPACE, 0, PANEL_TOOLBAR_BOTTOM_SPACE, PANEL_TOOLBAR_RIGHT_SPACE),		
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(gridModifier, gbConstGrid);
		
		// Contraintes du panel de boutons
		GridBagConstraints gbConstBoutons = new GridBagConstraints (	
				2,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            20,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(PANEL_TOOLBAR_UP_SPACE, 0, PANEL_TOOLBAR_BOTTOM_SPACE, PANEL_TOOLBAR_RIGHT_SPACE),		
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelBoutons, gbConstBoutons);
		
		// -------------- Layout de la frame d'options -------------------------
		// panelModification.setLayout(new BorderLayout());		
	}
	
	/**
	 * Créé tous les objets nécessaires à la fenêtre
	 * @param uiKeyboard 
	 *
	 */
	private void createObjects()
	{		
		// Création des panels
		mainPanel = new UIMovingPanel(this);
		mainPanelBg = new UIToolbarPanel(Color.GRAY.brighter());
		panelBoutons = new JPanel();
		
		// Panels de modification de touche
		panelOptionKeyOneLevel = new UIPanelOptionOneLevelKey();
		panelOptionKeyKeyboard = new UIPanelOptionKeyboardKey();
		panelOptionKeyCharacter = new UIPanelOptionKeyCharacter();
		panelOptionKeyLauncher = new UIPanelOptionKeyLauncher();
		panelOptionKeySound = new UIPanelOptionKeySound();
		panelOptionKeyShortcut = new UIPanelOptionKeyShortCut();
		panelOptionKeyString = new UIPanelOptionKeyString();
		
		// Création des frames
		frameOptionKeyOneLevel = new UIModificationKeyDialog(panelOptionKeyOneLevel);
		frameOptionKeyKeyboard = new UIModificationKeyDialog(panelOptionKeyKeyboard);
		frameOptionKeyCharacter = new UIModificationKeyDialog(panelOptionKeyCharacter);
		frameOptionKeyLauncher = new UIModificationKeyDialog(panelOptionKeyLauncher);
		frameOptionKeySound = new UIModificationKeyDialog(panelOptionKeySound);
		frameOptionKeyShortcut = new UIModificationKeyDialog(panelOptionKeyShortcut);
		frameOptionKeyString = new UIModificationKeyDialog(panelOptionKeyString);
		
		
		frameOptionApplication = new UIFrameModificationProfil( panelKeyboard);
		
		frameLevelManager = new UILevelManagerFrame();
		frameLevelManager.setUIKeyboard(panelKeyboard);
		
		panelBoutons.setLayout(new GridLayout(3,1,0,PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS));
		
		// Création des boutons 
		btFermerModeEdition = new JButton(UIString.getUIString("LB_EDITION_CLOSE_EDITION"));
		btOptionsApplication = new JButton(UIString.getUIString("LB_EDITION_OPEN_OPTIONS"));
		btEditionKey = new JButton(UIString.getUIString("LB_EDITION_EDIT_KEY"));
		btOpenLevelManager = new JButton(UIString.getUIString("LB_EDITION_OPEN_LEVEL_MANAGER"));
		
		btFermerModeEdition.setMinimumSize(new Dimension(0,0));
		btOptionsApplication.setMinimumSize(new Dimension(0,0));
		btEditionKey.setMinimumSize(new Dimension(0,0));
		btOpenLevelManager.setMinimumSize(new Dimension(0,0));
		
		btFermerModeEdition.setPreferredSize(new Dimension(0,0));
		btOptionsApplication.setPreferredSize(new Dimension(0,0));
		btEditionKey.setPreferredSize(new Dimension(0,0));
		btOpenLevelManager.setPreferredSize(new Dimension(0,0));
		
		// Ajout des tooltips
		btFermerModeEdition.setToolTipText(UIString.getUIString("LB_EDITION_CLOSE_EDITION_TOOLTIP"));
		btOptionsApplication.setToolTipText(UIString.getUIString("LB_EDITION_OPEN_OPTIONS_TOOLTIP"));
		btEditionKey.setToolTipText(UIString.getUIString("LB_EDITION_EDIT_KEY_TOOLTIP"));
		btOpenLevelManager.setToolTipText(UIString.getUIString("LB_EDITION_OPEN_LEVEL_MANAGER_TOOLTIP"));
		
		panelBoutons.add(btFermerModeEdition);
		panelBoutons.add(btOptionsApplication);
		panelBoutons.add(btOpenLevelManager);
		panelBoutons.add(btEditionKey);
		
		panelToolbar = new UIKeyCreationToolbar(	CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor());
		
		// Controleur de grid
		gridModifier = new UIGridModifier(panelKeyboard.getMagnetGrid());
		gridModifier.addGridChangedListener(this);
		
		gridModifier.setValues(10, 10, true);
	}
	
	/**
	 * Mets en place tous les panels
	 */
	private void setPanels()
	{	
		// Frame principale
		mainPanel.add(panelToolbar);
		mainPanel.add(gridModifier);
		mainPanel.add(panelBoutons);
		mainPanel.add(panelKeyboard);
		
		mainPanelBg.setBackground(Color.GREEN);
		
		mainPanelBg.add(mainPanel);
		add(mainPanelBg);
	}
	
	/**
	 * Initialise les options des objets de la fenêtre
	 */
	private void initFrame()
	{		
		// On définit de la transparence pour tous
		mainPanel.setOpaque(false);
		panelBoutons.setOpaque(false);
		panelToolbar.setOpaque(false);
		gridModifier.setOpaque(false);
		
		// On autorise le resize de la fenêtre
		mainPanel.setEditable(true);
		
		// Affectation de la taille
		setBounds(0, 0, PREFERED_WIDTH, PREFERED_HEIGHT);
		
		// Ajout des actions aux boutons
		btEditionKey.setAction(new BtEditionKeyAction(UIString.getUIString("LB_EDITION_EDIT_KEY")));
		btFermerModeEdition.setAction(new BtFermerModeEditionAction(UIString.getUIString("LB_EDITION_CLOSE_EDITION")));
		btOptionsApplication.setAction(new BtOptionsApplicationAction(UIString.getUIString("LB_EDITION_OPEN_OPTIONS")));
		btOpenLevelManager.setAction(new BtOpenLevelManagerAction(UIString.getUIString("LB_EDITION_OPEN_LEVEL_MANAGER")));
		
		// Initialisation des tailles des fenêtres
		
		frameOptionKeyOneLevel.setSize(410,300);
		frameOptionKeyKeyboard.setSize(410,175);
		frameOptionKeyCharacter.setSize(540,540);
		frameOptionKeyLauncher.setSize(410,350);
		frameOptionKeySound.setSize(410,350);
		frameOptionKeyShortcut.setSize(410,400);
		frameOptionKeyString.setSize(410,360);
		
		// Frame d'options de l'application
		frameOptionApplication.setSize(new Dimension (550,620));
		frameOptionApplication.setResizable(false);
		frameOptionApplication.setModal(true);
		frameOptionApplication.setVisible(false);
		
		// Initialisation des composants
		btEditionKey.setEnabled(false);
		
		// Pas toujours au sommet		
		setAlwaysOnTop(false);
	}
	
	// ------- Actions des boutons
	protected class BtEditionKeyAction extends AbstractAction
	{
		public BtEditionKeyAction(String caption)
		{
			super(caption);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			onClickBtEditionKey();
		}
	}
	
	protected class BtOptionsApplicationAction extends AbstractAction
	{
		public BtOptionsApplicationAction(String caption)
		{
			super(caption);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			frameOptionApplication.setVisible(true);
		}
	}
	
	protected class BtOpenLevelManagerAction extends AbstractAction
	{
		public BtOpenLevelManagerAction(String caption)
		{
			super(caption);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			frameLevelManager.setVisible(true);
		}
	}
	
	private void stopDefilMode()
	{
		ClickEngine.getInstance().mouseHookPause();
		
		DefilementEngine.getInstance().stopDefilement();
		
		DefilementKeyEngine.getInstance().stopKeyDefilEngine();
		
		ClickTemporiseEngine.getInstance().stopClickTempoEngine();
	}

	private void startDefilMode()
	{
		ClickEngine.getInstance().mouseHookResume();
		
		DefilementEngine.getInstance().startDefilement();
		
		DefilementKeyEngine.getInstance().startKeyDefilEngine();
	}
	
	private void startClickTempoMode()
	{
		ClickEngine.getInstance().mouseHookResume();
		
		DefilementEngine.getInstance().startDefilement();
		
		ClickTemporiseEngine.getInstance().startClickTempoEngine();	
	}
	
	protected void onClickBtEditionKey()
	{
		if(panelKeyboard.getSelectedKeys().size() > 1)
		// Modification multiple
		{
			frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_MULTIPLE"));
			
			panelOptionKeyKeyboard.setValuesKeyKeyboard(panelKeyboard.getSelectedKeys());
			
			frameOptionKeyKeyboard.setVisible(true);
		}
		else
		// Modification unique
		{
			// Récupération de la touche
			CKey selectedKey = panelKeyboard.getSelectedKeys().get(0).getCoreKey();
			
			// On sélectionne le bon panel
			if (selectedKey instanceof CKeyCharacter)
			{
				frameOptionKeyCharacter.setTitle(UIString.getUIString("FR_OPTIONS_KEYCHARACTER"));
				panelOptionKeyCharacter.setValuesKeyCharacter((CKeyCharacter)selectedKey);
				frameOptionKeyCharacter.setVisible(true);
			}
			else if (selectedKey instanceof CKeyPrediction)
			{
				frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_KEYPREDICTION"));
				panelOptionKeyKeyboard.setValuesKeyKeyboard((CKeyPrediction)selectedKey);
				frameOptionKeyKeyboard.setVisible(true);
			}
			else if (selectedKey instanceof CKeyLastWord)
			{
				frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_KEYLASTWORD"));
				panelOptionKeyKeyboard.setValuesKeyKeyboard((CKeyLastWord)selectedKey);
				frameOptionKeyKeyboard.setVisible(true);
			}
			else if (selectedKey instanceof CKeyLauncher)
			{
				frameOptionKeyLauncher.setTitle(UIString.getUIString("FR_OPTIONS_KEYLAUNCHER"));
				panelOptionKeyLauncher.setValuesKeyLauncher((CKeyLauncher)selectedKey);
				frameOptionKeyLauncher.setVisible(true);
			}
			else if (selectedKey instanceof CKeySound)
			{
				frameOptionKeySound.setTitle(UIString.getUIString("FR_OPTIONS_KEYSOUND"));
				panelOptionKeySound.setValuesKeySound((CKeySound)selectedKey);
				frameOptionKeySound.setVisible(true);
			}
			else if (selectedKey instanceof CKeyLevel)
			{
				CKeyLevel selectedKeyLevel = (CKeyLevel)selectedKey;
				frameOptionKeyOneLevel.setTitle(UIString.getUIString("FR_OPTIONS_KEYLEVEL") + 
										selectedKeyLevel.GetLevel().toString());
				panelOptionKeyOneLevel.setValuesKeyOneLevel(selectedKeyLevel);
				frameOptionKeyOneLevel.setVisible(true);
			}
			else if (selectedKey instanceof CKeyShortcut)
			{
				frameOptionKeyShortcut.setTitle(UIString.getUIString("FR_OPTIONS_KEYSHORTCUT"));
				panelOptionKeyShortcut.setValuesKeyShortcut((CKeyShortcut)selectedKey);
				frameOptionKeyShortcut.setVisible(true);
			}
			else if (selectedKey instanceof CKeyString)
			{
				frameOptionKeyString.setTitle(UIString.getUIString("FR_OPTIONS_KEYSTRING"));
				panelOptionKeyString.setValuesKeyString((CKeyString)selectedKey);
				frameOptionKeyString.setVisible(true);
			}
			else if (selectedKey instanceof CKeyClavicom)
			{
				CKeyClavicom selectedKeyClavicom = (CKeyClavicom)selectedKey;
				frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_KEYCLAVICOM") + 
										TKeyClavicomActionType.getString(selectedKeyClavicom.getAction()));
				panelOptionKeyKeyboard.setValuesKeyKeyboard((CKeyClavicom)selectedKey);
				
				frameOptionKeyKeyboard.setVisible(true);
			}
		}
		
	}

	public void gridChanged()
	{
		panelKeyboard.recreateGrid();
		panelKeyboard.repaint();		
	}

	public void gridUsed(boolean used)
	{		
		panelKeyboard.useMagnetGrid(used);
		panelKeyboard.repaint();
	}
	
	public void enableEditionButton(boolean enabled)
	{
		btEditionKey.setEnabled(enabled);
	}
	
	public void openEditionFrame()
	{
		if(panelKeyboard.getSelectedKeys().size() > 1)
		// Modification multiple
		{
			frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_MULTIPLE"));
			
			panelOptionKeyKeyboard.setValuesKeyKeyboard(panelKeyboard.getSelectedKeys());
			
			frameOptionKeyKeyboard.setVisible(true);
		}
		else
		// Modification unique
		{
			// Récupération de la touche
			CKey selectedKey = panelKeyboard.getSelectedKeys().get(0).getCoreKey();
			
			// On sélectionne le bon panel
			if (selectedKey instanceof CKeyCharacter)
			{
				frameOptionKeyCharacter.setTitle(UIString.getUIString("FR_OPTIONS_KEYCHARACTER"));
				panelOptionKeyCharacter.setValuesKeyCharacter((CKeyCharacter)selectedKey);
				frameOptionKeyCharacter.setVisible(true);
			}
			else if (selectedKey instanceof CKeyPrediction)
			{
				frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_KEYPREDICTION"));
				panelOptionKeyKeyboard.setValuesKeyKeyboard((CKeyPrediction)selectedKey);
				frameOptionKeyKeyboard.setVisible(true);
			}
			else if (selectedKey instanceof CKeyLastWord)
			{
				frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_KEYLASTWORD"));
				panelOptionKeyKeyboard.setValuesKeyKeyboard((CKeyLastWord)selectedKey);
				frameOptionKeyKeyboard.setVisible(true);
			}
			else if (selectedKey instanceof CKeyLauncher)
			{
				frameOptionKeyLauncher.setTitle(UIString.getUIString("FR_OPTIONS_KEYLAUNCHER"));
				panelOptionKeyLauncher.setValuesKeyLauncher((CKeyLauncher)selectedKey);
				frameOptionKeyLauncher.setVisible(true);
			}
			else if (selectedKey instanceof CKeySound)
			{
				frameOptionKeySound.setTitle(UIString.getUIString("FR_OPTIONS_KEYSOUND"));
				panelOptionKeySound.setValuesKeySound((CKeySound)selectedKey);
				frameOptionKeySound.setVisible(true);
			}
			else if (selectedKey instanceof CKeyLevel)
			{
				CKeyLevel selectedKeyLevel = (CKeyLevel)selectedKey;
				frameOptionKeyOneLevel.setTitle(UIString.getUIString("FR_OPTIONS_KEYLEVEL") + 
												selectedKeyLevel.GetLevel().toString());
				panelOptionKeyOneLevel.setValuesKeyOneLevel(selectedKeyLevel);
				frameOptionKeyOneLevel.setVisible(true);
			}
			else if (selectedKey instanceof CKeyShortcut)
			{
				frameOptionKeyShortcut.setTitle(UIString.getUIString("FR_OPTIONS_KEYSHORTCUT"));
				panelOptionKeyShortcut.setValuesKeyShortcut((CKeyShortcut)selectedKey);
				frameOptionKeyShortcut.setVisible(true);
			}
			else if (selectedKey instanceof CKeyString)
			{
				frameOptionKeyString.setTitle(UIString.getUIString("FR_OPTIONS_KEYSTRING"));
				panelOptionKeyString.setValuesKeyString((CKeyString)selectedKey);
				frameOptionKeyString.setVisible(true);
			}
			else if (selectedKey instanceof CKeyClavicom)
			{
				CKeyClavicom selectedKeyClavicom = (CKeyClavicom)selectedKey;
				frameOptionKeyKeyboard.setTitle(UIString.getUIString("FR_OPTIONS_KEYCLAVICOM") + 
											    selectedKeyClavicom.getAction().toString());
				panelOptionKeyKeyboard.setValuesKeyKeyboard((CKeyClavicom)selectedKey);
				
				frameOptionKeyKeyboard.setVisible(true);
			}
		}
	}
	
	protected class BtFermerModeEditionAction extends AbstractAction
	{
		public BtFermerModeEditionAction(String caption)
		{
			super(caption);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			// On vérifie qu'il ne reste pas de touches non classées
			if(panelKeyboard.getUnClassedKey().size() != 0)
			{
				// On selectionne les touches non classées
				for(UIKeyKeyboard currentKey : panelKeyboard.getUnClassedKey())
				{
					currentKey.setSelected(true);
				}
				
				// Affichage d'un dialog pour demander à l'utilisateur ce qu'il veut faire
				int reponse = JOptionPane.showOptionDialog(UIToolbarFrame.this, 
							UIString.getUIString("LB_EDITION_KEY_UNCLASSED_KEYS_1") + "\n" +
						  	UIString.getUIString("LB_EDITION_KEY_UNCLASSED_KEYS_2") + "\n\n" +
						  	UIString.getUIString("LB_EDITION_KEY_UNCLASSED_KEYS_3"), 
						  	UIString.getUIString("LB_EDITION_KEY_UNCLASSED_KEYS_TITLE"), 
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    UNCLASSED_KEY_CHOICES, UNCLASSED_KEY_CHOICES[0]);
				  
				if (reponse == JOptionPane.YES_OPTION)
				// Trier
				{
					frameLevelManager.setVisible(true);
					return;
				}
				else if (reponse == JOptionPane.CANCEL_OPTION)	
				// Annuler
				{
					// On arrête
					return;
				}
				else 
				// Ignorer
				{
					 // Rien à faire
				}
				
				// On déselectionne tout
				panelKeyboard.select(false);
			}
			
			// On repasse en mode clavicom normal
			frameKeyboard.edit(false);
			
			// on regarde si on doit lancer le défilement
			if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT )
			{
				startDefilMode();
			}
			if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.CLICK_TEMPORISE )
			{
				startClickTempoMode();
			}
			else if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.STANDARD )
			{
				stopDefilMode();
			}
		}
	}
	
	public void setVisibleOnly(boolean arg0)
	{
		super.setVisible(arg0);
	}
	
	@Override
	public void setVisible(boolean arg0)
	{
		// Arrêt du défilement
		if(arg0 == true)
		{
			stopDefilMode();
			
			// On se positionne pour être bien visible
			Point location = frameKeyboard.getLocation();
			location.translate(0,-(getHeight() + PREFERED_SPACE_WITH_KEYBOARD));
			
			setLocation(location);
		}

		super.setVisible(arg0);
	}
}

