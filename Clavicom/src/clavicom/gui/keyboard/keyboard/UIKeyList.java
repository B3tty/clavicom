/*-----------------------------------------------------------------------------+

			Filename			: UIKeyList.java
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
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeySound;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.gui.keyboard.key.UIKey;
import clavicom.gui.keyboard.key.UIKeyCharacter;
import clavicom.gui.keyboard.key.UIKeyClavicom;
import clavicom.gui.keyboard.key.UIKeyKeyboard;
import clavicom.gui.keyboard.key.UIKeyLastWord;
import clavicom.gui.keyboard.key.UIKeyLauncher;
import clavicom.gui.keyboard.key.UIKeyLevel;
import clavicom.gui.keyboard.key.UIKeyPrediction;
import clavicom.gui.keyboard.key.UIKeyShortcut;
import clavicom.gui.keyboard.key.UIKeySound;
import clavicom.gui.keyboard.key.UIKeyString;
import clavicom.gui.keyboard.key.UIKeyThreeLevel;
import clavicom.tools.TUIKeyState;

public class UIKeyList
{
	//--------------------------------------------------------- CONSTANTES --//
	List<UIKeyKeyboard> keys;				// Liste des UIKey
	List<UIKeyThreeLevel> threeLevelKeys;	// Liste des UIKeyThreeLevel
	
	CKeyList coreKeyList;					// Objet du noyau
	//---------------------------------------------------------- VARIABLES --//	

	//------------------------------------------------------ CONSTRUCTEURS --//		
	public UIKeyList(CKeyList myCoreKeyList)
	{
		// Initialisation des attributs
		coreKeyList = myCoreKeyList;
		keys = new ArrayList<UIKeyKeyboard>();
		threeLevelKeys = new ArrayList<UIKeyThreeLevel>();
		
		// Récupération du nombre de keys 
		int keyCount = coreKeyList.keyCount();
		
		// Variables temporaires
		CKeyKeyboard currentCoreKey;
		
		// On parcours tous les groupes
		for (int i = 0 ; i < keyCount ; ++i)
		{		
			// On récupère la CKeyKeyboard courante
			currentCoreKey = coreKeyList.getKeyKeyboard(i);
			
			// On caste pour savoir quel objet on doit créer
			if (currentCoreKey instanceof CKeyCharacter)
			{
				// Construction d'une UIKey du bon type
				UIKeyCharacter uiKey = new UIKeyCharacter((CKeyCharacter)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
				
				// Ajout à la liste des keyThreeLevel
				threeLevelKeys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyPrediction)
			{
				// Construction d'une UIKey du bon type
				UIKeyPrediction uiKey = new UIKeyPrediction((CKeyPrediction)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyLastWord)
			{
				// Construction d'une UIKey du bon type
				UIKeyLastWord uiKey = new UIKeyLastWord((CKeyLastWord)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyLauncher)
			{
				// Construction d'une UIKey du bon type
				UIKeyLauncher uiKey = new UIKeyLauncher((CKeyLauncher)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeySound)
			{
				// Construction d'une UIKey du bon type
				UIKeySound uiKey = new UIKeySound((CKeySound)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyLevel)
			{
				// Construction d'une UIKey du bon type
				UIKeyLevel uiKey = new UIKeyLevel((CKeyLevel)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyShortcut)
			{
				// Construction d'une UIKey du bon type
				UIKeyShortcut uiKey = new UIKeyShortcut((CKeyShortcut)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyString)
			{
				// Construction d'une UIKey du bon type
				UIKeyString uiKey = new UIKeyString((CKeyString)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
			else if (currentCoreKey instanceof CKeyClavicom)
			{
				// Construction d'une UIKey du bon type
				UIKeyClavicom uiKey = new UIKeyClavicom((CKeyClavicom)currentCoreKey);
				
				// Ajout à la liste des uiKeys
				keys.add(uiKey);
			}
		}
	}
	//----------------------------------------------------------- METHODES --//	
	
	public void moveKeyToIndex(int currentIndex, int requiredIndex)
	{
		// Sauvegarde des objets
		UIKeyKeyboard uiKey = keys.get(currentIndex);
		CKeyKeyboard cKey = (CKeyKeyboard)uiKey.getCoreKey();
		
		// Déplacement de l'objet du noyau
		coreKeyList.removeKey(cKey);
		coreKeyList.addKeyboardKey(requiredIndex,cKey);
		
		// Déplacement de l'objet de l'UI
		keys.remove(uiKey);
		keys.add(requiredIndex, uiKey);	
	}
	
	public void simulateEnter( boolean selection )
	{
		// séléction de toutes les touches
		for( UIKeyKeyboard keyboardKey : keys )
		{
			if( selection )
			{
				keyboardKey.forceState( TUIKeyState.ENTERED );
			}
			else
			{
				keyboardKey.forceState( TUIKeyState.NORMAL );
			}
		}
	}
	
	public void select (boolean selection )
	{
		// séléction de toutes les touches
		for( UIKeyKeyboard keyboardKey : keys )
		{
			keyboardKey.setSelected(selection);
		}
	}

	public List<UIKeyKeyboard> getKeys()
	{
		return keys;
	}
	
	public List<UIKeyThreeLevel> getThreeLevelKeys()
	{
		return threeLevelKeys;
	}
	
	public boolean removeKey(UIKeyKeyboard key)
	{
		return keys.remove(key);
	}

	public CKeyList getCoreKeyList()
	{
		return coreKeyList;
	}

	public void removeKeys(List<UIKey> keysToDelete)
	{
		keys.removeAll(keysToDelete);
		threeLevelKeys.retainAll(keysToDelete);
	}
	
	@Override
	public String toString()
	{
		// TODO Auto-generated method stub
		return coreKeyList.toString();
	}
	
	//--------------------------------------------------- METHODES PRIVEES --//
}
