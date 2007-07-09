/*-----------------------------------------------------------------------------+

			Filename			: CPreferedWords.java
			Creation date		: 25 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.profil

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

package clavicom.core.profil;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import clavicom.core.engine.dictionary.CDictionaryWord;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CPreferedWords
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	List<CDictionaryWord> preferedWords;
	
	boolean active;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public CPreferedWords( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_PREFERED_WORDS") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		preferedWords = new ArrayList<CDictionaryWord>();
		
		// ============================================================
		// récupération de l'attribut active
		// ============================================================
		String strActive = node.getAttributeValue(TXMLNames.PR_ATTRIBUTE_PREFERED_WORD_ACTIVE);
		if(strActive == null || strActive.equals(""))
		{
			throw new Exception (	UIString.getUIString("EX_PREFERED_WORDS_MISSING_ATTRIBUTE_1")+
									TXMLNames.PR_ATTRIBUTE_NAVIGATION_ROLLOVER +
									UIString.getUIString("EX_PREFERED_WORDS_MISSING_ATTRIBUTE_2"));
	
		}
		
		try
		{
			active = Boolean.parseBoolean(strActive);
		}
		catch (Exception ex)
		{
			throw new Exception (	UIString.getUIString("EX_PREFERED_WORD_BAD_ACTIVE_1")+
									strActive +
									UIString.getUIString("EX_PREFERED_WORD_BAD_ACTIVE_2")+
									TXMLNames.PR_ATTRIBUTE_PREFERED_WORD_ACTIVE +							
									UIString.getUIString("EX_PREFERED_WORD_BAD_ACTIVE_3"));			
		}
		
		// ============================================================
		// récupération des Elements preferedWord
		// ============================================================
		List keyboardKeyList = node.getChildren( TXMLNames.PR_ELEMENT_PREFERED_WORD );
		for( Object object : keyboardKeyList )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					String prefredWord = element.getText();
					
					// attribut frequency
					String frequency = element.getAttributeValue( TXMLNames.PR_ATTRIBUTES_PREFERED_WORD_FREQUENCY );
					if( frequency == null )
					{
						throw new Exception( "[" + UIString.getUIString("EX_PROFIL_BUILD_PREFERED_WORDS") + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.PR_ATTRIBUTES_PREFERED_WORD_FREQUENCY );
					}
					int i_frequency;
					try
					{
						i_frequency = Integer.parseInt( frequency );
					}
					catch ( Exception ex )
					{
						throw new Exception( "[" + UIString.getUIString( "EX_PROFIL_BUILD_PREFERED_WORDS" ) + "] : "+ UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + frequency + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ) );
					}
					
					preferedWords.add(new CDictionaryWord( prefredWord, i_frequency) );
				}
			}
		}
	}

	//----------------------------------------------------------- METHODES --//	
	
	public void addPreferedWord( CDictionaryWord preferedWord )
	{
		if ( preferedWords.contains( preferedWord ) )
		{
			preferedWord.increaseFrequency();
		}
		else
		{
			preferedWords.add( preferedWord );
		}
	}
	
	public void clearPreferedWord(  )
	{
		preferedWords.clear();
	}
	
	public void removePreferedWord( CDictionaryWord preferedWord )
	{
		preferedWords.remove( preferedWord );
	}
	
	public boolean contain( String preferedWord )
	{
		for( CDictionaryWord dictionaryWord : preferedWords )
		{
			if( dictionaryWord.getWord().equals( preferedWord ) )
			{
				return true;
			}
		}
		return false;
	}
	
	public CDictionaryWord getPreferedWord( int order )
	{
		return preferedWords.get( order );
	}
	
	public CDictionaryWord getPreferedWord( String preferedWord )
	{
		for( CDictionaryWord dictionaryWord : preferedWords )
		{
			if( dictionaryWord.getWord().equals( preferedWord ) )
			{
				return dictionaryWord;
			}
		}
		
		return null;
	}
	
	public int getSize() { return preferedWords.size(); }
	
	public Element buildNode()
	{
		Element preferedWords_elem = new Element( TXMLNames.PR_ELEMENT_PREFERED_WORDS );
		Element preferedWord_elem;
		Attribute frequency_att;
		int frequency;
		
		// ajout de l'attribut active
		Attribute active_att = new Attribute( TXMLNames.PR_ATTRIBUTE_PREFERED_WORD_ACTIVE, String.valueOf( active ) );
		preferedWords_elem.setAttribute( active_att );
		
		for( CDictionaryWord preferedWord : preferedWords )
		{
			frequency = preferedWord.getFrequency();
			
			preferedWord_elem = new Element( TXMLNames.PR_ELEMENT_PREFERED_WORD );
			preferedWord_elem.setText( preferedWord.getWord() );
			
			frequency_att = new Attribute( TXMLNames.PR_ATTRIBUTES_PREFERED_WORD_FREQUENCY, String.valueOf( frequency ) );
			preferedWord_elem.setAttribute( frequency_att );
			
			preferedWords_elem.addContent( preferedWord_elem );
		}
		
		return preferedWords_elem;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
