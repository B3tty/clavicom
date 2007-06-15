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
import clavicom.gui.edition.keyboard.UIPanelKeyCreation;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.key.resizer.UIJResizer;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.listener.UIKeyboardSelectionChanged;

public class UIKeyboardFrame extends UITranslucentFrame implements UIKeyboardSelectionChanged
{
	//--------------------------------------------------------- CONSTANTES --//

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
	UIPanelKeyCreation creationComponent;	// Panel contenant les touches d'edition
	
	// Panels de modification de touche
	UIPanelKeyCreation panelKeyCreation;
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
		super(.9f);
		
		// Recopie des attributs
		this.panelKeyboard = panelKeyboard;
		
		// Création des panels
		mainPanel = new UIMovingPanel(this);
		mainPanel.setEditable(true);
		
		// Changement de layout
		mainPanel.setLayout(new BorderLayout());
		
		panelOptions = new JPanel();
		panelModification = new JPanel();
		panelOptions.setLayout(new BorderLayout());
		
		// TEMP
		//panelModification.setPreferredSize(new Dimension(100,100));
		//panelModification.setBackground(Color.RED);
		
		creationComponent = new UIPanelKeyCreation(	CProfil.getInstance().getDefaultColor().getDefaultKeyClicked().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor(),
													CProfil.getInstance().getDefaultColor().getDefaultKeyNormal().getColor());
		
		// Mise en place des panels
		// Options en haut
		panelOptions.add(panelModification,BorderLayout.EAST);
		panelOptions.add(creationComponent,BorderLayout.CENTER);
		
		// Ajout au panel principal
		mainPanel.add(panelOptions, BorderLayout.NORTH);
		mainPanel.add(panelKeyboard, BorderLayout.CENTER);
		
		// Ajout du panel principal à la frame
		add(mainPanel);
		
		// Initialisation des options
		mainPanel.setEditable(true);
	}
	
	//----------------------------------------------------------- METHODES --//	
	/**
	 * Appelé lorsque la selection a changé.
	 */
	public void selectionChanged(List<UIKeyKeyboard> selectedKeys)
	{
		// On met à jour la sélection de keys
		this.selectedKeys = selectedKeys;		
	}


	//--------------------------------------------------- METHODES PRIVEES --//
}
