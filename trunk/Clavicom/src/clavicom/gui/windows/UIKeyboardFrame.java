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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

import clavicom.core.profil.CProfil;
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
	private final int PANEL_OPTIONS_BOTTOM_SPACE = 5;
	private final int PANEL_TOOLBAR_RIGHT_SPACE = 5;
	private final int PANEL_TOOLBAR_BOTTOM_SPACE = 5;
	private final int PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS = 5;
	
	//---------------------------------------------------------- VARIABLES --//
	private boolean isEdited; 	// Indique si on est en édition
	
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
	JButton btFermerModeEdition, btOptionsApplication;

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
		
		// Ajout du ComponentListener
		addComponentListener(this);
		
//		panelOptionKeyKeyboard = new UIPanelOptionKeyboardKey(new CKeyPrediction(Color.RED, Color.GREEN, Color.BLUE, new TPoint(15,20), new TPoint(40,60),""));
//		panelModification.add(panelOptionKeyKeyboard);
	}

	public void selectionChanged(List<UIKeyKeyboard> selectedKeys)
	{
		
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
		
		// Contraintes du panel de modification de touche
		GridBagConstraints gbConstKeyModification = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            2,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            0,							// Taille horizontale relative : rien car s'adapte
	            0,							// Taille verticale relative : rien car s'adapte
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, PANEL_OPTIONS_BOTTOM_SPACE, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelModification, gbConstKeyModification);
		
		// Contraintes du panel avec la liste d'outils
		GridBagConstraints gbConstToolbar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
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
	            1,							// Numéro de ligne
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
	            2,							// Numéro de ligne
	            2,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            90,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelKeyboard, gbConstKeyboard);
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
		
		panelOptionKeyKeyboard = new UIPanelOptionKeyboardKey();
		panelOptionKeyCharacter = new UIPanelOptionKeyCharacter();
		panelOptionKeyClavicom = new UIPanelOptionKeyClavicom();
		panelOptionKeyLauncher = new UIPanelOptionKeyLauncher();
		panelOptionKeyLevel = new UIPanelOptionKeyLevel();
		panelOptionKeyShortcut = new UIPanelOptionKeyShortCut();
		panelOptionKeyString = new UIPanelOptionKeyString();
		
		
		
		
		// TODO : enlever la ligne suivante
		panelBoutons.setLayout(new GridLayout(2,1,0,PANEL_BUTTONS_SPACE_BETWEEN_BUTTONS));
		
		// Création des boutons 
		btFermerModeEdition = new JButton(UIString.getUIString("LB_EDITION_CLOSE_EDITION"));
		btOptionsApplication = new JButton(UIString.getUIString("LB_EDITION_OPEN_OPTIONS"));
		
		panelBoutons.add(btFermerModeEdition);
		panelBoutons.add(btOptionsApplication);
		
		panelToolbar = new UIKeyCreationToolbar(	CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor());
	}
	
	/**
	 * Mets en place tous les panels
	 */
	private void setPanels()
	{	
		mainPanel.add(panelModification);
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
}
