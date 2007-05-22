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

package clavicom.core.keyboard.blocs;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import clavicom.core.keyboard.key.CKeyboardKey;
import clavicom.tools.TXMLNames;

public class CBlocLevel2
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	List<CKeyboardKey> keyList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CBlocLevel2()
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
	
	private CKeyboardKey GetKeyboardKey( Element node )
	{
		CKeyboardKey = 
		
		if( node.getName().equals( TXMLNames.KY_CLAVICOM_ELEMENT ) )
		{
			
		}
	}
	
	//---------------------------------------------------------------- XML --//
	public static CBlocLevel2 BuildBlocLevel2 ( Element node ) throws Exception
	{
		if( node == null  )
		{
			throw new Exception("[Construction d'un bloc de niveau 2] : Impossible de récupérer le noeud XML");
		}
		
		CBlocLevel2 blocLevel2 = new CBlocLevel2();
		
		// =================================================================
		// Récupération des OrderedKeys
		// =================================================================
		List keyboardKeyList = node.getChildren( TXMLNames.BL_ELEMENT_ORDERED_KEY );
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
					CKeyboardKey keybordKey = GetKeyboardKey( element );
					
					blocLevel2.AddKeyboardKey( i_order , keybordKey );
				}
			}
		}
		
		return blocLevel2;
	}
}
