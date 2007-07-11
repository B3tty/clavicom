/*-----------------------------------------------------------------------------+

			Filename			: UIPopupMenuKey.java
			Creation date		: 10 juil. 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.keyboard

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

package clavicom.gui.keyboard.keyboard;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.EventListenerList;

import clavicom.gui.language.UIString;
import clavicom.gui.listener.UIPopupMenuItemClicked;

public class UIPopupMenuKey extends JPopupMenu
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	// Items des menus
	JMenuItem menuItemEditer;
	JMenuItem menuItemSupprimer;
	
    // un seul objet pour tous les types d'écouteurs
    protected final EventListenerList listeners = new EventListenerList();
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIPopupMenuKey()
	{
		// Création des menus items
		menuItemEditer = new JMenuItem(UIString.getUIString("LB_POPUP_MENU_KEY_EDIT"));
		menuItemSupprimer = new JMenuItem(UIString.getUIString("LB_POPUP_MENU_KEY_DELETE"));
		
		// Ajout des menus items
		add(menuItemEditer);
		add(menuItemSupprimer);
		
		// Ajout des listeners sur click
		menuItemEditer.addActionListener( new ActionListener()
										  {public void actionPerformed(ActionEvent arg0)
										  {menuItemEditerClicked();}});
		
		menuItemSupprimer.addActionListener( new ActionListener()
											 {public void actionPerformed(ActionEvent arg0)
											 {menuItemSupprimerClicked();}});
	}	
	
	//----------------------------------------------------------- METHODES --//	
	@Override
	/**
	 * Affiche le menu popup. 
	 * On dérive cette méthode pour éviter des affichages  qui dépassent de l'écran
	 */
	public void show(Component invoker, int x, int y)
	{
           // Dimension de l'écran
           Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
           
           // Dimension du Menu popup
           Dimension popupSize = this.getPreferredSize();

           // Position en x,y du popup à l'écran (pour le .show)
           double xPopupEcran = invoker.getLocationOnScreen().getX() + x;
           double yPopupEcran = invoker.getLocationOnScreen().getY() + y;
           
           //Si le popup déborde de l'écran sur la droite on décale sur x
           if ((xPopupEcran + popupSize.getWidth()) > screenSize.getWidth()) {
                   x = x - (int)popupSize.getWidth();
           } 
           
           // Si le popup déborde de l'écran sur le bas on décale sur y
           if ((yPopupEcran + popupSize.getHeight()) > screenSize.getHeight()) {
                   y = y - (int)popupSize.getHeight();
           } 
           
           // On affiche le popup à l'endroit judicieusement calculé :)
           super.show(invoker, x, y);
	}
	//--------------------------------------------------- METHODES PRIVEES --//
	
	protected void menuItemEditerClicked()
	{
		// TODO
		fireEditItemClicked();
	}
	
	protected void menuItemSupprimerClicked()
	{
		// TODO
		fireDeleteItemClicked();
	}
	
	// Listeners sur le click dans le menu
	public void addPopupMenuListener(UIPopupMenuItemClicked listener) 
	{
        listeners.add(UIPopupMenuItemClicked.class, listener);
    }
    
    public void removePopupMenuListener(UIPopupMenuItemClicked listener) 
    {
        listeners.remove(UIPopupMenuItemClicked.class, listener);
    }
 
    public UIPopupMenuItemClicked[] getPopupListeners() 
    {
        return listeners.getListeners(UIPopupMenuItemClicked.class);
    }

    protected void fireDeleteItemClicked() 
    {
	    for ( UIPopupMenuItemClicked listener : getPopupListeners() )
		{
			listener.deleteItemClicked();
		}
    }
    
    protected void fireEditItemClicked() 
    {
	    for ( UIPopupMenuItemClicked listener : getPopupListeners() )
		{
			listener.editItemClicked();
		}
    }
}
