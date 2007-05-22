/*-----------------------------------------------------------------------------+

			Filename			: CBlocLevel1.java
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

import clavicom.tools.TXMLNames;

public class CBlocLevel1
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	List<CBlocLevel2> blocLevel2List;
	String caption; // nom a afficher à l'utilisateur pour afficher ou non ce bloc
	boolean visible;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public CBlocLevel1( String myCaption, Boolean myVisible )
	{
		blocLevel2List = new ArrayList<CBlocLevel2>();
		visible = myVisible;
		caption = myCaption;
	}

	//----------------------------------------------------------- METHODES --//
	public void AddBlocLevel2( CBlocLevel2 blocLevel2 )
	{
		blocLevel2List.add( blocLevel2 );
	}
	
	public CBlocLevel2 GetBlocLevel2( int order )
	{
		return blocLevel2List.get( order );
	}
	
	public int size(){return blocLevel2List.size();}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	//---------------------------------------------------------------- XML --//
	public static CBlocLevel1 BuildBlocLevel1 ( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception("[Construction d'un bloc de niveau 1] : Impossible de trouver le noeud XML");
		}
		
		// =================================================================
		// Récupération de l'attribut Caption
		// =================================================================
		String caption = node.getAttributeValue( TXMLNames.BL_CAPTION );
		if( caption == null )
		{
			throw new Exception("[Construction d'un bloc de niveau 1] : Impossible de trouver l'attribut " + TXMLNames.BL_CAPTION);
		}
		
		// =================================================================
		// Récupération de l'attribut visible
		// =================================================================
		String s_visible = node.getAttributeValue( TXMLNames.BL_VISIBLE );
		if( s_visible == null )
		{
			throw new Exception("[caption : " + caption + "][Construction d'un bloc de niveau 1] : Impossible de trouver l'attribut " + TXMLNames.BL_VISIBLE);
		}
		boolean b_visible;
		try
		{
			b_visible = Boolean.parseBoolean( s_visible );
		}
		catch (Exception ex)
		{
			throw new Exception("[caption : " + caption + "][Construction d'un bloc de niveau 1] : Impossible de convertir " + s_visible + " en booléen");
		}
		
		CBlocLevel1 blocLevel1 = new CBlocLevel1( caption, b_visible );
		
		// =================================================================
		// Récupération des blocs de niveau 2
		// =================================================================
		List blocNiveau2List = node.getChildren( TXMLNames.BL_ELEMENT_BLOC_N2 );
		for( Object object : blocNiveau2List )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					CBlocLevel2 blocLevel2 = null;
					try
					{
						blocLevel2 = CBlocLevel2.BuildBlocLevel2( element );
					}
					catch(Exception ex)
					{
						throw new Exception("[caption : " + caption + "]" + ex.getMessage() );
					}
					
					blocLevel1.AddBlocLevel2( blocLevel2 );
				}
			}
		}
		
		return blocLevel1;
	}
}
