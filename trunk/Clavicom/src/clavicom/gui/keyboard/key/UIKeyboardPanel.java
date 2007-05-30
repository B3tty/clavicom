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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import clavicom.tools.TUIKeyState;

public class UIKeyboardPanel extends JPanel implements UIKeyListener 
{
	//--------------------------------------------------------- CONSTANTES --//
	
	//---------------------------------------------------------- VARIABLES --//	
	private JPanel touche1, touche2;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyboardPanel() {
		// TEMPORAIRE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(800, 600));
		setSize(getPreferredSize());
		
		// Touches de test
		touche1 = createUIKey();
		touche2 = createUIKey();
		
		setLayout(new GridLayout());
		
		add(touche1);
		add(touche2);
	}
	//----------------------------------------------------------- METHODES --//
	//-----------------------------------------------------------------------
	// Gestion des UIKeys - Implémentation de l'interface UIKeyListener
	//-----------------------------------------------------------------------
	public void buttonEntered(UIKey source)
	{
		source.setState(TUIKeyState.SELECTED);
		source.repaint();
	}

	public void buttonExited(UIKey source)
	{
		source.setState(TUIKeyState.NORMAL);
		source.repaint();
	}

	public void buttonPressed(UIKey source)
	{
		source.setState(TUIKeyState.PRESSED);
		source.repaint();	
	}

	public void buttonReleased(UIKey source)
	{
		source.setState(TUIKeyState.NORMAL);
		source.repaint();		
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//	
	//-----------------------------------------------------------------------
	// Gestion des UIKeys
	//-----------------------------------------------------------------------
	protected JPanel createUIKey()
	{
//		UIKey key = new UIKey();
//		key.addUIKeyListener(this);
		return null;
	}

}
