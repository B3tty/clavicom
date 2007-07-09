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
import clavicom.core.listener.OnClickKeyDynamicStringPredictionListener;
import clavicom.core.listener.OnClickKeyShortcutListener;
import clavicom.core.profil.CKeyboard;
import clavicom.core.profil.CPreferedWords;
import clavicom.core.profil.CProfil;

public class CPredictionEngine extends CStringsEngine implements
		OnClickKeyCharacterListener, OnClickKeyDynamicStringPredictionListener, OnClickKeyShortcutListener
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
			((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListenerPrediction( this );
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
									((CKeyDynamicString)keyboardKey).addOnClickKeyDynamicStringListenerPrediction( this );
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
		// tri de la liste
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
			((CKeyDynamicString)keyboardKey).removeOnClickKeyDynamicStringListenerPrediction( this );
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
			// si ce n'est qu'on caracter ( pas de F1, ESCAPE, SPACE ... )
			if( character.length() == 1 )
			{
				// on l'ajoute au mot courrant
				currentString += character;
				
				// on récupère la liste des mots de prédiction
				// Dans un nouveau thread pour éviter de faire rammer l'appli
				Thread threadDico = new Thread()
				{
					public void run() 
					{
						stringList = CDictionary.getWords( currentString , keyList.size() );
						
						// réaffiche les bouttons
						updateKeys();
					}
				};
				threadDico.setPriority( Thread.MIN_PRIORITY );
				threadDico.run();
			}
		}
	}
	

	public void onClickKeyDynamicStringPrediction(CKeyDynamicString keyDynamicString)
	{
		currentString = keyDynamicString.getCaption();
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
			// si l'utilisateur veux sauvegarder ces mots préférés
			if( CProfil.getInstance().getPreferedWords().isActive() )
			{
				// on ajout ou on augmente la frequence du mots
				addOrIncreaseWord( currentString );
			}
			
			// on vide la chaine courrante
			currentString = "";
			
			// on vide la liste des strings
			stringList.clear();
			
			// réaffiche les bouttons
			updateKeys();
		}
	}
	
	void addOrIncreaseWord( String currentString )
	{
		// si le mot courant à plus d'un caractère
		if( currentString.length() > 1 )
		{
			CDictionaryWord dictionaryWord = null;
			
			//	s'il n'est pas dans les preferedWords
			if( ! preferedWord.contain( currentString ) )
			{
				dictionaryWord = new CDictionaryWord( currentString, 1 );
				preferedWord.addPreferedWord( dictionaryWord );
				CDictionary.addWord(CDictionary.getUserDictionnaryLevel(), dictionaryWord);
			}
			else
			{
				dictionaryWord = preferedWord.getPreferedWord( currentString );
				if( dictionaryWord != null )
				{
					dictionaryWord.increaseFrequency();
				}
			}
		}
	}


	// --------------------------------------------------- METHODES PRIVEES --//
}
