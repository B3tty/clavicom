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

import java.awt.BorderLayout;
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
import javax.swing.UIManager;

import clavicom.core.keygroup.CKey;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.profil.CProfil;

import clavicom.gui.configuration.UIFrameModificationProfil;
import clavicom.gui.edition.key.UIPanelOptionKeyCharacter;
import clavicom.gui.edition.key.UIPanelOptionKeyClavicom;
import clavicom.gui.edition.key.UIPanelOptionKeyLauncher;
import clavicom.gui.edition.key.UIPanelOptionKeyLevel;
import clavicom.gui.edition.key.UIPanelOptionKeyShortCut;
import clavicom.gui.edition.key.UIPanelOptionKeyString;
import clavicom.gui.edition.key.UIPanelOptionKeyboardKey;
import clavicom.gui.edition.keyboard.UIKeyCreationToolbar;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.language.UIString;
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentFrame;

public class UIKeyboardFrame extends UITranslucentFrame implements UIKeyboardSelectionChanged, ComponentListener
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int PANEL_TOOLBAR_RIGHT_SPACE = 5;
	private final int PANEL_TOOLBAR_BOTTOM_SPACE = 5;
	private final int PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS = 5;
	
	//---------------------------------------------------------- VARIABLES --//
	private boolean isEdited; 	// Indique si on est en édition
	
	// Frame d'option
	UIModificationKeyDialog frameOptionKey;
	
	// Frame d'options de l'application
	UIFrameModificationProfil frameOptionApplication;
	
	// Panel principal
	UIMovingPanel mainPanel;
	
	// Sous panels
	JPanel panelModification;
	JPanel panelBoutons;	// Contient le bouton fermer mode édition et paramètres
	
	// Sous panels
	UIKeyboard panelKeyboard;			// Panel contenant le clavier
	UIKeyCreationToolbar panelToolbar;	// Panel contenant les touches d'edition
	
	// Panels de modification de touche
	UIPanelOptionKeyboardKey panelOptionKeyKeyboard;
	UIPanelOptionKeyCharacter panelOptionKeyCharacter;
	UIPanelOptionKeyClavicom panelOptionKeyClavicom;
	UIPanelOptionKeyLauncher panelOptionKeyLauncher;
	UIPanelOptionKeyLevel panelOptionKeyLevel;
	UIPanelOptionKeyShortCut panelOptionKeyShortcut;
	UIPanelOptionKeyString panelOptionKeyString;
	
	// Selections de key
	List<UIKeyKeyboard> selectedKeys;
	
	// Boutons
	JButton btFermerModeEdition, btOptionsApplication, btEditionKey;
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardFrame(UIKeyboard panelKeyboard)
	{		
		// TODO : passer la couleur
		super(.98f);
		
		// Mise en place du style
		setStyle();
		
		// Recopie des attributs
		this.panelKeyboard = panelKeyboard;

		// Création des objets
		createObjects();
		
		// Application des layouts
		setAllLayouts();		
		
		// Mise en place des panels
		setPanels();
		
		// Initialisation des objets
		initFrame();
		
		// Ajout aux listeners
		addComponentListener(this);
		panelKeyboard.addSelectionChangeListener(this);
		
//		panelOptionKeyKeyboard = new UIPanelOptionKeyboardKey(new CKeyPrediction(Color.RED, Color.GREEN, Color.BLUE, new TPoint(15,20), new TPoint(40,60),""));
//		panelModification.add(panelOptionKeyKeyboard);
	}

	public void selectionChanged(List<UIKeyKeyboard> selectedKeys)
	{
		if (isEdited == false)
		{
			return;
		}
		
		if (selectedKeys.size() == 0)
		{
			frameOptionKey.setVisible(false);
			return;
		}
		
		panelModification.setVisible(false);
		CKey currentCoreKey = selectedKeys.get(0).getCoreKey();
		
		panelModification.removeAll();
		
		if (currentCoreKey instanceof CKeyCharacter)
		{
			panelOptionKeyCharacter.setValuesKeyCharacter((CKeyCharacter)currentCoreKey);
			panelModification.add(panelOptionKeyCharacter);
		}
		else if (currentCoreKey instanceof CKeyPrediction)
		{
			// TODO : faire quelque chose !
			return;
		}
		else if (currentCoreKey instanceof CKeyLastWord)
		{
			// TODO : faire quelque chose !
			return;
		}
		else if (currentCoreKey instanceof CKeyLauncher)
		{
			panelOptionKeyLauncher.setValuesKeyLauncher((CKeyLauncher)currentCoreKey);
			panelModification.add(panelOptionKeyLauncher);
		}
		else if (currentCoreKey instanceof CKeyLevel)
		{
			// TODO : faire quelque chose !
			return;
		}
		else if (currentCoreKey instanceof CKeyShortcut)
		{
			panelOptionKeyShortcut.setValuesKeyShortcut((CKeyShortcut)currentCoreKey);
			panelModification.add(panelOptionKeyShortcut);
		}
		else if (currentCoreKey instanceof CKeyString)
		{
			panelOptionKeyString.setValuesKeyString((CKeyString)currentCoreKey);
			panelModification.add(panelOptionKeyString);
		}
		else if (currentCoreKey instanceof CKeyClavicom)
		{
			panelOptionKeyClavicom.setValuesKeyClavicom((CKeyClavicom)currentCoreKey);
			panelModification.add(panelOptionKeyClavicom);
		}
		
		panelModification.setVisible(true);
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
	 * Met en place le style de la fenêtre
	 */
	private void setStyle()
	{
		try
		{
			UIManager.setLookAndFeel( "de.javasoft.plaf.synthetica.SyntheticaBlueMoonLookAndFeel"  );
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
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
		panelModification.setLayout(new BorderLayout());		
	}
	
	/**
	 * Créé tous les objets nécessaires à la fenêtre
	 *
	 */
	private void createObjects()
	{		
		// Création des panels
		mainPanel = new UIMovingPanel(this);
		panelModification = new JPanel();
		panelBoutons = new JPanel();
		
		// Création des frames
		frameOptionKey = new UIModificationKeyDialog(panelModification);
		frameOptionApplication = new UIFrameModificationProfil();
		
		panelOptionKeyKeyboard = new UIPanelOptionKeyboardKey();
		panelOptionKeyCharacter = new UIPanelOptionKeyCharacter();
		panelOptionKeyClavicom = new UIPanelOptionKeyClavicom();
		panelOptionKeyLauncher = new UIPanelOptionKeyLauncher();
		panelOptionKeyLevel = new UIPanelOptionKeyLevel();
		panelOptionKeyShortcut = new UIPanelOptionKeyShortCut();
		panelOptionKeyString = new UIPanelOptionKeyString();
		
		
		// TODO : enlever la ligne suivante
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
		mainPanel.setEditable(true);
		isEdited = false;
		edit(true);
		
		// Ajout des actions aux boutons
		btEditionKey.setAction(new BtEditionKeyAction(UIString.getUIString("LB_EDITION_EDIT_KEY")));
		btFermerModeEdition.setAction(new BtFermerModeEditionAction(UIString.getUIString("LB_EDITION_CLOSE_EDITION")));
		btOptionsApplication.setAction(new BtOptionsApplicationAction(UIString.getUIString("LB_EDITION_OPEN_OPTIONS")));
		
		// Frame d'option de touche
		frameOptionKey.setSize(new Dimension (640,480));
		frameOptionKey.setModal(true);
		frameOptionKey.setVisible(false);
		
		// Frame d'options de l'application
		frameOptionApplication.setSize(new Dimension (500,600));
		frameOptionApplication.setResizable(false);
		frameOptionApplication.setModal(true);
		frameOptionApplication.setVisible(false);
	}
	
	/**
	 * Permet d'éditer ou non
	 */
	public void edit(boolean edit)
	{
		if (edit == true)
		{
			isEdited = true;
			
			panelKeyboard.edit();
			panelToolbar.setVisible(true);
			panelBoutons.setVisible(true);
			panelModification.setVisible(true);
		}
		else
		{
			isEdited = true;
			
			panelKeyboard.unEdit();
			panelToolbar.setVisible(false);
			panelBoutons.setVisible(false);
			panelModification.setVisible(false);			
		}
		
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
			frameOptionKey.setVisible(true);
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
			// TODO
		}
	}
}
