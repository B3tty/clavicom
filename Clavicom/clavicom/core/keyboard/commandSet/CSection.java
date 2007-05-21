/*-----------------------------------------------------------------------------+

			Filename			: CSection.java
			Creation date		: 21 mai 07
		
			Project				: Clavicom
			Package				: clavicom.core.keyboard.commandSet

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

package clavicom.core.keyboard.commandSet;

import java.util.HashMap;

import org.jdom.Element;

import clavicom.tools.TXMLNames;

public class CSection
{
	//--------------------------------------------------------- CONSTANTES --//

	//---------------------------------------------------------- VARIABLES --//
	String name;	// nom de la section
	HashMap<String, CCommand> commandMap;

	//------------------------------------------------------ CONSTRUCTEURS --//	
	CSection( String myName )
	{
		name = myName;
		commandMap = new HashMap<String, CCommand>();
	}
	
	
	public static CSection BuildSection( Element node ) throws Exception
	{
		// construction d'une section a partir d'un noeud XML
		
		if ( node == null )
		{
			throw new Exception("[Construction d'une section de clavier] : Impossible de trouver le noeud XML correspondant à cette section");
		}
		
		String name = node.getAttributeValue( TXMLNames.CS_ATTRIBUT_NAME );
		
		if( name == null )
		{
			throw new Exception("[Construction d'une section de clavier] : Impossible de récupérer l'attribut " + TXMLNames.CS_ATTRIBUT_NAME + " d'une section");
		}
		
		CSection section = new CSection( name );
		
		for( Object object : node.getChildren( TXMLNames.CS_ELEMENT_COMMAND ) )
		{
			if( object instanceof Element )
			{
				Element element = (Element)object;
				if( element != null )
				{
					CCommand command = CCommand.BuildCommand( element );
					
					try
					{
						section.AddCommand( command );
					}
					catch(Exception ex)
					{
						throw new Exception("[Construction d'une section de clavier] : Impossible d'ajouter la commande à la liste des commandes");
					}
				}
			}
		}
		
		return section;
		
	}

	//----------------------------------------------------------- METHODES --//
	/**
	 * Méthode permettant d'ajouter une commande à une section
	 * Lance une exception si ce n'est pas possible
	 */
	public void AddCommand( CCommand command )
	{
		commandMap.put( command.GetCaption(), command );
	}
	
	/**
	 * Retourn la command correspondante au name passé en parametre
	 * Retourne null si la commande n'éxiste pas
	 * @param name
	 * @return
	 */
	public CCommand GetCommand( String name )
	{
		return commandMap.get( name );
	}
	
	public String GetName(){return name;}

	//--------------------------------------------------- METHODES PRIVEES --//
}
