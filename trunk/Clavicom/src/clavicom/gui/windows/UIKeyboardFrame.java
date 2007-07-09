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

import java.awt.GridLayout;
import java.util.List;

import clavicom.core.profil.CFramePosition;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.DefilementKeyEngine;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.listener.UIKeyboardNewKeyCreated;
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TSize;

//import com.sun.jna.examples.WindowUtils;


public class UIKeyboardFrame extends UITranslucentFrame 
implements UIKeyboardSelectionChanged,
UIKeyboardNewKeyCreated
{
	//--------------------------------------------------------- CONSTANTES --//
	//---------------------------------------------------------- VARIABLES --//
	@SuppressWarnings("unused")
	private boolean isEdited; 	// Indique si on est en édition
	
	// Panel principal
	UIMovingPanel mainPanel;
	
	// Panel de keyboard
	UIKeyboard panelKeyboard;
	
	// Engine
	DefilementKeyEngine defilEngine;
	
	// Selections de key
	List<UIKeyKeyboard> selectedKeys;
	
	// Frame d'outils
	UIToolbarFrame frameToolbar;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardFrame(UIKeyboard panelKeyboard)
	{
		super();
		
		// Recopie des attributs
		this.panelKeyboard = panelKeyboard;
		
		// Création des objets
		mainPanel = new UIMovingPanel(this);
		frameToolbar = new UIToolbarFrame(this);
		
		// Mise en place du layout
		mainPanel.setLayout(new GridLayout());
		
		// Mise en place des panels
		mainPanel.add(panelKeyboard);
		add(mainPanel);
		
		// Initialisation des objets
		initFrame();
		
		// Ajout aux listeners
		panelKeyboard.addSelectionChangeListener(this);
		panelKeyboard.addKeyCreatedListener(this);
	}
	
	public void selectionChanged(List<UIKeyKeyboard> selectedKeys)
	{
		this.selectedKeys = selectedKeys;
		
		if(selectedKeys.size() == 0)
		{
			frameToolbar.enableEditionButton(false);
		}
		else
		{
			frameToolbar.enableEditionButton(true);
		}
	}
	
	//----------------------------------------------------------- METHODES --//	
	
	//--------------------------------------------------- METHODES PRIVEES --//
	
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
			
//			setFocusableWindowState(true);
//			setAlwaysOnTop(false);
			
			panelKeyboard.edit();		
			
			frameToolbar.setVisible(true);
		}
		else
		// En utilisation
		{
			isEdited = false;
			
			frameToolbar.setVisible(false);
			
			setTransparency(CProfil.getInstance().getTransparency().getKeyboardTransparency());
			
//			setFocusableWindowState(false);
			setAlwaysOnTop(true);
			
			// désabonnement a tous les moteurs
			panelKeyboard.unListenAllKeyKeyboard();
			
			// réabonnement a tous les moteurs
			panelKeyboard.listenAllKeyKeyboard();
			
			panelKeyboard.unEdit();
		}
		
		mainPanel.revalidate();
		panelKeyboard.repaint();
	}
	
	/**
	 * Appelé lorsqu'un nouvelle touche vient d'être ajoutée
	 * au keyboard
	 */
	public void keyCreated(UIKey createdKey)
	{
		// On desectionne toutes les keys
		panelKeyboard.select(false);
		
		// On selectionne la key qu'on vient d'ajouter
		createdKey.setSelected(true);
		
		// Ouverture de la fenêtre d'edition
		frameToolbar.openEditionFrame();
	}
		
	public UIKeyboard getUIKeyboard()
	{
		return panelKeyboard;
	}
}
