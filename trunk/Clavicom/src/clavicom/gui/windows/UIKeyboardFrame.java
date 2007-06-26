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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.profil.CFramePosition;
import clavicom.core.profil.CProfil;
import clavicom.gui.configuration.UIFrameModificationProfil;
import clavicom.gui.edition.key.UIPanelOptionKeyCharacter;
import clavicom.gui.edition.key.UIPanelOptionKeyLauncher;
import clavicom.gui.edition.key.UIPanelOptionKeyShortCut;
import clavicom.gui.edition.key.UIPanelOptionKeyString;
import clavicom.gui.edition.key.UIPanelOptionKeyboardKey;
import clavicom.gui.edition.key.UIPanelOptionOneLevelKey;
import clavicom.gui.edition.keyboard.UIKeyCreationToolbar;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TKeyClavicomActionType;
import clavicom.tools.TLevelEnum;
import clavicom.tools.TNavigationType;
import clavicom.tools.TSize;

public class UIKeyboardFrame extends UITranslucentFrame implements UIKeyboardSelectionChanged, ComponentListener
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int PANEL_TOOLBAR_RIGHT_SPACE = 5;
	private final int PANEL_TOOLBAR_BOTTOM_SPACE = 5;
	private final int PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS = 5;
	
	//---------------------------------------------------------- VARIABLES --//
	@SuppressWarnings("unused")
	private boolean isEdited; 	// Indique si on est en édition
	
	// Frame de modification de touche
	UIModificationKeyDialog frameOptionKeyOneLevel;
	UIModificationKeyDialog frameOptionKeyKeyboard;
	UIModificationKeyDialog frameOptionKeyCharacter;
	UIModificationKeyDialog frameOptionKeyLauncher;
	UIModificationKeyDialog frameOptionKeyShortcut;
	UIModificationKeyDialog frameOptionKeyString;
	
	// Frame d'options de l'application
	UIFrameModificationProfil frameOptionApplication;
	
	// Panel principal
	UIMovingPanel mainPanel;
	
	// Sous panels
	JPanel panelBoutons;	// Contient le bouton fermer mode édition et paramètres
	
	// Sous panels
	UIKeyboard panelKeyboard;			// Panel contenant le clavier
	UIKeyCreationToolbar panelToolbar;	// Panel contenant les touches d'edition
	
	// Panels de modification de touche
	UIPanelOptionOneLevelKey panelOptionKeyOneLevel;
	UIPanelOptionKeyboardKey panelOptionKeyKeyboard;
	UIPanelOptionKeyCharacter panelOptionKeyCharacter;
	UIPanelOptionKeyLauncher panelOptionKeyLauncher;
	UIPanelOptionKeyShortCut panelOptionKeyShortcut;
	UIPanelOptionKeyString panelOptionKeyString;
	
	// Selections de key
	List<UIKeyKeyboard> selectedKeys;
	
	// Boutons
	JButton btFermerModeEdition, btOptionsApplication, btEditionKey;
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardFrame(UIKeyboard panelKeyboard)
	{		
		// TODO : passer la couleurZ
		super();
		
		// Recopie des attributs
		this.panelKeyboard = panelKeyboard;
		
		// Création des objets
		createObjects( panelKeyboard );
		
		// Application des layouts
		setAllLayouts();		
		
		// Mise en place des panels
		setPanels();
		
		// Initialisation des objets
		initFrame();
		
		// Ajout aux listeners
		addComponentListener(this);
		panelKeyboard.addSelectionChangeListener(this);
	}
	
	public void selectionChanged(List<UIKeyKeyboard> selectedKeys)
	{
		this.selectedKeys = selectedKeys;
		
		if(selectedKeys.size() == 0)
		{
			btEditionKey.setEnabled(false);
		}
		else
		{
			btEditionKey.setEnabled(true);
		}
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	public void componentHidden(ComponentEvent arg0)
	{
		// Rien à ajouter
	}

	public void componentMoved(ComponentEvent arg0)
	{
		// Rien à ajouter
	}

	public void componentResized(ComponentEvent arg0)
	{
		//  Rien à ajouter
	}

	public void componentShown(ComponentEvent arg0)
	{
		// Rien à ajouter
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
	/**
	 * Spécifie tous les layouts de l'application
	 */
	private void setAllLayouts()
	{
		// -------------- Layout du panel principal ----------------------------
		GridBagLayout gbLayoutMain = new GridBagLayout();
		mainPanel.setLayout(gbLayoutMain);
		
		// Contraintes du panel avec la liste d'outils
		GridBagConstraints gbConstToolbar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            90,							// Taille horizontale relative
	            5,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, PANEL_TOOLBAR_BOTTOM_SPACE, PANEL_TOOLBAR_RIGHT_SPACE),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelToolbar, gbConstToolbar);

		// Contraintes du panel de boutons
		GridBagConstraints gbConstBoutons = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            10,							// Taille horizontale relative
	            5,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, PANEL_TOOLBAR_BOTTOM_SPACE, 0),		
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelBoutons, gbConstBoutons);
		
		// Contraintes du panel du keyboard
		GridBagConstraints gbConstKeyboard = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            2,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            95,						// Taille horizontale relative
	            90,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelKeyboard, gbConstKeyboard);
		
		// -------------- Layout de la frame d'options -------------------------
		// panelModification.setLayout(new BorderLayout());		
	}
	
	/**
	 * Créé tous les objets nécessaires à la fenêtre
	 * @param uiKeyboard 
	 *
	 */
	private void createObjects(UIKeyboard uiKeyboard)
	{		
		// Création des panels
		mainPanel = new UIMovingPanel(this);
		panelBoutons = new JPanel();
		
		// Panels de modification de touche
		panelOptionKeyOneLevel = new UIPanelOptionOneLevelKey();
		panelOptionKeyKeyboard = new UIPanelOptionKeyboardKey();
		panelOptionKeyCharacter = new UIPanelOptionKeyCharacter();
		panelOptionKeyLauncher = new UIPanelOptionKeyLauncher();
		panelOptionKeyShortcut = new UIPanelOptionKeyShortCut();
		panelOptionKeyString = new UIPanelOptionKeyString();
		
		// Création des frames
		frameOptionKeyOneLevel = new UIModificationKeyDialog(panelOptionKeyOneLevel);
		frameOptionKeyKeyboard = new UIModificationKeyDialog(panelOptionKeyKeyboard);
		frameOptionKeyCharacter = new UIModificationKeyDialog(panelOptionKeyCharacter);
		frameOptionKeyLauncher = new UIModificationKeyDialog(panelOptionKeyLauncher);
		frameOptionKeyShortcut = new UIModificationKeyDialog(panelOptionKeyShortcut);
		frameOptionKeyString = new UIModificationKeyDialog(panelOptionKeyString);
		
		frameOptionApplication = new UIFrameModificationProfil( uiKeyboard );
		
		panelBoutons.setLayout(new GridLayout(3,1,0,PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS));
		
		// Création des boutons 
		btFermerModeEdition = new JButton(UIString.getUIString("LB_EDITION_CLOSE_EDITION"));
		btOptionsApplication = new JButton(UIString.getUIString("LB_EDITION_OPEN_OPTIONS"));
		btEditionKey = new JButton(UIString.getUIString("LB_EDITION_EDIT_KEY"));
		
		
		panelBoutons.add(btFermerModeEdition);
		panelBoutons.add(btOptionsApplication);
		panelBoutons.add(btEditionKey);
		
		panelToolbar = new UIKeyCreationToolbar(	CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor());
	}
	
	/**
	 * Mets en place tous les panels
	 */
	private void setPanels()
	{	
		// Frame principale
		mainPanel.add(panelToolbar);
		mainPanel.add(panelBoutons);
		mainPanel.add(panelKeyboard);
		
		add(mainPanel);
	}
	
	/**
	 * Initialise les options des objets de la fenêtre
	 */
	private void initFrame()
	{
		// Par défaut on n'est pas en edition
		edit(false);
		
		CFramePosition clavicomPosition = CProfil.getInstance().getAdvancedOption().getClavicomFramePosition();
		
	
		setBounds( TSize.getRectangleBound( clavicomPosition.getLeftUp(), clavicomPosition.getRightDown() ) );
		
		// On autorise le resize de la fenêtre
		mainPanel.setEditable(true);
		
		// Ajout des actions aux boutons
		btEditionKey.setAction(new BtEditionKeyAction(UIString.getUIString("LB_EDITION_EDIT_KEY")));
		btFermerModeEdition.setAction(new BtFermerModeEditionAction(UIString.getUIString("LB_EDITION_CLOSE_EDITION")));
		btOptionsApplication.setAction(new BtOptionsApplicationAction(UIString.getUIString("LB_EDITION_OPEN_OPTIONS")));
		
		// Initialisation des tailles des fenêtres
		
		frameOptionKeyOneLevel.setSize(410,250);
		frameOptionKeyKeyboard.setSize(410,125);
		frameOptionKeyCharacter.setSize(540,540);
		frameOptionKeyLauncher.setSize(410,310);
		frameOptionKeyShortcut.setSize(410,350);
		frameOptionKeyString.setSize(410,310);
		
		// Frame d'options de l'application
		frameOptionApplication.setSize(new Dimension (500,600));
		frameOptionApplication.setResizable(false);
		frameOptionApplication.setModal(true);
		frameOptionApplication.setVisible(false);
		
		// Initialisation des composants
		btEditionKey.setEnabled(false);
	}
	
	/**
	 * Permet d'éditer ou non
	 */
	public void edit(boolean edit)
	{
		if (edit == true)
		// En edition
		{
			isEdited = true;
			
			setTransparency(1f);
			
			setFocusableWindowState(true);
			setAlwaysOnTop(false);
			
			panelKeyboard.edit();
			panelToolbar.setVisible(true);
			panelBoutons.setVisible(true);
		}
		else
		// En utilisation
		{
			isEdited = false;
			
			setTransparency((float)(CProfil.getInstance().getTransparency().getKeyboardTransparencyPourcent())/100f);
			
			setFocusableWindowState(false);
			setAlwaysOnTop(true);
			
			panelKeyboard.unEdit();
			panelToolbar.setVisible(false);
			panelBoutons.setVisible(false);			
		}
		
		invalidate();		
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
	
	protected class BtFermerModeEditionAction extends AbstractAction
	{
		public BtFermerModeEditionAction(String caption)
		{
			super(caption);
		}
		
		public void actionPerformed(ActionEvent arg0)
		{
			// On repasse en mode clavicom normal
			edit(false);
			
			// on regarde si on doit lancer le défilement
			if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.DEFILEMENT )
			{
				stopDefilMode();
				startDefilMode();
			} else if( CProfil.getInstance().getNavigation().getTypeNavigation() == TNavigationType.STANDARD )
			{
				stopDefilMode();
			}
			
		}

		private void stopDefilMode()
		{
			// TODO Auto-generated method stub
			
		}

		private void startDefilMode()
		{
//			try
//			{
//				toolKeyboardDefilEngine = new DefilementKeyEngine( uiKeyboard, false );
//			}
//			catch (Exception ex)
//			{
//				CMessageEngine.newError(	UIString.getUIString("MSG_MAIN_CANT_LOAD_KEYBOARD_DEFIL_ENGINE_1"),
//											ex.getMessage());
//			}
		}
	}
	
	protected void onClickBtEditionKey()
	{
		if(selectedKeys.size() > 1)
		// Modification multiple
		{
			// TODO : Selection multiple
		}
		else
		// Modification unique
		{
			// Récupération de la touche
			CKey selectedKey = selectedKeys.get(0).getCoreKey();
			
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
			else if (selectedKey instanceof CKeyLevel)
			{
				CKeyLevel selectedKeyLevel = (CKeyLevel)selectedKey;
				frameOptionKeyOneLevel.setTitle(UIString.getUIString("FR_OPTIONS_KEYLEVEL") + 
										TLevelEnum.getString(selectedKeyLevel.GetLevel()));
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
	
	public void setMouseFrame()
	{
		// TODO : COMPLETER CA !
	}
}
