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

package clavicom.core.keyboard.blocks;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CKeyGroup
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	List<CKeyList> keyListList;
	String caption; // nom a afficher à l'utilisateur pour afficher ou non ce bloc
	boolean visible;
	
	//------------------------------------------------------ CONSTRUCTEURS --//
	public CKeyGroup( String myCaption, Boolean myVisible )
	{
		keyListList = new ArrayList<CKeyList>();
		visible = myVisible;
		caption = myCaption;
	}

	//----------------------------------------------------------- METHODES --//
	public void AddKeyList( int order, CKeyList blocLevel2 )
	{
		keyListList.add( order, blocLevel2 );
	}
	
	public CKeyList GetkeyList( int order )
	{
		return keyListList.get( order );
	}
	
	public int size(){return keyListList.size();}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	//---------------------------------------------------------------- XML --//
	public static CKeyGroup BuildKeyGroup ( Element node ) throws Exception
	{
		if( node == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYGROUP_BUILD_GROUP" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ));
		}
		
		// =================================================================
		// Récupération de l'attribut Caption
		// =================================================================
		String caption = node.getAttributeValue( TXMLNames.BL_ATTRIBUTE_CAPTION );
		if( caption == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYGROUP_BUILD_GROUP" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE") + " " + TXMLNames.BL_ATTRIBUTE_CAPTION);
		}
		
		// =================================================================
		// Récupération de l'attribut visible
		// =================================================================
		String s_visible = node.getAttributeValue( TXMLNames.BL_ATTRIBUTE_VISIBLE );
		if( s_visible == null )
		{
			throw new Exception("[caption : " + caption + "][" + UIString.getUIString( "EX_KEYGROUP_BUILD_GROUP" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE") + " " + TXMLNames.BL_ATTRIBUTE_VISIBLE);
		}
		boolean b_visible;
		try
		{
			b_visible = Boolean.parseBoolean( s_visible );
		}
		catch (Exception ex)
		{
			throw new Exception("[caption : " + caption + "][" + UIString.getUIString( "EX_KEYGROUP_BUILD_GROUP" ) + "] : " + UIString.getUIString( "EX_KEYBOARD_CAN_NOT_CONVERT" ) + s_visible + UIString.getUIString( "EX_KEYGROUP_TO_BOOLEAN" ));
		}
		
		CKeyGroup keyGroup = new CKeyGroup( caption, b_visible );
		
		// =================================================================
		// Récupération des keylist
		// =================================================================
		List keylist = node.getChildren( TXMLNames.BL_ELEMENT_KEY_LIST );
		for( Object object : keylist )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					// =================================================================
					// Récupération de l'attribut order de la keyList
					// =================================================================
					String s_order = element.getAttributeValue( TXMLNames.BL_ATTRIBUTE_ORDER );
					if( s_order == null )
					{
						throw new Exception("[caption : " + caption + "][" + UIString.getUIString( "EX_KEYGROUP_BUILD_GROUP" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.BL_ATTRIBUTE_ORDER);
					}
					int i_order;
					try
					{
						i_order = Integer.parseInt( s_order );
					}
					catch (Exception ex)
					{
						throw new Exception("[caption : " + caption + "][" + UIString.getUIString( "EX_KEYGROUP_BUILD_GROUP" ) + "] : " + UIString.getUIString( "EX_KEYBOARD_CAN_NOT_CONVERT" ) + s_order + UIString.getUIString( "EX_KEYGROUP_TO_BOOLEAN" ));
					}
					
					
					// =================================================================
					// Récupération des keyList
					// =================================================================
					
					CKeyList keyList = null;
					try
					{
						keyList = CKeyList.BuildKeyList( element );
					}
					catch(Exception ex)
					{
						throw new Exception("[caption : " + caption + "]" + ex.getMessage() );
					}
					
					keyGroup.AddKeyList( i_order, keyList );
				}
			}
		}
		
		return keyGroup;
	}
}