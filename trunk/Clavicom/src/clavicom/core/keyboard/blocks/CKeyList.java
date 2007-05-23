/*-----------------------------------------------------------------------------+

			Filename			: CBlocLevel2.java
			Creation date		: 22 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.blocs

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

package clavicom.core.keyboard.blocks;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Element;

import clavicom.core.keyboard.key.CKeyClavicom;
import clavicom.core.keyboard.key.CKeyboardKey;
import clavicom.tools.TXMLNames;

public class CKeyList
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	List<CKeyboardKey> keyList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyList()
	{
		keyList = new ArrayList<CKeyboardKey>();
	}

	//----------------------------------------------------------- METHODES --//
	public void AddKeyboardKey( int order, CKeyboardKey keybordKey )
	{
		keyList.add( order, keybordKey );
	}

	public CKeyboardKey GetKeyboardKey( int order )
	{
		return keyList.get( order );
	}
	public int size(){return keyList.size();}
	//--------------------------------------------------- METHODES PRIVEES --//
	
	private static CKeyboardKey GetKeyboardKey( Element node ) throws Exception
	{
		CKeyboardKey keyboardKey = null;
		
		if( node.getName().equals( TXMLNames.KY_CHARACTER_ELEMENT ) )
		{
			try
			{
				// keyboardKey = new CKeyCharacter( node );
			}
			catch (Exception ex)
			{
				throw new Exception("[Type de touche : Caractere]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_CLAVICOM_ELEMENT ) )
		{
			try
			{
				keyboardKey = new CKeyClavicom( node );
			}
			catch (Exception ex)
			{
				throw new Exception("[Type de touche : Clavicom]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_LUNCHER_ELEMENT ) )
		{
			try
			{
				// keyboardKey = new CKeyLauncher( node );
			}
			catch (Exception ex)
			{
				throw new Exception("[Type de touche : lanceur d'application]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_SHORTCUT_ELEMENT ) )
		{
			try
			{
				// keyboardKey = new CKeyShortcut( node );
			}
			catch (Exception ex)
			{
				throw new Exception("[Type de touche : Raccourci]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_STRING_ELEMENT ) )
		{
			try
			{
				// keyboardKey = new CKeyString( node );
			}
			catch (Exception ex)
			{
				throw new Exception("[Type de touche : Chaine de caractere]" + ex.getMessage() );
			}
		}
		else
		{
			throw new Exception("Type de touche inconnu : " + node.getName() );
		}
		
		return keyboardKey;
	}
	
	//---------------------------------------------------------------- XML --//
	public static CKeyList BuildKeyList ( Element node ) throws Exception
	{
		if( node == null  )
		{
			throw new Exception("[Construction d'une liste] : Impossible de récupérer le noeud XML");
		}
		
		CKeyList keylist = new CKeyList();
		
		// =================================================================
		// Récupération des keyboardKey
		// =================================================================
		List keyboardKeyList = node.getChildren( );
		for( Object object : keyboardKeyList )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					// =================================================================
					// Récupération de l'order
					// =================================================================
					String s_order = element.getAttributeValue( TXMLNames.KY_ATTRIBUT_ORDER );
					if( s_order == null )
					{
						throw new Exception("[Construction d'un bloc de niveau 2] : Impossible de récupérer l'attribut " + TXMLNames.KY_ATTRIBUT_ORDER + " pour le noeud " + element.getName() );
					}
					int i_order;
					try
					{
						i_order = Integer.parseInt( s_order );
					}
					catch ( Exception ex )
					{
						throw new Exception( "[Construction d'un bloc de niveau 2] : Impossible de convertir l'order " + s_order + " en entier" );
					}
					
					// =================================================================
					// Création du bon type de keyboardKey
					// =================================================================
					CKeyboardKey keybordKey;
					try
					{
						keybordKey = GetKeyboardKey( element );
					}
					catch(Exception ex)
					{
						throw new Exception( "[order=" + i_order + "]" + ex.getMessage() );
					}
					
					try
					{
						keylist.AddKeyboardKey( i_order , keybordKey );
					}
					catch(Exception ex)
					{
						throw new Exception( "[order=" + i_order + "] Impossible d'ajouter la touche à la liste" );
					}
				}
			}
		}
		
		return keylist;
	}
}
