/*-----------------------------------------------------------------------------+

			Filename			: CDictionaryLevel.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CDictionaryLevel
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	Character character; // caractere correspondant au niveau
	
	HashMap<Character, CDictionaryLevel> dictionaryLevelMap; // map des niveaux suivants
	
 
	List<CDictionaryWord> dictionaryWordOrededList; // liste des mot triés par leur frequence
	
	HashMap<String, CDictionaryWord> dictionaryWordMap; // map des mots avec leurs frequence

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CDictionaryLevel( Character myCharacter )
	{
		character = myCharacter;
		
		dictionaryLevelMap = new HashMap<Character, CDictionaryLevel>();
		

		
		dictionaryWordOrededList = new ArrayList<CDictionaryWord>();
		
		dictionaryWordMap = new HashMap<String, CDictionaryWord>();
	}

	//----------------------------------------------------------- METHODES --//	
	
	public CDictionaryWord getDictionaryWord( String word )
	{
		return dictionaryWordMap.get( word );
	}
	
	public Character getCharacter()
	{
		return character;
	}
	
	public void addDictionaryLevel( CDictionaryLevel dictionaryLevel )
	{
		dictionaryLevelMap.put( dictionaryLevel.getCharacter() , dictionaryLevel);
	}

	public void increaseDictionaryWord( String word )
	{
		
		if( dictionaryWordMap.containsKey( word ) )
		{		
			// MAP
			// on incrémante la frequence
			CDictionaryWord dictionaryWord = dictionaryWordMap.get( word  );
			dictionaryWord.increaseFrequency();
		
		
			// LIST
			// ajout au bon endroit dans dictionaryWordOrededList
			CDictionary.addToOrderedList( dictionaryWord, dictionaryWordOrededList );
		
		}
	}
	
	public void addDictionaryWord( CDictionaryWord newDictionaryWord )
	{		
		// Ajout à la map
		dictionaryWordMap.put( newDictionaryWord.getWord(), newDictionaryWord );
		
		// ajout au bon endroit dans dictionaryWordOrededList
		CDictionary.addToOrderedList( newDictionaryWord, dictionaryWordOrededList );
	}

	public int getFrequency( String word )
	{
		if( dictionaryWordMap.containsKey( word ) )
		{
			return dictionaryWordMap.get( word ).getFrequency();
		}
		else
		{
			return 0;
		}
		
	}
	
	public CDictionaryLevel getDictionaryLevel( Character character )
	{
		return dictionaryLevelMap.get( character );
	}

	public List<CDictionaryWord> getDictionaryWordOrededList()
	{
		return dictionaryWordOrededList;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
