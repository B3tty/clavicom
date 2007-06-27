/*-----------------------------------------------------------------------------+

			Filename			: CKeyboard.java
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

import org.jdom.Element;

import clavicom.core.keygroup.keyboard.blocks.CKeyGroup;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CKeyboard
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//	
	List< CKeyGroup > keyGroupList;

	//------------------------------------------------------ CONSTRUCTEURS --//
	
	public CKeyboard( Element node ) throws Exception
	{
		if ( node == null )
		{
			throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_BUILD") + "] : " + UIString.getUIString("EX_KEYGROUP_NOT_FIND_NODE") );
		}
		
		keyGroupList = new ArrayList<CKeyGroup>();
		
		// ============================================================
		// Récupération de la liste des groupes
		// ============================================================
		List groupeList = node.getChildren( TXMLNames.PR_ELEMENT_KEY_GROUP );
		for( Object object : groupeList )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					// ============================================================
					// Récupération de l'order
					// ============================================================
					String s_order = element.getAttributeValue( TXMLNames.BL_ATTRIBUTE_ORDER );
					if( s_order == null )
					{
						throw new Exception("[" + UIString.getUIString( "EX_KEYBOARD_BUILD" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.BL_ATTRIBUTE_ORDER);
					}
					int i_order;
					try
					{
						i_order = Integer.parseInt( s_order );
					}
					catch (Exception ex)
					{
						throw new Exception("[" + UIString.getUIString( "EX_KEYBOARD_BUILD" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_order + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ));
					}
					
					
					
					// récupération du groupe
					CKeyGroup key_group = null;
					try
					{
						key_group = CKeyGroup.BuildKeyGroup( element );
					}
					catch(Exception ex)
					{
						throw new Exception( "[" + UIString.getUIString("EX_KEYBOARD_BUILD") + "] : " + ex.getMessage() );
					}
					
					keyGroupList.add( i_order, key_group );
				}
			}
		}
	}

	//----------------------------------------------------------- METHODES --//
	public boolean keyGroupExists(String caption)
	{
		for (CKeyGroup currentGroup : keyGroupList)
		{
			if(currentGroup.getCaption().equals(caption))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public void addKeyGroup( CKeyGroup keyGroup )
	{
		keyGroupList.add( keyGroup );
	}
	
	public void removeKeyGroup( CKeyGroup keyGroup )
	{
		keyGroupList.remove( keyGroup );
	}
	
	public CKeyGroup getKeyGroup( int order )
	{
		return keyGroupList.get( order );
	}
	
	public int groupCount( )
	{
		return keyGroupList.size();
	}
	
	public Element BuildNode() throws Exception
	{
		Element keyboard = new Element( TXMLNames.PR_ELEMENT_KEYBOARD );
		
		// pour tous les groupes
		for ( int i = 0 ; i < keyGroupList.size() ; ++i )
		{
			CKeyGroup keyGroup = keyGroupList.get( i );
			if( keyGroup != null )
			{
				try
				{
					keyboard.addContent( keyGroup.buildNode( i ) );
				}
				catch ( Exception e )
				{
					throw new Exception("[group:" + i + "]" + e.getMessage());
				}
			}
		}
		
		return keyboard;
	}

	public void clearKeyboard()
	{
		keyGroupList.clear();
	}

	//--------------------------------------------------- METHODES PRIVEES --//
}
