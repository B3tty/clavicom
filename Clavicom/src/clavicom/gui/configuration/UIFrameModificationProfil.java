/*-----------------------------------------------------------------------------+

			Filename			: GlobalProfilModification.java
			Creation date		: 12 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.configuration

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

package clavicom.gui.configuration;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;

import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class UIFrameModificationProfil extends JDialog
{
	//--------------------------------------------------------- CONSTANTES --//
	// Espaces entre les différents élements de la fenêtre
	protected final int MAIN_PANEL_SPACE_LEFT = 5;
	protected final int MAIN_PANEL_SPACE_RIGHT = 5;
	protected final int MAIN_PANEL_SPACE_BOTTOM = 5;
	protected final int MAIN_PANEL_SPACE_UP = 5;
	
	protected final int BOTTOM_PANEL_SPACE_BETWEEN_BUTTONS = 5;
	protected final int BOTTOM_PANEL_SPACE_UP = 5;
	protected final int BOTTOM_PANEL_SPACE_LEFT = 5;
	//---------------------------------------------------------- VARIABLES --//	
	// Panel des boutons
	JPanel bottomPanel;
	JButton btOk, btCancel, btApply;
	JProgressBar progressBarApply;
	
	JPanel buttonPanel, progressBarPanel;
	
	// Onglets
	JTabbedPane tabbedPane;
	
	PanelModificationProfilCommandSetName panelCommandSetName;
	PanelModificationProfilShortcutSetName panelShortcutSetName;
	PanelModificationProfilDictionaryName panelDictionaryName;
	PanelModificationProfilFont panelFont;
	PanelModificationProfilKeyboardColor panelKeyboardColor;
	PanelModificationProfilNavigation panelNavigation;
	PanelModificationProfilPreferedWords panelPreferedWords;
	PanelModificationProfilSound panelSound;
	PanelModificationProfilTransparency panelTransparency;
	PanelModificationProfilLangueUIName panelLangueUI;
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public UIFrameModificationProfil()
	{
		// Initialisation de la fenêtre
		initFrame();
		
		// Création des objets de bas de page
		createBottom();
		
		// Création des onglets
		createTabs();

		// Création des layouts
		setLayouts();
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	/**
	 * Initialise la fenêtre
	 */
	protected void initFrame()
	{
		// Titre de la fenêtre
		setTitle(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_TITLE"));
	}
	
	/**
	 * Créé le panel avec les onglets
	 */
	protected void createTabs()
	{
		tabbedPane = new JTabbedPane();
		CProfil profil = CProfil.getInstance();
		
		// panel des command Set
		JPanel panel = new JPanel(); 
		panelCommandSetName = new PanelModificationProfilCommandSetName( profil.getCommandSetName() );
		panel.add( panelCommandSetName );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_COMMANDSET"), panel);
		
		// panel des shortcut Set
		panel = new JPanel(); 
		panelShortcutSetName = new PanelModificationProfilShortcutSetName( profil.getShortcutSetName() );
		panel.add( panelShortcutSetName );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_SHORTCUTSET"), panel);
		
		// panel des dictionary name
		panel = new JPanel(); 
		panelDictionaryName = new PanelModificationProfilDictionaryName( profil.getDictionnaryName() );
		panel.add( panelDictionaryName );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_DICTIONARY"), panel);
		
		// panel des fonts
		panel = new JPanel(); 
		panelFont = new PanelModificationProfilFont( profil.getKeyboardFont() );
		panel.add( panelFont );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_FONT"), panel);
		
		// panel des kayboard color
		panel = new JPanel(); 
		panelKeyboardColor = new PanelModificationProfilKeyboardColor( profil.getDefaultColor() );
		panel.add( panelKeyboardColor );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_KEYBOARDCOLOR"), panel);
		
		// panel des languesUI
		panel = new JPanel(); 
		panelLangueUI = new PanelModificationProfilLangueUIName( profil.getLangueUI() );
		panel.add( panelLangueUI );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_LANGUAGE"), panel);
		
		// panel de navigation
		panel = new JPanel( new BorderLayout() ); 
		panelNavigation = new PanelModificationProfilNavigation( profil.getNavigation() );
		panel.add( panelNavigation, BorderLayout.CENTER );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_NAVIGATION"), panel);
		
		// panel des preferedWords
		panel = new JPanel(); 
		panelPreferedWords = new PanelModificationProfilPreferedWords( profil.getPreferedWords() );
		panel.add( panelPreferedWords );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_PREFEREDWORDS"), panel);
		
		// panel des sound
		panel = new JPanel(); 
		panelSound = new PanelModificationProfilSound( profil.getSound() );
		panel.add( panelSound );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_SOUND"), panel);
		
		// panel de la transparence
		panel = new JPanel(); 
		panelTransparency = new PanelModificationProfilTransparency( profil.getTransparency() );
		panel.add( panelTransparency );
		tabbedPane.addTab( UIString.getUIString("LB_CONFPROFIL_PANNEL_TRANSPARENCY"), panel);
		
		// Ajout au panel
		add(tabbedPane);
	}
	
	/**
	 * Créé les objets de bas de page (bouttons et progressBar)
	 * 
	 */
	protected void createBottom()
	{
		// Création de panels
		bottomPanel = new JPanel();
		buttonPanel = new JPanel();
		progressBarPanel = new JPanel();
		
		buttonPanel.setLayout(new GridLayout(1,3,BOTTOM_PANEL_SPACE_BETWEEN_BUTTONS,BOTTOM_PANEL_SPACE_BETWEEN_BUTTONS));
		progressBarPanel.setLayout(new GridLayout());
		
		// PROGRESS BAR
		progressBarApply = new JProgressBar();
		progressBarPanel.add(progressBarApply);
		bottomPanel.add(progressBarPanel);
		
		// TOUCHES
		btOk = new JButton(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_OK"));
		btCancel = new JButton(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_CANCEL"));
		btApply = new JButton(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_APPLY"));
		
		btOk.setAction(new BtOkAction(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_OK")));
		btCancel.setAction(new BtCancelAction(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_CANCEL")));
		btApply.setAction(new BtApplyAction(UIString.getUIString("LB_EDITION_OPTION_APPLICATION_APPLY")));
		
		buttonPanel.add(btOk);
		buttonPanel.add(btCancel);
		buttonPanel.add(btApply);
		
		bottomPanel.add(buttonPanel);
		
		add(bottomPanel);
	}
	
	/**
	 * Applique les layouts
	 */
	protected void setLayouts()
	{
		// -------------- Layout du panel d'onglets ----------------------------
		GridBagLayout gbLayoutMain = new GridBagLayout();
		setLayout(gbLayoutMain);
		
		// Contraintes du panel avec les onglets
		GridBagConstraints gbConstToolbar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            90,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	        								// Espace autours (haut, gauche, bas, droite)
	            new Insets(MAIN_PANEL_SPACE_UP, MAIN_PANEL_SPACE_LEFT, 0, MAIN_PANEL_SPACE_RIGHT),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(tabbedPane, gbConstToolbar);
		
		// Contraintes du panel bottom
		GridBagConstraints gbConstBottom = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            10,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
											// Espace autours (haut, gauche, bas, droite)
	            new Insets(MAIN_PANEL_SPACE_UP, MAIN_PANEL_SPACE_LEFT, MAIN_PANEL_SPACE_BOTTOM, MAIN_PANEL_SPACE_RIGHT),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(bottomPanel, gbConstBottom);
		
		// -------------- Layout du panel de bas de page ------------------------
		GridBagLayout gbLayoutBottom = new GridBagLayout();
		bottomPanel.setLayout(gbLayoutBottom);
		
		// Contraintes du panel progress bar
		GridBagConstraints gbConstProgressBar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            70,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, 0, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutBottom.setConstraints(progressBarPanel, gbConstProgressBar);
		
		// Contraintes du panel de boutons
		GridBagConstraints gbConstButtons = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            30,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, BOTTOM_PANEL_SPACE_LEFT, 0, 0),		
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutBottom.setConstraints(buttonPanel, gbConstButtons);
	}

	/**
	 * Appelé à l'appui sur le bouton Ok
	 */
	protected void btOkPressed()
	{
		
	}
	
	/**
	 * Appelé à l'appui sur le bouton Annuler
	 */
	protected void btCancelPressed()
	{
		
	}

	
	/**
	 * Appelé à l'appui sur le bouton Appliquer
	 */
	protected void btApplyPressed()
	{
		
	}
	//---------------------------------------------------- CLASSES PRIVEES --//
	protected class BtApplyAction extends AbstractAction
	{
		public BtApplyAction(String caption)
		{
			super(caption);
		}

		public void actionPerformed(ActionEvent arg0)
		{
			btApplyPressed();
		}
	}
	
	protected class BtOkAction extends AbstractAction
	{
		public BtOkAction(String caption)
		{
			super(caption);
		}

		public void actionPerformed(ActionEvent arg0)
		{
			btOkPressed();
		}
	}
	
	protected class BtCancelAction extends AbstractAction
	{
		public BtCancelAction(String caption)
		{
			super(caption);
		}

		public void actionPerformed(ActionEvent arg0)
		{
			btCancelPressed();
		}
	}
	
}