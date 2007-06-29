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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import clavicom.gui.language.UIString;
import clavicom.tools.TSwingUtils;

public class UIModificationKeyDialog extends JDialog
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	JButton btClose;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIModificationKeyDialog(JPanel insidePanel)
	{
		super();
		JButton btClose = new JButton(UIString.getUIString("LB_EDITION_OPTION_KEY_OK"));
		JPanel btPanel = new JPanel();
		
		btPanel.add(btClose);
		
		btClose.setAction(new BtCloseAction(UIString.getUIString("LB_EDITION_OPTION_KEY_OK")));		
		
		setLayout(new GridLayout());
		
		JPanel inPanel = new JPanel();
		inPanel.setLayout(new BorderLayout());
		
		inPanel.add(insidePanel, BorderLayout.CENTER);
		
		inPanel.add(btPanel, BorderLayout.SOUTH);
		
		add(inPanel);
		
		// Options par défaut
		setModal(true);
		
		// On dit qu'on veut qu'elle soit centrée
		setLocationRelativeTo( null );
		
		setResizable(false);
	}
	//----------------------------------------------------------- METHODES --//		
	@Override
	public void setVisible(boolean arg0)
	{
		// On centre à l'écran
		TSwingUtils.centerComponentToScreen(this);
		
		// TODO Auto-generated method stub
		super.setVisible(arg0);
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
