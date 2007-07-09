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
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyDynamicString;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyDynamicStringPredictionListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.profil.CKeyboard;

public class CLastWordEngine extends CStringsEngine implements
		OnClickKeyCharacterListener, OnClickKeyDynamicStringPredictionListener, OnClickKeyShortcutListener
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	static CLastWordEngine instance;
	// ------------------------------------------------------ CONSTRUCTEURS --//
	public static void createInstance( CKeyboard keyboard )
	{
		instance = new CLastWordEngine( keyboard );
	}
	
	public static CLastWordEngine getInstance()
	{
		return instance;
	}
	
	protected CLastWordEngine( CKeyboard keyboard )
	{
		super( keyboard );
		
		// Abonnement aux listeners
		listen( keyboard );
	}
	
	public void listen( CKeyKeyboard keyboardKey )
	{
		if( keyboardKey != null )
		{
			// on cast pour savoir si le type est bien
			// keyLauncher
			if( keyboardKey instanceof CKeyLastWord )
			{
				CKeyLastWord keyLastWord = (CKeyLastWord)keyboardKey;
				if( keyLastWord != null )
				{
					// Ajout à la liste des keyLastWord
					keyList.add(keyLastWord);
				}
			}else if( keyboardKey instanceof CKeyCharacter )
			{
				((CKeyCharacter)keyboardKey).addOnClickKeyCharacterListener( this );
			}else if( keyboardKey instanceof CKeyDynamicString )
			{
				((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListenerPrediction( this );
			}else if( keyboardKey instanceof CKeyShortcut )
			{
				((CKeyShortcut)keyboardKey).addOnClickKeyShortcutListener( this );
			}
		}
	}
	public void listen( CKeyboard keyboard )
	{
		// ========================================================
		// création de la liste des keyLastWord et abonnement
		// ========================================================
		List<CKeyLastWord> keyLastWordListTemp = new ArrayList<CKeyLastWord>();
		for( int i = 0 ; i < keyboard.groupCount() ; ++i )
		{
			CKeyGroup keyGroup = keyboard.getKeyGroup( i );
			if( keyGroup != null )
			{
				for( int j = 0 ; j < keyGroup.listCount() ; ++j )
				{
					CKeyList keyList = keyGroup.getkeyList( j );
					if( keyList != null )
					{
						for( int k = 0 ;  k < keyList.keyCount() ; ++k )
						{
							CKeyKeyboard keyboardKey = keyList.getKeyKeyboard( k );
							if( keyboardKey != null )
							{
								// on cast pour savoir si le type est bien
								// keyLauncher
								if( keyboardKey instanceof CKeyLastWord )
								{
									CKeyLastWord keyLastWord = (CKeyLastWord)keyboardKey;
									if( keyLastWord != null )
									{
										// Ajout à la liste des keyLastWord
										keyLastWordListTemp.add(keyLastWord);
									}
								}else if( keyboardKey instanceof CKeyCharacter )
								{
									((CKeyCharacter)keyboardKey).addOnClickKeyCharacterListener( this );
								}else if( keyboardKey instanceof CKeyDynamicString )
								{
									((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListenerPrediction( this );
								}else if( keyboardKey instanceof CKeyShortcut )
								{
									((CKeyShortcut)keyboardKey).addOnClickKeyShortcutListener( this );
								}
							}
						}
					}
				}
			}
		}
		
		// ========================================================
		// tri de la liste des keyLastWord
		// ========================================================
		keyList.clear();
		for( int i = 0 ; i < keyLastWordListTemp.size() ; ++i )
		{
			for( CKeyLastWord keyLastWord : keyLastWordListTemp )
			{
				if( keyLastWord.getOrder() == i )
				{
					keyList.add( keyLastWord );
				}
			}
		}
	}
	
	public void unListen( CKeyKeyboard keyboardKey )
	{
		// on cast pour savoir si le type est bien
		// keyLauncher
		if( keyboardKey instanceof CKeyLastWord )
		{
			CKeyLastWord keyLastWord = (CKeyLastWord)keyboardKey;
			if( keyLastWord != null )
			{
				// Ajout à la liste des keyLastWord
				keyList.remove(keyLastWord);
			}
		}else if( keyboardKey instanceof CKeyCharacter )
		{
			((CKeyCharacter)keyboardKey).removeOnClickKeyCharacterListener( this );
		}else if( keyboardKey instanceof CKeyDynamicString )
		{
			((CKeyDynamicString)keyboardKey).removeOnClickKeyDynamicStringListenerPrediction( this );
		}else if( keyboardKey instanceof CKeyShortcut )
		{
			((CKeyShortcut)keyboardKey).removeOnClickKeyShortcutListener( this );
		}
	}
	

	// ----------------------------------------------------------- METHODES --//
	
	public void onClickKeyCharacter(CKeyCharacter keyCharacter)
	{
		if(keyCharacter.getCommand(CLevelEngine.getInstance().getCurrentLevel()) == null)
			return;
		
		CCommand command = keyCharacter.getCommand( CLevelEngine.getInstance().getCurrentLevel() );
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
				updateKeys();
			}
		}
		else
		{
			// on l'ajoute au mot courrant
			currentString += character;
		}

	}
	

	public void onClickKeyDynamicStringPrediction(CKeyDynamicString keyDynamicString)
	{		
		if( keyDynamicString.GetStringCommand() != "" )
		{
			// on l'ajoute au début de la liste
			stringList.add( 0, keyDynamicString.GetStringCommand() );
		}
		
		// on met la chaine de keyDynamicString dans la string courrante
		currentString = "";
		
		// on met a jour les touches
		updateKeys();
	}
	
	public void onClickKeyShortcut(CKeyShortcut keyShortcut)
	{
		// si la chaine courrante n'est pas vide
		if( ! currentString.equals("") )
		{
			// on l'ajoute au début de la liste
			stringList.add( 0, currentString );
		}
		
		currentString = "";
		
		// on met a jour les touches
		updateKeys();
	}



	// --------------------------------------------------- METHODES PRIVEES --//
	
}
