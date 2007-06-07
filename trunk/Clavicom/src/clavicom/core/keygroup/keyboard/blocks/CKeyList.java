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

package clavicom.core.keygroup.keyboard.blocks;

import java.util.ArrayList;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import clavicom.core.keygroup.keyboard.key.CKeyCharacter;
import clavicom.core.keygroup.keyboard.key.CKeyClavicom;
import clavicom.core.keygroup.keyboard.key.CKeyLastWord;
import clavicom.core.keygroup.keyboard.key.CKeyLauncher;
import clavicom.core.keygroup.keyboard.key.CKeyLevel;
import clavicom.core.keygroup.keyboard.key.CKeyPrediction;
import clavicom.core.keygroup.keyboard.key.CKeyShortcut;
import clavicom.core.keygroup.keyboard.key.CKeyString;
import clavicom.core.keygroup.keyboard.key.CKeyKeyboard;
import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CKeyList
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	
	List<CKeyKeyboard> keyList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CKeyList()
	{
		keyList = new ArrayList<CKeyKeyboard>();
	}

	//----------------------------------------------------------- METHODES --//
	public void AddKeyboardKey( int order, CKeyKeyboard keybordKey )
	{
		keyList.add( order, keybordKey );
	}

	public CKeyKeyboard getKeyKeyboard( int order )
	{
		return keyList.get( order );
	}
	public int keyCount(){return keyList.size();}
	//--------------------------------------------------- METHODES PRIVEES --//
	
	private static CKeyKeyboard GetKeyboardKey( Element node ) throws Exception
	{
		CKeyKeyboard keyboardKey = null;
		
		if( node.getName().equals( TXMLNames.KY_ELEMENT_CHARACTER ) )
		{
			try
			{
				keyboardKey = new CKeyCharacter( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : " + UIString.getUIString( "EX_KEYLIST_TYPE_CHARACTER" ) + "]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_ELEMENT_CLAVICOM ) )
		{
			try
			{
				keyboardKey = new CKeyClavicom( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_CLAVICOM" )+ "]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_ELEMENT_LAUNCHER ) )
		{
			try
			{
				keyboardKey = new CKeyLauncher( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_APPLICATION_LAUNCHER" )+ "]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_ELEMENT_SHORTCUT ) )
		{
			try
			{
				keyboardKey = new CKeyShortcut( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_SHORTCUT" )+ "]" + ex.getMessage() );
			}
		}else if( node.getName().equals( TXMLNames.KY_ELEMENT_STRING ) )
		{
			try
			{
				keyboardKey = new CKeyString( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_STRING" )+ "]" + ex.getMessage() );
			}
		}
		else if( node.getName().equals( TXMLNames.KY_ELEMENT_KEYLEVEL ) )
		{
			try
			{
				keyboardKey = new CKeyLevel( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_LEVEL" )+ "]" + ex.getMessage() );
			}
		}
		else if( node.getName().equals( TXMLNames.KY_ELEMENT_LASTWORD ) )
		{
			try
			{
				keyboardKey = new CKeyLastWord( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_LAST_WORD" )+ "]" + ex.getMessage() );
			}
		}
		else if( node.getName().equals( TXMLNames.KY_ELEMENT_PREDICTION ) )
		{
			try
			{
				keyboardKey = new CKeyPrediction( node );
			}
			catch (Exception ex)
			{
				throw new Exception("["+ UIString.getUIString( "EX_KEYLIST_KEY_TYPE" )+ " : "+ UIString.getUIString( "EX_KEYLIST_TYPE_PREDICTION" )+ "]" + ex.getMessage() );
			}
		}
		else
		{
			throw new Exception( UIString.getUIString( "EX_KEYLIST_UNKNOWN_KEY_TYPE" )+ " : " + node.getName() );
		}
		
		return keyboardKey;
	}
	
	//---------------------------------------------------------------- XML --//
	public static CKeyList BuildKeyList ( Element node ) throws Exception
	{
		if( node == null  )
		{
			throw new Exception("[" + UIString.getUIString( "EX_KEYLIST_BUILD_LIST" ) + "] : "+ UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) );
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
					// Récupération de l'order de la keyboardKey
					// =================================================================
					String s_order = element.getAttributeValue( TXMLNames.KY_ATTRIBUTE_ORDER );
					if( s_order == null )
					{
						throw new Exception("[" + UIString.getUIString( "EX_KEYLIST_BUILD_LIST" ) + "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.KY_ATTRIBUTE_ORDER + UIString.getUIString( "EX_KEYLIST_FOR_THE_NODE" ) + element.getName() );
					}
					int i_order;
					try
					{
						i_order = Integer.parseInt( s_order );
					}
					catch ( Exception ex )
					{
						throw new Exception( "[" + UIString.getUIString( "EX_KEYLIST_BUILD_LIST" ) + "] : "+ UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + s_order + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ) );
					}
					
					// =================================================================
					// Création du bon type de keyboardKey
					// =================================================================
					CKeyKeyboard keybordKey;
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
						throw new Exception( "[order=" + i_order + "] " + UIString.getUIString( "EX_KEYLIST_CAN_NOT_ADD_KEY" ) );
					}
				}
			}
		}
		
		return keylist;
	}
	
	
	public Element buildNode( int order ) throws Exception
	{
		Element list = new Element( TXMLNames.BL_ELEMENT_KEY_LIST );
		
		Attribute order_att = new Attribute( TXMLNames.BL_ATTRIBUTE_ORDER, String.valueOf( order ) );
		list.setAttribute( order_att );
		
		for( int i = 0 ; i < keyList.size() ; ++i )
		{
			CKeyKeyboard keyboardKey = keyList.get( i );
			if( keyboardKey != null )
			{
				try
				{
					list.addContent( keyboardKey.buildNode( i ) );
				}
				catch ( Exception e )
				{
					throw new Exception("[key:" + i + "]" + e.getMessage());
				}
			}
		}
		
		return list;
	}
}
