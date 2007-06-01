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
import clavicom.core.keygroup.keyboard.key.CKeyboardKey;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyDynamicStringListener;
import clavicom.core.profil.CKeyboard;

public class CLastWordEngine extends CLevelEngine implements
		OnClickKeyCharacterListener, OnClickKeyDynamicStringListener
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	List<CKeyLastWord> keyLastWordList; // liste des keyLastWord
	List<String> stringList; // liste de chaine de caractere
	String currentString; // Chaine de caractere courrente
	
	
	// ------------------------------------------------------ CONSTRUCTEURS --//

	
	public CLastWordEngine( CKeyboard keyboard )
	{
		super( keyboard );
		
		keyLastWordList = new ArrayList<CKeyLastWord>();
		
		currentString = "";
		
		// ========================================================
		// création de la liste des keyLastWord et abonnement
		// ========================================================
		for( int i = 0 ; i < keyboard.size() ; ++i )
		{
			CKeyGroup keyGroup = keyboard.getKeyGroup( i );
			if( keyGroup != null )
			{
				for( int j = 0 ; j < keyGroup.size() ; ++j )
				{
					CKeyList keyList = keyGroup.GetkeyList( j );
					if( keyList != null )
					{
						for( int k = 0 ;  k < keyList.size() ; ++k )
						{
							CKeyboardKey keyboardKey = keyList.GetKeyboardKey( k );
							if( keyboardKey != null )
							{
								// on cast pour savoir si le type est bien
								// keyLauncher
								if( keyboardKey instanceof CKeyLastWord )
								{
									CKeyLastWord keyLastWord = (CKeyLastWord)keyboardKey;
									if( keyLastWord != null )
									{
										// Abonnement
										keyLastWord.addOnClickKeyDynamicStringListener( this );
										
										// Ajout à la liste des keyLastWord
										keyLastWordList.add(keyLastWord);
									}
								}
							}
						}
					}
				}
			}
		}
		
		stringList = new ArrayList<String>( keyLastWordList.size() );
	}

	

	// ----------------------------------------------------------- METHODES --//
	
	public void onClickKeyCharacter(CKeyCharacter keyCharacter)
	{
		CCommand command = keyCharacter.getCommand( currentLevel );
		String character = command.GetSearchString();
		
		// Si ce caractere est un caractere de fin de mot
		if( IsEndWordCharacter( character ) )
		{
			// si la chaine courrente n'est pas vide
			if( ! currentString.equals("") )
			{
				// on l'ajoute au début de la liste
				stringList.add( 0, currentString );
				
				// on vide la chaine courrante
				currentString = "";
				
				// on mes a jour les touches
				updateKeyLastWord();
			}
		}
		else
		{
			// on l'ajoute au mot courrant
			currentString += character;
		}

	}


	

	public void onClickKeyDynamicString(CKeyDynamicString keyDynamicString)
	{
		// si la chaine courrante n'est pas vide
		if( ! currentString.equals("") )
		{
			// on l'ajoute au début de la liste
			stringList.add( 0, currentString );
		}
		
		// on met la chaine de keyDynamicString dans la string courrante
		currentString = keyDynamicString.GetStringCommand();
		
		// on met a jour les touches
		updateKeyLastWord();
	}

	// --------------------------------------------------- METHODES PRIVEES --//
	
	static private boolean IsEndWordCharacter(String character)
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
	
	private void updateKeyLastWord()
	{
		for( int i = 0 ; i < keyLastWordList.size() ; ++i )
		{
			CKeyLastWord keyLastWord = keyLastWordList.get( i );
			String stringToDisplay = stringList.get( i );
			
			if( (keyLastWord != null ) && ( stringToDisplay != null ) )
			{
				keyLastWord.setCaption( stringToDisplay );
			}
		}
	}
}
