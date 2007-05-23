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

package clavicom.core.keyboard.command;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

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
			throw new Exception("[Construction d'une commande de clavier] : Impossible de trouver le noeud de la commande");
		}
		
		String caption = node.getAttributeValue( TXMLNames.CS_ATTRIBUTE_CAPTION );
		if( caption == null )
		{
			throw new Exception("[Construction d'une commande de clavier] : Impossible de trouver l'attribut :" + TXMLNames.CS_ATTRIBUTE_CAPTION);
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
					String order = element.getAttributeValue( TXMLNames.CS_ATTRIBUTE_ORDER );
					if( order == null )
					{
						throw new Exception("[Construction d'une commande de clavier] : Impossible de trouver l'attribut :" + TXMLNames.CS_ATTRIBUTE_ORDER);
					}
					
					int orderInt = 0;
					try
					{
						orderInt = Integer.parseInt( order );
					}
					catch( Exception ex )
					{
						throw new Exception("[caption : " + caption + "][order : " + order + "][Construction d'une commande de clavier] : Impossible de convertir l'order en entier.");
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
						throw new Exception("[order : " + orderInt + "][Construction d'une commande de clavier] : Impossible d'ajouter le code d'ordre " + orderInt + " dans la commande " + caption);
					}
				}
			}
		}
		
		return command;
	}
}
