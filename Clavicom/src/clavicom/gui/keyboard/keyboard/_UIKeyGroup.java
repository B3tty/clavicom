/*-----------------------------------------------------------------------------+

			Filename			: UIKeyGroup.java
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

import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;

public class _UIKeyGroup
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	List<_UIKeyList> keyLists;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public _UIKeyGroup (CKeyGroup coreKeyGroup)
	{
		// Initialisation des attributs
		keyLists = new ArrayList<_UIKeyList>();
		
		// Récupération du nombre de listes 
		int keyListCount = coreKeyGroup.listCount();
		
		// Variables temporaires
		_UIKeyList currentKeyList;
		
		// On parcours tous les groupes
		for (int i = 0 ; i < keyListCount ; ++i)
		{
			// Création du UIKeyGroup
			currentKeyList = new _UIKeyList (coreKeyGroup.getkeyList(i));
			
			// Ajout de la KeyList au conteneur
			keyLists.add(currentKeyList);
		}
	}
	
	//----------------------------------------------------------- METHODES --//	
	/**
	 * Retourne la liste des UIKeyThreeLevel contenues par le groupe
	 */
	public List<UIKeyThreeLevel> getThreeLevelKeys()
	{
		// Construction de la liste des UIThreeLevelKeys
		List<UIKeyThreeLevel> threeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		
		// Parcours de keyLists
		for (_UIKeyList currentKeyList : keyLists)
		{
			// Ajout des threeLevelKeys
			threeLevelKeys.addAll(currentKeyList.getThreeLevelKeys());
		}
		
		// Retour
		return threeLevelKeys;
	}
	
	/**
	 * Retourne la liste des UIKey contenues par le groupe
	 */
	public List<UIKey> getKeys()
	{
		// Construction de la liste des UIThreeLevelKeys
		List<UIKey> keys = new ArrayList<UIKey>();
		
		// Parcours de keyLists
		for (_UIKeyList currentKeyList : keyLists)
		{
			// Ajout des UIKeys
			keys.addAll(currentKeyList.getKeys());
		}
		
		// Retour
		return keys;
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
