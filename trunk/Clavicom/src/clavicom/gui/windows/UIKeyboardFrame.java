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
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import clavicom.CSettings;
import clavicom.core.profil.CFramePosition;
import clavicom.core.profil.CProfil;
import clavicom.gui.engine.DefilementKeyEngine;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.keyboard.UIKeyboard;
import clavicom.gui.keyboard.keyboard.UIPopupMenuKey;
import clavicom.gui.listener.UIKeyboardNewKeyCreated;
import clavicom.gui.listener.UIKeyboardSelectionChanged;
import clavicom.gui.listener.UIPopupMenuItemClicked;
import clavicom.gui.listener.UIRightClickListener;
import clavicom.gui.utils.UIMovingPanel;
import clavicom.gui.utils.UITranslucentFrame;
import clavicom.tools.TSize;

//import com.sun.jna.examples.WindowUtils;


public class UIKeyboardFrame extends UITranslucentFrame 
implements UIKeyboardSelectionChanged,
UIKeyboardNewKeyCreated,
UIPopupMenuItemClicked,
UIRightClickListener
{
	//--------------------------------------------------------- CONSTANTES --//
	//---------------------------------------------------------- VARIABLES --//
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
	
	// Popup menu des keys
	UIPopupMenuKey popupMenu;			
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardFrame(UIKeyboard panelKeyboard)
	{
		super();
		
		// Recopie des attributs
		this.panelKeyboard = panelKeyboard;
		
		// Création des objets
		mainPanel = new UIMovingPanel(this, panelKeyboard.getMouseAdapter());
		frameToolbar = new UIToolbarFrame(this);
		popupMenu = new UIPopupMenuKey();
		
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
		panelKeyboard.addRightClickListener(this);
		popupMenu.addPopupMenuListener(this);
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
		
		// On ajoute un listener pour réafficher la toolbar en cas de redimension
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowDeiconified(WindowEvent arg0)
			{
				if(isEdited)
					frameToolbar.setVisibleOnly(true);
				
				super.windowDeiconified(arg0);
			}
			
			@Override
			public void windowIconified(WindowEvent arg0)
			{
				if(isEdited)
					frameToolbar.setVisibleOnly(false);
				
				super.windowIconified(arg0);
			}
		});
	}
	
	/**
	 * Permet d'éditer ou non
	 */
	public void edit(boolean edit)
	{
		if (edit)
		// En edition
		{
			isEdited = true;
			
			setTransparency(1f);
			
			setFocusableWindowState(true);
			setAlwaysOnTop(false);
			
			panelKeyboard.edit();		
			
			frameToolbar.setVisible(true);
		}
		else
		// En utilisation
		{
			isEdited = false;

			frameToolbar.setVisible(false);
			
			setTransparency(CSettings.getTransparency().getKeyboardTransparency());
			
			setFocusableWindowState(false);
			frameToolbar.setFocusableWindowState(false);
			
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

	public void rightClickOccured(Point mousePositionOnScreen)
	{
		// On affiche le popup que s'il y a des selectionnées
		if(panelKeyboard.getSelectedKeys().size() > 0)
			popupMenu.show(this, mousePositionOnScreen.x, mousePositionOnScreen.y);
	}

	public void deleteItemClicked()
	{
		// Suppression des touches
		panelKeyboard.deleteSelectedKeys();		
	}

	public void editItemClicked()
	{
		frameToolbar.editSelectedKeys();
	}
}
