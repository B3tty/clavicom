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

import javax.swing.JPopupMenu;

public class UIPopupMenuKey extends JPopupMenu
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIPopupMenuKey()
	{
		// TODO : A COMPLETER
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
}
