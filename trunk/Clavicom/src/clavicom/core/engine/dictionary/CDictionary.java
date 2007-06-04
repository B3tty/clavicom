/*-----------------------------------------------------------------------------+

			Filename			: CDictionary.java
			Creation date		: 4 juin 07
		
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

package clavicom.core.engine.dictionary;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import clavicom.CFilePaths;
import clavicom.core.profil.CDictionaryName;
import clavicom.core.profil.CPreferedWords;
import clavicom.gui.language.UIString;

public class CDictionary
{
	//--------------------------------------------------------- CONSTANTES --//
	static int nbDictionaryLevel = 4;

	//---------------------------------------------------------- VARIABLES --//
	CDictionaryLevel dictionaryLevel_0;
	
	// == TMP POUR TEST ================================================
	public CDictionaryLevel getDictionaryLevel_0()
	{
		return dictionaryLevel_0;
	}
	// == TMP POUR TEST ================================================

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CDictionary( 
			CDictionaryName dictionaryName,
			CPreferedWords preferedWords
			) throws Exception
	{
		dictionaryLevel_0 = new CDictionaryLevel( new Character(' ') ); // le caractere du niveau 0, on s'en fou
		
		// Chargement du fichier de dictionaire
		LoadDictionary( dictionaryName );
		
		// Chargement des preferedWords
		LoadPreferedWords( preferedWords );
	}

	
	//----------------------------------------------------------- METHODES --//
	
	private void LoadPreferedWords(CPreferedWords preferedWords)
	{
		CDictionaryWord preferedWord;
		for( int i = 0 ; i < preferedWords.getSize() ; ++i )
		{
			preferedWord = preferedWords.getPreferedWord( i );
			if( preferedWord != null )
			{
				addWord( new CDictionaryWord(  preferedWord.getWord(), preferedWord.getFrequency() ) );
			}
		}
		
	}
	
	private void LoadDictionary(CDictionaryName dictionaryName) throws Exception
	{
		InputStream ips = null; 
		InputStreamReader ipsr = null;
		BufferedReader br = null;
		
		String dictionaryPath = CFilePaths.getDictionaries() + dictionaryName.getDictionaryName();
		
		// ouverture du fichier
		try
		{
			ips = new FileInputStream( dictionaryPath ); 
			ipsr = new InputStreamReader(ips, "UTF-8");
			br = new BufferedReader(ipsr);
		}
		catch (Exception e)
		{
			throw new Exception("[" + UIString.getUIString( "EX_DICTIONARY_BUILD" ) + "] :" + UIString.getUIString("EX_DICTIONARY_OPEN") + dictionaryPath + " : " + e.getMessage() );
		}
		
		// parcourt du fichier
		try
		{
			String word;
			
			while ( (word = br.readLine() ) != null )
			{
				// pour chaque mot - on met tout en minuscule
				addWord( new CDictionaryWord( word.toLowerCase(), 0 ) );
			}
			
			br.close(); 
		}		
		catch (Exception e)
		{
			throw new Exception("[" + UIString.getUIString( "EX_DICTIONARY_BUILD" ) + "] :" + UIString.getUIString("EX_DICTIONARY_READ") + " : " + e.getMessage() );
		}

	}

	public void addWord( CDictionaryWord newDictionaryWord )
	{
		Character currentCharacter;
		CDictionaryLevel currentDictionaryLevel = dictionaryLevel_0;
		CDictionaryLevel currentDictionaryLevelTemp;
		
		for( int i = 0 ; i < nbDictionaryLevel ; ++i )
		{
			// Si le caractere existe
			if( newDictionaryWord.getWord().length() > i )
			{
				// on récupere ce caractere
				currentCharacter = newDictionaryWord.getWord().charAt( i );
				
				// on recupere le dictionaryLevel corespondant
				currentDictionaryLevelTemp = currentDictionaryLevel.getDictionaryLevel( currentCharacter );
				
				// si ce level n'existe pas, on le creer et on l'ajoute au level courrant
				if( currentDictionaryLevelTemp == null )
				{
					currentDictionaryLevelTemp = new CDictionaryLevel( currentCharacter );
					currentDictionaryLevel.addDictionaryLevel( currentDictionaryLevelTemp );
				}
				
				// le nouveau level devient le level courrant
				currentDictionaryLevel = currentDictionaryLevelTemp;			
				
			}
			else
			{
				// si le caractere n'existe pas (si le mot est plus petit que le nbDictionaryLevel )
				// on l'ajoute dans le dictionaryLevel courrant
				currentDictionaryLevel.addDictionaryWord( newDictionaryWord );
				

				// on quitte la fonction car on a plus rien à y faire
				return;
			}

		}
		
		currentDictionaryLevel.addDictionaryWord( newDictionaryWord );
	}

	/**
	 * Retourne une liste de mot dans l'ordre des préférences
	 * @param beginString
	 * @param nbOfWord
	 * @return
	 */
	public List<String> getWords( String beginString, int nbOfWord )
	{
		List<CDictionaryWord> dictionaryWordList = new ArrayList<CDictionaryWord>( nbOfWord );
		
		CDictionaryLevel currentLevel = dictionaryLevel_0;
		List<CDictionaryWord> wordExistedList;
		int minFrequency = 0;
		
		// pour tous les mots suceptibles de répondrent aux attentes
		for( int i = 0 ; i < beginString.length() ; ++i )
		{
			currentLevel = currentLevel.getDictionaryLevel( beginString.charAt( i ) );
			if( currentLevel == null )
			{
				break;
			}
			
			// Ajout des elements à la list à renvoyer
			wordExistedList = currentLevel.getDictionaryWordOrededList();
			
			for( int j = 0 ; j < wordExistedList.size() ; ++j )
			{
				CDictionaryWord dictionaryWord = wordExistedList.get( j );
				// si la frequence est plus basse que celle du minimum de la liste, 
				// ce n'est pas la peine d'essayer de l'ajouter
				if( dictionaryWord.getFrequency() >= minFrequency )
				{
					addToOrderedList( dictionaryWord, dictionaryWordList, nbOfWord );
					
					// on change le min 
					if( dictionaryWordList.size() != 0 )
					{
						minFrequency = dictionaryWordList.get( dictionaryWordList.size() - 1 ).getFrequency();
					}
				}
				
			}
		}
		
		
		// l'utilisteur ne veux que les string
		List<String> wordList = new ArrayList<String>( nbOfWord );
		for( CDictionaryWord dictionaryWord : dictionaryWordList )
		{
			wordList.add( dictionaryWord.getWord() );
		}
		return wordList;
	}
	
	public void increaseWord( String word )
	{
		CDictionaryLevel currentLevel = dictionaryLevel_0;
		CDictionaryLevel currentLevelTemp;
		Character currentCharacter;
		
		// on trouve le mot
		for( int i = 0 ; i < nbDictionaryLevel ; ++i )
		{
			// Si le caractere existe
			if( word.length() > i )
			{
				// on récupere ce caractere
				currentCharacter = word.charAt( i );
				
				// on recupere le dictionaryLevel corespondant
				currentLevelTemp = currentLevel.getDictionaryLevel( currentCharacter );
				
				// si on bien trouvé le level
				if( currentLevelTemp == null )
				{
					// le mot ce trouve donc dans le level en court, ou n'existe pas
					CDictionaryWord dictionaryWord = currentLevel.getDictionaryWord( word );
					if( dictionaryWord != null )
					{
						dictionaryWord.increaseFrequency();
					}
				}
				else
				{
					// le level temporaire devient le current level
					currentLevel = currentLevelTemp;
				}
			}
			else
			{
				// si le caractere n'existe pas
				// le mot ce trouve donc dans le level en court, ou n'existe pas
				CDictionaryWord dictionaryWord = currentLevel.getDictionaryWord( word );
				if( dictionaryWord != null )
				{
					dictionaryWord.increaseFrequency();
				}				
			}				
		}
		
		// le mot ce trouve donc dans le level en court, ou n'existe pas
		CDictionaryWord dictionaryWord = currentLevel.getDictionaryWord( word );
		if( dictionaryWord != null )
		{
			dictionaryWord.increaseFrequency();
		}
	}
	
	
	
	
	
	static public void addToOrderedList( 
			CDictionaryWord newDictionaryWord, 
			List<CDictionaryWord> orderedList,
			int nbMaxElem)
	{
		addToOrderedList( newDictionaryWord, orderedList );
		
		if( orderedList.size() > nbMaxElem )
		{
			// pour tous les elements dépassant la capacité demandé, on les supprime
			for( int i = nbMaxElem ; i < orderedList.size() ; ++i )
			{
				orderedList.remove( i );
			}
		}
	}
	
	static public void addToOrderedList( CDictionaryWord newDictionaryWord, List<CDictionaryWord> orderedList )
	{
		// s'il est contenu dans la liste
		if( orderedList.contains( newDictionaryWord ) )
		{
			// on le supprimer de la list
			orderedList.remove( newDictionaryWord );
		}
		
		CDictionaryWord dictionaryWordExisted;

		int addOrder = -1;
		for( int  i = 0 ; i < orderedList.size() ; ++i )
		{
			dictionaryWordExisted = orderedList.get( i );
			if( dictionaryWordExisted != null )
			{
				if( newDictionaryWord.getFrequency() > dictionaryWordExisted.getFrequency() )
				{
					addOrder = i;
					break;
				}
			}
		}
		
		if( addOrder != -1 )
		{
			orderedList.add( addOrder, newDictionaryWord );
		}
		else
		{
			orderedList.add( newDictionaryWord );
		}
		
	}
	//--------------------------------------------------- METHODES PRIVEES --//

	
}
