/*-----------------------------------------------------------------------------+

			Filename			: UIModificationKeyFrame.java
			Creation date		: 18 juin 07
		
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
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import clavicom.gui.language.UIString;

public class UIModificationKeyDialog extends JDialog
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	JButton btClose;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIModificationKeyDialog(JPanel insidePanel)
	{
		super();
		
		setLayout(new BorderLayout());
		
		add(insidePanel,BorderLayout.CENTER);
		JButton btClose = new JButton(UIString.getUIString("LB_EDITION_OPTION_KEY_OK"));
		
		btClose.setAction(new BtCloseAction(UIString.getUIString("LB_EDITION_OPTION_KEY_OK")));
		
		JPanel panelBouton = new JPanel();
		panelBouton.add(btClose);
		btClose.setPreferredSize(new Dimension(100,50));
		add(panelBouton,BorderLayout.SOUTH);
	}
	//----------------------------------------------------------- METHODES --//		

	@Override
	public void paintComponents(Graphics arg0)
	{
		System.out.println("stop");
		// TODO Auto-generated method stub
		super.paintComponents(arg0);
	}
	//--------------------------------------------------- METHODES PRIVEES --//
	class BtCloseAction extends AbstractAction
	{
		public BtCloseAction(String caption)
		{
			super(caption);
		}
		public void actionPerformed(ActionEvent arg0)
		{
			UIModificationKeyDialog.this.setVisible(false);
		}
		
	}
}
