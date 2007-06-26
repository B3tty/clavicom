/*-----------------------------------------------------------------------------+

			Filename			: UILevelManagerFrame.java
			Creation date		: 26 juin 07
		
			Project				: Clavicom
			Package				: clavicom.gui.levelmanager

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

package clavicom.gui.levelmanager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.ListUI;

import clavicom.CFilePaths;
import clavicom.tools.TImageUtils;

public class UILevelManagerFrame extends JFrame
{
	//--------------------------------------------------------- CONSTANTES --//
	private final int BT_IMAGE_SIZE = 25;
	
	//---------------------------------------------------------- VARIABLES --//	
	// Panels principaux
	JPanel panelGlobal;
	JPanel panelUnclassedKeys;
	JPanel panelClassedKeys;
	
	// Panels secondaires
	UILevelList panelGroups;
	UILevelList panelLists;
	UILevelList panelKeys;
	
	// Composants
	JButton btClassKey;
	//JList listUnclassedKeys;
	
	// TODO : ajouter --> JButton btClassAutomaticKey;
	
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UILevelManagerFrame()
	{
		// Création des objets
		createObjects();
		
		// Création du panel de touches non classées
		createPanelUnclassedKeys();
		
		// Création du panel de touches classées
		createPanelClassedKeys();
		
	}
	
	//----------------------------------------------------------- METHODES --//	
	protected void createObjects()
	{
		// Panels principaux
		panelGlobal = new JPanel();
		panelUnclassedKeys = new JPanel();
		panelClassedKeys = new JPanel();
		
		// Panels secondaires
		panelGroups = new UILevelList();
		panelLists = new UILevelList();
		panelKeys = new UILevelList();
		
		// Composants
		btClassKey = new JButton();
		//listUnclassedKeys = new JList();
	}
	
	protected void createPanelUnclassedKeys()
	{
		// Image du bouton
		btClassKey.setIcon(TImageUtils.scaleImage(TImageUtils.getImage(CFilePaths.getLevelEditorAdd()),BT_IMAGE_SIZE,-1));
		
		//panelUnclassedKeys.add(L)
	}
	
	protected void createPanelClassedKeys()
	{
		
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
