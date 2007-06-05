/*-----------------------------------------------------------------------------+

			Filename			: UIKeyboardPanel.java
			Creation date		: 30 mai 07
		
			Project				: Clavicom
			Package				: clavicom.gui.keyboard.key

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

package clavicom.gui.keyboard.key;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class UIKeyboardPanel extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	private UIKey touche1, touche2, touche3, touche4, touche5, touche6, touche7, touche8, touche9;
	Image image;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardPanel() {
		// TEMPORAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//		setBackground(Color.GREEN);
		setPreferredSize(new Dimension(600,400 ));
		setSize(getPreferredSize());
		
		ImageIcon iconImage = new ImageIcon("C:\\Developpement Java\\Clavicom\\Ressources\\Application\\Pictures\\texture2.jpg");	
		
		// Récupération de l'image
		image = iconImage.getImage();
		
		setOpaque(false);
		
		//setLayout(new BorderLayout());
//		
		// Touches de test
		touche1 = createUIKey();
		touche2 = createUIKey();
		touche3 = createUIKey();
		touche4 = createUIKey();
		touche5 = createUIKey();
		touche6 = createUIKey();
		touche7 = createUIKey();
		touche8 = createUIKey();
		touche9 = createUIKey();
		
//	
		//touche1.setOpaque(true);
		add(touche1);
		touche1.setPreferredSize(new Dimension(160,80));
		
		//touche1.unEdit();
		
//		add(touche2);
//		add(touche3);
//		add(touche4);
//		add(touche5);
//		add(touche6);
//		add(touche7);
//		add(touche8);
//		add(touche9);
	}
	//----------------------------------------------------------- METHODES --//
	//-----------------------------------------------------------------------
	// Gestion des UIKeys - Implémentation de l'interface UIKeyListener
	//-----------------------------------------------------------------------
	protected void paintComponent(Graphics myGraphic)
	{
		try
		{
			// Appel du père
			super.paintComponent(myGraphic);
			
			// Dessin de l'image courante
			Graphics2D g2 = (Graphics2D) myGraphic;
			//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_ATOP, 0.5f ));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawImage(image, 0, 0, null);	
		}
		catch (Exception ex)
		{
			// A COMPLETER
			ex.printStackTrace();
		}
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//	
	//-----------------------------------------------------------------------
	// Gestion des UIKeys
	//-----------------------------------------------------------------------
	protected UIKey createUIKey()
	{
		UIKey key = new UIKeyOneLevel();
		return key;
	}

}
