/*-----------------------------------------------------------------------------+

 Filename			: testDictionary.java
 Creation date		: 4 juin 07
 
 Project				: Clavicom
 Package				: test.core

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

package test.core;

import java.util.List;

import clavicom.core.engine.dictionary.CDictionary;
import clavicom.core.keygroup.keyboard.command.commandSet.CCommandSet;
import clavicom.core.profil.CProfil;
import clavicom.gui.language.UIString;

public class testDictionary
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{


		try
		{
			// Chargement des UIString et shortcutset
			UIString.LoadUIStringFile("Ressources\\Application\\LanguagesUI\\francais.clg");
			CCommandSet.CreateInstance("Ressources\\Application\\CommandSets\\francais.ccs");
			
			// Chemins
			String input = "Ressources\\Temp\\profil.xml";
			
			// Chargement du profil
			CProfil.createInstance(input);
			CProfil profil = CProfil.getInstance();
			
			
			CDictionary.createInstance( profil.getDictionnaryName(), profil.getPreferedWords() );
			
//			CDictionaryLevel dictionaryLevel = dictionary.getDictionaryLevel_0();
//			
//			CDictionaryLevel dicionaryLevel0 = dictionaryLevel.getDictionaryLevel( new Character('t') ) ;
//			CDictionaryLevel dicionaryLevel1 = dicionaryLevel0.getDictionaryLevel( new Character('a') ) ;
//			CDictionaryLevel dicionaryLevel2 = dicionaryLevel1.getDictionaryLevel( new Character('r') ) ;
//			CDictionaryLevel dicionaryLevel3 = dicionaryLevel2.getDictionaryLevel( new Character('a') ) ;
			
			System.out.println("coucou");
			
			CDictionary.increaseWord("tarabiscoterait");
			CDictionary.increaseWord("tarabiscoterait");
			CDictionary.increaseWord("tarabiscoterait");
			CDictionary.increaseWord("tarabiscoterait");
			CDictionary.increaseWord("tarabiscoterait");
			CDictionary.increaseWord("tarabiscoterait");
			
			List<String> stringList = CDictionary.getWords( "tara", 14 );
			
			for( String word : stringList )
			{
				System.out.println( word );
			}
			
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}
}
