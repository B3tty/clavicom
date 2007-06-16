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
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JPanel;

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
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentFrame;

public class UIKeyboardFrame extends UITranslucentFrame implements UIKeyboardSelectionChanged, ComponentListener
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int PANEL_OPTIONS_BOTTOM_SPACE = 5;
	private final int PANEL_OPTIONS_RIGHT_SPACE = 5;
	private final int PANEL_KEY_MODIFICATION_LEFT_SPACE = 5;
	
	//---------------------------------------------------------- VARIABLES --//
	// Panel principal
	UIMovingPanel mainPanel;
	
	// Panels secondaires
	JPanel panelOptions; 	// Contiendra le panel d'ajout et/ou le panel de 
							// modification d'une touche.
	
	// Sous panels
	JPanel panelModification;
	
	// Sous panels
	UIKeyboard panelKeyboard;			// Panel contenant le clavier
	UIKeyCreationToolbar creationToolbar;	// Panel contenant les touches d'edition
	
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

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardFrame(UIKeyboard panelKeyboard)
	{
		// TODO : passer la couleur
		super(1f);
		panelKeyboard.edit();
		
		// Recopie des attributs
		this.panelKeyboard = panelKeyboard;

		// Création des panels
		mainPanel = new UIMovingPanel(this);
		panelOptions = new JPanel();
		panelModification = new JPanel();
		
		creationToolbar = new UIKeyCreationToolbar(	CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor());

		// Application de la couleur de fond
		mainPanel.setOpaque(false);
		
		// -------------- Layout du panel principal ----------------------------
		GridBagLayout gbLayoutMain = new GridBagLayout();
		mainPanel.setLayout(gbLayoutMain);
		
		// Contraintes du panel d'option
		GridBagConstraints gbConstOptions = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            20,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	          								// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, 0, PANEL_OPTIONS_BOTTOM_SPACE, PANEL_OPTIONS_RIGHT_SPACE),		
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelOptions, gbConstOptions);

		// Contraintes du panel du keyboard
		GridBagConstraints gbConstKeyboard = new GridBagConstraints (	
				0,							// Numéro de colonne
	            1,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            100,						// Taille horizontale relative
	            80,							// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutMain.setConstraints(panelKeyboard, gbConstKeyboard);
	
		// -------------- Layout du panel d'options ----------------------------
		GridBagLayout gbLayoutOptions = new GridBagLayout();
		panelOptions.setLayout(gbLayoutOptions);
			
		// Contraintes du panel avec la liste d'outils
		GridBagConstraints gbConstToolbar = new GridBagConstraints (	
				0,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            60,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            new Insets(0, 0, 0, 0),		// Espace autours (haut, gauche, bas, droite)
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutOptions.setConstraints(creationToolbar, gbConstToolbar);
		
		// Contraintes du panel de modification de touche
		GridBagConstraints gbConstKeyModification = new GridBagConstraints (	
				1,							// Numéro de colonne
	            0,							// Numéro de ligne
	            1,							// Nombre de colonnes occupées
	            1,							// Nombre de lignes occupées
	            40,							// Taille horizontale relative
	            100,						// Taille verticale relative
	            GridBagConstraints.CENTER,	// Ou placer le composant en cas de redimension
	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
	            							// Espace autours (haut, gauche, bas, droite)
	            new Insets(0, PANEL_KEY_MODIFICATION_LEFT_SPACE, 0, 0),
	            0,							// Espace intérieur en X
	            0							// Espace intérieur en Y
	    );
		gbLayoutOptions.setConstraints(panelModification, gbConstKeyModification);		
		
		// Mise en place des panels
		panelOptions.add(creationToolbar);
		panelOptions.add(panelModification);
		
		mainPanel.add(panelOptions);
		mainPanel.add(panelKeyboard);
		
		add(mainPanel);
		
		// Initialisation des options
		mainPanel.setEditable(true);
		
		// Ajout du ComponentListener
		addComponentListener(this);
	}

	public void selectionChanged(List<UIKeyKeyboard> selectedKeys)
	{
		// TODO Auto-generated method stub
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
	public void Temp()
	{
		panelOptions.setVisible(true);
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
