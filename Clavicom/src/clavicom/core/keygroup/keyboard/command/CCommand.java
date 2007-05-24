/*-----------------------------------------------------------------------------+

			Filename			: CCommand.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.command

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

package clavicom.core.keygroup.keyboard.command;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import clavicom.gui.language.UIString;
import clavicom.tools.TXMLNames;

public class CCommand
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String caption;
	List<CCode> CodeList;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	public CCommand( String myCaption )
	{
		caption = myCaption;
		CodeList = new ArrayList<CCode>();
	}

	//----------------------------------------------------------- METHODES --//
	/**
	 * Ajoute un code à la liste des codes
	 * Lance une exception si l'order n'est pas bon
	 */
	public void AddCode( int order, CCode code )
	{
		CodeList.add(order, code);
	}
	
	public String GetCaption(){return caption;}
	
	/**
	 * Donne le code correspondant à l'order donné
	 * Lance une exception si l'order n'est pas bon
	 * @param order
	 * @return
	 */
	public CCode GetCode( int order )
	{
		return CodeList.get( order );
	}
	
	public int Size(){return CodeList.size();}

	//--------------------------------------------------- METHODES PRIVEES --//
	
	//---------------------------------------------------------------- XML --//
	
	public static CCommand BuildCommand( Element node ) throws Exception
	{
		// Construction d'un CCommand à partir d'un noeud XML
		
		if( node == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_COMMAND_BUILD_COMMANDE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_NODE" ) );
		}
		
		String caption = node.getAttributeValue( TXMLNames.CM_ATTRIBUTE_CAPTION );
		if( caption == null )
		{
			throw new Exception("[" + UIString.getUIString( "EX_COMMAND_BUILD_COMMANDE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.CM_ATTRIBUTE_CAPTION);
		}
		
		CCommand command = new CCommand( caption );
		
		for( Object object : node.getChildren( TXMLNames.CS_ELEMENT_CODE ) )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				
				if( element != null )
				{
					// Récupération de l'attribut order
					String order = element.getAttributeValue( TXMLNames.CM_ATTRIBUTE_ORDER );
					if( order == null )
					{
						throw new Exception("[" + UIString.getUIString( "EX_COMMAND_BUILD_COMMANDE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_NOT_FIND_ATTRIBUTE" ) + TXMLNames.CM_ATTRIBUTE_ORDER);
					}
					
					int orderInt = 0;
					try
					{
						orderInt = Integer.parseInt( order );
					}
					catch( Exception ex )
					{
						throw new Exception("[caption : " + caption + "][order : " + order + "][" + UIString.getUIString( "EX_COMMAND_BUILD_COMMANDE" )+ "] : " + UIString.getUIString( "EX_KEYGROUP_CAN_NOT_CONVERT" ) + order + UIString.getUIString( "EX_KEYGROUP_TO_INTEGER" ));
					}
					
					CCode code = null;
					try
					{
						code = CCode.BuildCode( element );
					}
					catch (Exception ex)
					{
						throw new Exception( "[order : " + orderInt + "]" + ex.getMessage() );
					}
					
					try
					{
						command.AddCode(orderInt, code);
					}
					catch(Exception ex)
					{
						throw new Exception("[order : " + orderInt + "][" + UIString.getUIString( "EX_COMMAND_BUILD_COMMANDE" )+ "] : " + UIString.getUIString( "EX_COMMAND_CAN_NOT_ADD_CODE" ) + orderInt + UIString.getUIString( "EX_COMMAND_IN_THE_COMMANDE" ) + caption);
					}
				}
			}
		}
		
		return command;
	}
}
