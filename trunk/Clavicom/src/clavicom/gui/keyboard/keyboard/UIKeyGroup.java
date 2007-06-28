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

import clavicom.core.engine.CLevelEngine;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;

public class UIKeyGroup
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	List<UIKeyList> keyLists;
	CKeyGroup		coreKeyGroup;
	
	//------------------------------------------------------ CONSTRUCTEURS --//	
	public UIKeyGroup (CKeyGroup myCoreKeyGroup, CLevelEngine levelEngine)
	{
		// Initialisation des attributs
		coreKeyGroup = myCoreKeyGroup;
		keyLists = new ArrayList<UIKeyList>();
		
		// Récupération du nombre de listes 
		int keyListCount = coreKeyGroup.listCount();
		
		// Variables temporaires
		UIKeyList currentKeyList;
		
		// On parcours tous les groupes
		for (int i = 0 ; i < keyListCount ; ++i)
		{
			// Création du UIKeyGroup
			currentKeyList = new UIKeyList (coreKeyGroup.getkeyList(i),levelEngine);
			
			// Ajout de la KeyList au conteneur
			keyLists.add(currentKeyList);
		}
	}
	
	
	//----------------------------------------------------------- METHODES --//
	
	public void simulateEnter( boolean selection )
	{
		// séléction de toutes les listes
		for( UIKeyList keyList : keyLists )
		{
			keyList.simulateEnter( selection );
		}
	}
	
	public void select(boolean select)
	{
		// séléction de toutes les listes
		for( UIKeyList keyList : keyLists )
		{
			keyList.select( select );
		}
	}
	
	public UIKeyList getListByCaption(String caption)
	{
		for(UIKeyList currentList : keyLists )
		{
			if(currentList.toString().equals(caption))
			{
				return currentList;
			}
		}
		
		return null;
	}
	
	public void moveListToIndex(int currentIndex, int requiredIndex)
	{
		// Sauvegarde des objets
		UIKeyList uiKeyList = keyLists.get(currentIndex);
		CKeyList cKeyList = uiKeyList.getCoreKeyList();
		
		// Déplacement de l'objet du noyau
		coreKeyGroup.removeList(cKeyList);
		coreKeyGroup.AddKeyList(requiredIndex, cKeyList);
		
		// Déplacement de l'objet de l'UI
		keyLists.remove(uiKeyList);
		keyLists.add(requiredIndex, uiKeyList);	
	}
	
	
	/**
	 * Retourne la liste des UIKeyThreeLevel contenues par le groupe
	 */
	public List<UIKeyThreeLevel> getThreeLevelKeys()
	{
		// Construction de la liste des UIThreeLevelKeys
		List<UIKeyThreeLevel> threeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		
		// Parcours de keyLists
		for (UIKeyList currentKeyList : keyLists)
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
	public List<UIKeyKeyboard> getKeys()
	{
		// Construction de la liste des UIThreeLevelKeys
		List<UIKeyKeyboard> keys = new ArrayList<UIKeyKeyboard>();
		
		// Parcours de keyLists
		for (UIKeyList currentKeyList : keyLists)
		{
			// Ajout des UIKeys
			keys.addAll(currentKeyList.getKeys());
		}
		
		// Retour
		return keys;
	}

	public List<UIKeyList> getKeyLists()
	{
		return keyLists;
	}

	public CKeyGroup getCoreKeyGroup()
	{
		return coreKeyGroup;
	}
	
	public void removeUIList(UIKeyList list)
	{
		keyLists.remove(list);
	}

	public void removeLists(List<UIKeyList> listsToDelete)
	{
		keyLists.removeAll(listsToDelete);		
	}
	
	@Override
	public String toString()
	{
		return coreKeyGroup.toString();
	}
	//--------------------------------------------------- METHODES PRIVEES --//
}
