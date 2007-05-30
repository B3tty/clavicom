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

public class UIKeyboardPanel extends JPanel //implements ButtonListener 
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

	//--------------------------------------------------- METHODES PRIVEES --//

//	public void buttonClicked(UIKey source)
//	{
//		// TODO Auto-generated method stub
//		if(source == touche1)
//		{
//			System.out.println("Click 1");
//		}
//		else
//		{
//			System.out.println("Click 2");
//		}
//	}
//
//	public void buttonEntered(UIKey source)
//	{
//		// TODO Auto-generated method stub
//		if(source == touche1)
//		{
//			System.out.println("Survol 1");
//		}
//		else
//		{
//			System.out.println("Survol 2");
//		}
//	}
//	
	private JPanel createUIKey() 
	{
		UIKey button = new UIKey();
//		button.addButtonListener(this);
		
		return button;
	}
}
