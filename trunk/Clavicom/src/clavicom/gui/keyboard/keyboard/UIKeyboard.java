/*-----------------------------------------------------------------------------+

			Filename			: UIKeyboard.java
			Creation date		: 7 juin 07
		
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

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import clavicom.core.profil.CKeyboard;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;

public class UIKeyboard extends JPanel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	private List<_UIKeyGroup> keyGroups;				// Liste des UIKeyGroups
	private List<UIKey> allKeys;					// Liste des keys
	private List<UIKeyThreeLevel> threeLevelKeys;	// Liste des ThreeLevelKeys
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	/**
	 * Créé l'UIKeyboard à partir du CKeyboard
	 */
	public UIKeyboard(CKeyboard coreKeyboard)
	{
		// Initialisation des attributs
		keyGroups = new ArrayList<_UIKeyGroup>();
		allKeys = new ArrayList<UIKey>();
		threeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		
		// Récupération du nombre de groupes 
		int groupCount = coreKeyboard.groupCount();
		
		// Variables temporaires
		_UIKeyGroup currentKeyGroup;
		List<UIKeyThreeLevel> currentThreeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		List<UIKey> currentKeys = new ArrayList<UIKey>();
		
		// On parcours tous les groupes
		for (int i = 0 ; i < groupCount ; ++i)
		{
			// Création du UIKeyGroup
			currentKeyGroup = new _UIKeyGroup (coreKeyboard.getKeyGroup(i));
			
			// Demande de récupération des ThreeLevelKeys
			currentThreeLevelKeys.clear();
			currentThreeLevelKeys = currentKeyGroup.getThreeLevelKeys();
			
			if(currentThreeLevelKeys != null)
			{
				threeLevelKeys.addAll(currentThreeLevelKeys);
			}
			
			// Demande de récupération des Keys
			currentKeys.clear();
			currentKeys = currentKeyGroup.getKeys();
			
			if(currentKeys != null)
			{
				allKeys.addAll(currentKeys);
			}
		}
		
		// Ajout des touches au panel
		addUIKeys();
		
	}

	//----------------------------------------------------------- METHODES --//	

	//--------------------------------------------------- METHODES PRIVEES --//
	private void addUIKeys()
	{
		for (UIKey currentKey : allKeys)
		{
			// Ajout au panel
			add(currentKey);
		}
	}
}
