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
import java.awt.GridLayout;
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
		
//		//JPanel global = new JPanel();
//		//setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));		
//		JPanel insidePanelBigger = new JPanel();
//		
//		setLayout(new BorderLayout());
//		
//		insidePanelBigger.add(insidePanel, BorderLayout.CENTER);
//		
//		add(insidePanelBigger);
//		
//		JButton btClose = new JButton(UIString.getUIString("LB_EDITION_OPTION_KEY_OK"));
//		
//		btClose.setAction(new BtCloseAction(UIString.getUIString("LB_EDITION_OPTION_KEY_OK")));
//		
//		//JPanel panelBouton = new JPanel();
//		//panelBouton.add(btClose);
//		//add(btClose);
//		//global.add(panelBouton);
//		
//		//add(global);
//		
//		// Layouts
////		GridBagLayout gbLayoutMain = new GridBagLayout();
////		setLayout(gbLayoutMain);
////		
////		global.setBackground(Color.red);
////		
////		// Contraintes du panel avec la liste d'outils
////		GridBagConstraints gbConstMain = new GridBagConstraints (	
////				0,							// Numéro de colonne
////	            0,							// Numéro de ligne
////	            1,							// Nombre de colonnes occupées
////	            1,							// Nombre de lignes occupées
////	            0,							// Taille horizontale relative
////	            0,							// Taille verticale relative
////	            GridBagConstraints.BOTH,	// Ou placer le composant en cas de redimension
////	            GridBagConstraints.BOTH,	// Manière de rétrécir le composant
////	            new Insets(5, 5, 5, 5),		// Espace autours (haut, gauche, bas, droite)
////	            0,							// Espace intérieur en X
////	            0							// Espace intérieur en Y
////	    );
////		gbLayoutMain.setConstraints(this, gbConstMain);
		
		// Options par défaut
		setModal(true);
		
		setSize(new Dimension(300,500));
	}
	//----------------------------------------------------------- METHODES --//		

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
