/*-----------------------------------------------------------------------------+

 Filename			: CLastWordEngine.java
 Creation date		: 1 juin 07
 
 Project				: Clavicom
 Package				: clavicom.core.engine

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

package clavicom.core.engine;

import java.util.ArrayList;
import java.util.List;

import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyDynamicString;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyOneLevel;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;

import clavicom.core.keygroup.keyboard.key.CKeyboardKey;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyDynamicStringListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.profil.CKeyboard;

public abstract class CStringsEngine
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	List<CKeyOneLevel> keyList; // liste des CKeyOneLevel
	List<String> stringList; // liste de chaine de caractere
	String currentString; // Chaine de caractere courrente
	
	CLevelEngine levelEngine; // moteur de level
	
	
	// ------------------------------------------------------ CONSTRUCTEURS --//

	
	public CStringsEngine( CKeyboard keyboard, CLevelEngine myLevelEngine )
	{
		levelEngine = myLevelEngine;
		
		keyList = new ArrayList<CKeyOneLevel>();
		
		currentString = "";
		
	}
	
	/**
	 * Initialisation de stringList au nombre de key
	 * @param max
	 */
	protected abstract void LoadStringList( ); 

	

	// ----------------------------------------------------------- METHODES --//
	

	// --------------------------------------------------- METHODES PRIVEES --//
	
	protected boolean IsEndWordCharacter(String character)
	{
		CCommandSet commandSet = CCommandSet.GetInstance();
		for( int i = 0 ; i < commandSet.EndOfWordCharacterListSize() ; ++i )
		{
			String eowCharacter = commandSet.GetEndOfWordCharacter( i );
			if( eowCharacter != null )
			{
				// s'il est égal a notre caractere
				if( eowCharacter.equals( character ) )
				{
					return true;
				}
			}
		}
		return false;
	}
	
	protected void updateKeyLastWord()
	{
		for( int i = 0 ; i < keyList.size() ; ++i )
		{
			CKeyOneLevel keyboardKey = keyList.get( i );
			String stringToDisplay = stringList.get( i );
			
			if( (keyboardKey != null ) && ( stringToDisplay != null ) )
			{
				keyboardKey.setCaption( stringToDisplay );
			}
		}
	}
	
}
