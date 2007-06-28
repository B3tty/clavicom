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

import clavicom.core.engine.dictionary.CDictionary;
import clavicom.core.engine.dictionary.CDictionaryWord;
import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.core.keygroup.keyboard.blocks.CKeyList;
import clavicom.core.keygroup.keyboard.command.CCommand;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyDynamicString;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.core.listener.OnClickKeyCharacterListener;
import clavicom.core.listener.OnClickKeyDynamicStringListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CPreferedWords;

public class CPredictionEngine extends CStringsEngine implements
		OnClickKeyCharacterListener, OnClickKeyDynamicStringListener, OnClickKeyShortcutListener
{
	// --------------------------------------------------------- CONSTANTES --//

	// ---------------------------------------------------------- VARIABLES --//
	
	CPreferedWords preferedWord; // mot de l'utilisateur

	
	static CPredictionEngine instance;
	
	// ------------------------------------------------------ CONSTRUCTEURS --//
	public static void createInstance( 
			CKeyboard keyboard, 
			CPreferedWords myPreferedWord )
	{
		instance = new CPredictionEngine( keyboard, myPreferedWord );
	}
	
	public static CPredictionEngine getInstance()
	{
		return instance;
	}
	
	protected CPredictionEngine( 
			CKeyboard keyboard, 
			CPreferedWords myPreferedWord)
	{
		super( keyboard );
		
		preferedWord = myPreferedWord;
		
		// Abonnement aux listeners
		listen( keyboard );
		
	}
	
	public void listen( CKeyKeyboard keyboardKey )
	{
		// on cast pour savoir si le type est bien
		// keyLauncher
		if( keyboardKey instanceof CKeyPrediction )
		{
			CKeyPrediction keyPrediction = (CKeyPrediction)keyboardKey;
			if( keyPrediction != null )
			{
				// Ajout à la liste des keyLastWord
				keyList.add(keyPrediction);
			}
		}else if( keyboardKey instanceof CKeyCharacter )
		{
			((CKeyCharacter)keyboardKey).addOnClickKeyCharacterListener( this );
		}else if( keyboardKey instanceof CKeyDynamicString )
		{
			((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListener( this );
		}else if( keyboardKey instanceof CKeyShortcut )
		{
			((CKeyShortcut)keyboardKey).addOnClickKeyShortcutListener( this );
		}
	}

	public void listen( CKeyboard keyboard )
	{
		List<CKeyPrediction> keyPredictionListTemp = new ArrayList<CKeyPrediction>();

		
		// ========================================================
		// création de la liste des keyLastWord et abonnement
		// ========================================================
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
								if( keyboardKey instanceof CKeyPrediction )
								{
									CKeyPrediction keyPrediction = (CKeyPrediction)keyboardKey;
									if( keyPrediction != null )
									{
										// Ajout à la liste des keyLastWord
										keyPredictionListTemp.add(keyPrediction);
									}
								}else if( keyboardKey instanceof CKeyCharacter )
								{
									((CKeyCharacter)keyboardKey).addOnClickKeyCharacterListener( this );
								}else if( keyboardKey instanceof CKeyDynamicString )
								{
									((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListener( this );
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
		for( int i = 0 ; i < keyPredictionListTemp.size() ; ++i )
		{
			for( CKeyPrediction predictionWord : keyPredictionListTemp )
			{
				if( predictionWord.getOrder() == i )
				{
					keyList.add( predictionWord );
				}
			}
		}
	}
	
	public void unListen( CKeyKeyboard keyboardKey )
	{
		// on cast pour savoir si le type est bien
		// keyLauncher
		if( keyboardKey instanceof CKeyPrediction )
		{
			CKeyPrediction keyPrediction = (CKeyPrediction)keyboardKey;
			if( keyPrediction != null )
			{
				// Ajout à la liste des keyLastWord
				keyList.remove(keyPrediction);
			}
		}else if( keyboardKey instanceof CKeyCharacter )
		{
			((CKeyCharacter)keyboardKey).removeOnClickKeyCharacterListener( this );
		}else if( keyboardKey instanceof CKeyDynamicString )
		{
			((CKeyDynamicString)keyboardKey).removeOnClickKeyDynamicStringListener( this );
		}else if( keyboardKey instanceof CKeyShortcut )
		{
			((CKeyShortcut)keyboardKey).removeOnClickKeyShortcutListener( this );
		}
	}

	// ----------------------------------------------------------- METHODES --//
	
	public void onClickKeyCharacter(CKeyCharacter keyCharacter)
	{
		if (keyCharacter.getCommand( CLevelEngine.getInstance().getCurrentLevel()) == null)
			return;
		
		CCommand command = keyCharacter.getCommand( CLevelEngine.getInstance().getCurrentLevel() );
		String character = command.GetSearchString();
		
		// Si ce caractere est un caractere de fin de mot
		if( IsEndWordCharacter( character ) )
		{
			SaveAndClean();
		}
		else
		{
			// on l'ajoute au mot courrant
			currentString += character;
			
			// on récupère la liste des mots de prédiction
			stringList = CDictionary.getWords( currentString , keyList.size() );
			
			// réaffiche les bouttons
			updateKeys();
		}
	}
	

	public void onClickKeyDynamicString(CKeyDynamicString keyDynamicString)
	{
		SaveAndClean();
	}
	
	public void onClickKeyShortcut(CKeyShortcut keyShortcut)
	{
		SaveAndClean();
	}
	
	protected void SaveAndClean()
	{
		if( ! currentString.equals( "" ) )
		{
			// on sauvegarde le mot courrent dans le dictionnaire et dans les prefered Words
			CDictionaryWord dictionaryWord = CDictionary.getWord( currentString );
			if( dictionaryWord == null )
			{
				dictionaryWord = new CDictionaryWord( currentString, 0 );
				
				// Ajout au dictionnaire
				CDictionary.addWord( CDictionary.getUserDictionnaryLevel(),dictionaryWord );
			}
			
			dictionaryWord.increaseFrequency();
			
			// ajout a la liste des preferedWords
			preferedWord.addPreferedWord( dictionaryWord );
			
			// on vide la chaine courrante
			currentString = "";
			
			// on vide la liste des strings
			stringList.clear();
			
			// réaffiche les bouttons
			updateKeys();
		}
	}


	// --------------------------------------------------- METHODES PRIVEES --//
}
