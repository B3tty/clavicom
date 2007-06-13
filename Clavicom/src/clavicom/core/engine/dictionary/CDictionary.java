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

import java.io.FileInputStream;
import java.util.ArrayList;
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
	CDictionaryLevel dictionaryGlobal;
	CDictionaryLevel dictionaryUser;

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CDictionary( 
			CDictionaryName dictionaryName,
			CPreferedWords preferedWords
			) throws Exception
	{
		dictionaryGlobal = new CDictionaryLevel( new Character(' ') ); // le caractere du niveau 0, on s'en fou
		dictionaryUser = new CDictionaryLevel( new Character(' ') ); // le caractere du niveau 0, on s'en fou
		
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
				addWord( dictionaryUser, new CDictionaryWord(  preferedWord.getWord(), preferedWord.getFrequency() ) );
			}
		}
		
	}
	
	private void LoadDictionary(CDictionaryName dictionaryName) throws Exception
	{
		String dictionaryPath = CFilePaths.getDictionariesFolder() + dictionaryName.getDictionaryName();

		// Ouverture du fichier
		FileInputStream fis = new FileInputStream( dictionaryPath );
		
		// On construit et on lit autant d'octets qu'il y a dans le fichier
		int x= fis.available();
		byte b[]= new byte[x];
		fis.read(b);
		fis.close();
		
		// On construit la String à partir du buffer
		String content = new String(b,"UTF-8");
		String[] tabDico = content.split("[\r][\n]");
		int frequence = tabDico.length;
		
		// parcourt du fichier
		try
		{
			for ( String word : tabDico )
			{
				// pour chaque mot - on met tout en minuscule
				addWord( dictionaryGlobal, new CDictionaryWord( word.toLowerCase(), frequence-- ) );
			}
		}		
		catch (Exception e)
		{
			throw new Exception("[" + UIString.getUIString( "EX_DICTIONARY_BUILD" ) + "] :" + UIString.getUIString("EX_DICTIONARY_READ") + " : " + e.getMessage() );
		}

	}

	public void addWord( CDictionaryLevel dictionaryGlobal, CDictionaryWord newDictionaryWord )
	{
		Character currentCharacter;
		CDictionaryLevel currentDictionaryLevel = dictionaryGlobal;
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
		
		
		// ====================================================================================
		// récupération des mots du dictionnaire
		// ====================================================================================
		List<CDictionaryWord> dictionaryWordList = new ArrayList<CDictionaryWord>( nbOfWord );
		
		CDictionaryLevel currentLevel = dictionaryGlobal;
		CDictionaryLevel currentLevelTemp;
		boolean rienTrouve = false;
		
		// on trouve le bon level
		for( int i = 0 ; i < beginString.length() ; ++i )
		{
			currentLevelTemp = currentLevel.getDictionaryLevel( beginString.charAt( i ) );
			if( currentLevelTemp == null )
			{
				rienTrouve = true;
				break;
			}
			currentLevel = currentLevelTemp;
			
		}
		
		List<String> shortListDico = new ArrayList<String>();
		
		// si la requete donne bien quelque chose
		if( ! rienTrouve )
		{
			// je suis au bon niveau, donc je prend les mots du niveau
			// plus ceux des niveaux du dessous
			dictionaryWordList = currentLevel.getDictionaryWordOrededList( nbOfWord );
			
			// Tri de la list par order alphabétique
			//shortListDico = TrierListe( dictionaryWordList );
			
			// on met en tableau de string
			for( CDictionaryWord dictionaryWord : dictionaryWordList )
			{
				shortListDico.add( dictionaryWord.getWord() );
			}
		}
		
		
		
		
		// ====================================================================================
		// récupération des mots de l'utilisateur
		// ====================================================================================
		dictionaryWordList = new ArrayList<CDictionaryWord>( nbOfWord );
		
		currentLevel = dictionaryUser;
		rienTrouve = false;
		
		// on trouve le bon level
		for( int i = 0 ; i < beginString.length() ; ++i )
		{
			currentLevelTemp = currentLevel.getDictionaryLevel( beginString.charAt( i ) );
			if( currentLevelTemp == null )
			{
				rienTrouve = true;
				break;
			}
			currentLevel = currentLevelTemp;
			
		}
		List<String> shortListUser = new ArrayList<String>();
		
		// si la requete donne bien quelque chose
		if( ! rienTrouve )
		{
			// je suis au bon niveau, donc je prend les mots du niveau
			// plus ceux des niveaux du dessous
			dictionaryWordList = currentLevel.getDictionaryWordOrededList( nbOfWord );
			
			// Tri de la list par order alphabétique
			//shortListUser = TrierListe( dictionaryWordList );
			
			// on met en tableau de string
			for( CDictionaryWord dictionaryWord : dictionaryWordList )
			{
				shortListUser.add( dictionaryWord.getWord() );
			}
		}
		
		
		
		// ====================================================================================
		// Fusion des deux listes en mettant ceux de l'utilisateur en premier
		// ====================================================================================
		List<String> finalList = new ArrayList<String>();
		
		// celle de l'utilisteur en premier
		for( String word : shortListUser )
		{
			finalList.add( word );
		}
		
		// Puis celle du dictionnaire
		for( String word : shortListDico )
		{
			finalList.add( word );
		}
		
		int index = 0;
		if( nbOfWord > finalList.size() )
		{
			index = finalList.size();
		}
		else
		{
			index = nbOfWord;
		}
		finalList = finalList.subList(0, index);
		
		return finalList;
	}

	public CDictionaryWord getWord( String word )
	{
		CDictionaryLevel currentLevel = dictionaryGlobal;
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
					return currentLevel.getDictionaryWord( word );
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
				// le mot se trouve donc dans le level en court, ou n'existe pas
				return currentLevel.getDictionaryWord( word );				
			}				
		}
		
		// le mot ce trouve donc dans le level en court, ou n'existe pas
		return currentLevel.getDictionaryWord( word );
	}
	
	public void increaseWord( String word )
	{
		CDictionaryLevel currentLevel = dictionaryUser;
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


	public CDictionaryLevel getUserDictionnaryLevel()
	{
		return dictionaryUser;
	}

	
}
