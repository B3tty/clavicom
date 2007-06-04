/*-----------------------------------------------------------------------------+

			Filename			: CDictionaryWord.java
			Creation date		: 4 juin 07
		
			Project				: Clavicom
			Package				: clavicom.core.engine.dictionary

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

public class CDictionaryWord
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	int frequency;
	String word;
	

	//------------------------------------------------------ CONSTRUCTEURS --//
	public CDictionaryWord( String myWord, int myFrequency )
	{
		frequency = myFrequency;
		word = myWord;
	}

	//----------------------------------------------------------- METHODES --//
	
	public int getFrequency()
	{
		return frequency;
	}
	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}
	public String getWord()
	{
		return word;
	}
	public void setWord(String word)
	{
		this.word = word;
	}
	
	public void increaseFrequency()
	{
		frequency++;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
